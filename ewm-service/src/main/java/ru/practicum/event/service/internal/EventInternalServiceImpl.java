package ru.practicum.event.service.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.practicum.event.model.State.*;

@Service
@RequiredArgsConstructor
public class EventInternalServiceImpl implements EventInternalService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public EventFullDto addEvent(Integer userId, NewEventDto event) {
        User user = checkUser(userId);
        Category category = checkCategory(event.getCategoryId());

        Event newEvent = eventMapper.toEvent(event);
        checkDates(newEvent.getCreatedOn(), event.getEventDate());
        newEvent.setInitiator(user);
        newEvent.setCategory(category);
        newEvent.setState(PENDING);
        return eventMapper.toEventFullDto(eventRepository.save(newEvent));
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEvent(Integer userId, Integer eventId) {
        Event event = checkEvent(eventId);
        int confirmedRequests = requestRepository.findRequestsByEventIdAndStatus(eventId, CONFIRMED).size();
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest event) {
        Event eventFound = checkEvent(eventId);
        User user = checkUser(userId);

        if (eventFound.getState().equals(PUBLISHED)) {
            throw new ConflictException("Event with id " + eventId + " could not be changed");
        }

        if (event.getStateAction() != null) {
            switch (event.getStateAction()) {
                case CANCEL_REVIEW -> eventFound.setState(CANCELED);
                case SEND_TO_REVIEW -> eventFound.setState(PENDING);
            }
        }

        if (!userId.equals(eventFound.getInitiator().getId())) {
            throw new ConflictException("Initiator id mismatch");
        }

        if (event.getCategoryId() != null) {
            Category category = checkCategory(event.getCategoryId());
            eventFound.setCategory(category);
        }

        if (event.getEventDate() != null) {
            checkDates(eventFound.getCreatedOn(), event.getEventDate());
            eventFound.setEventDate(event.getEventDate());
        }

        return eventMapper.toEventFullDto(eventRepository.save(eventFound));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getUserRequests(Integer userId, Integer eventId) {
        checkUser(userId);
        checkEvent(eventId);

        return requestRepository.findRequestsByEventIdAndEventInitiatorId(eventId, userId).stream()
                .map(requestMapper::toRequestDto)
                .toList();
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest request,
                                                         Integer userId, Integer eventId) {
        checkUser(userId);
        Event event = checkEvent(eventId);
        List<Request> requests = requestRepository.findAllById(request.getRequestIds());

        if (Objects.equals(event.getParticipantLimit(), event.getConfirmedRequests())) {
            throw new ConflictException("ConfirmedRequests limit exceeded");
        }

        List<RequestDto> confirmedRequests = new ArrayList<>();
        List<RequestDto> rejectedRequests = new ArrayList<>();

        for (Request request1 : requests) {
            if (!request1.getStatus().equals(PENDING)) {
                throw new ConflictException("States should be PENDING");
            }

            if (request.getStatus().equals(CONFIRMED)) {
                if (event.getParticipantLimit() > event.getConfirmedRequests()) {
                    request1.setStatus(CONFIRMED);
                    requestRepository.save(request1);
                    confirmedRequests.add(requestMapper.toRequestDto(request1));
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    eventRepository.save(event);
                } else {
                    request1.setStatus(REJECTED);
                    requestRepository.save(request1);
                    rejectedRequests.add(requestMapper.toRequestDto(request1));
                }
            } else {
                request1.setStatus(REJECTED);
                requestRepository.save(request1);
                rejectedRequests.add(requestMapper.toRequestDto(request1));
            }
        }

        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEvents(Integer userId, Integer from, Integer size) {
        checkUser(userId);
        Pageable pageable = PageRequest.of(from, size);
        Page<Event> events = eventRepository.getEventByInitiatorId(userId, pageable);
        return events.getContent().stream()
                .map(eventMapper::toEventShortDto)
                .toList();
    }

    private void checkDates(LocalDateTime createdOn, LocalDateTime eventDate) {
        if (eventDate.isBefore(createdOn) ||
        eventDate.minusHours(2L).isBefore(createdOn)) {
            throw new ValidationException("Event date should be at least 2 hours later than the creation date");
        }
    }

    private User checkUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }

    private Category checkCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found"));
    }

    private Event checkEvent(Integer eventId) {
        return eventRepository.findEventById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));
    }
}
