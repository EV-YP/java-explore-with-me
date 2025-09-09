package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public RequestDto addRequest(Integer userId, Integer eventId) {
        Request request = new Request();

        Event event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        User user = checkUser(userId);

        if (requestRepository.findRequestsByEventIdAndRequesterId(eventId, userId) != null) {
            throw new ConflictException("Request already exists");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event is not published");
        } else if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Requester and initiator could not be the same.");
        }

        if (event.getParticipantLimit().equals(event.getConfirmedRequests()) && event.getParticipantLimit() != 0) {
            throw new ConflictException("Request limit exceeded");
        }

        if (!(event.getRequestModeration()) || (event.getParticipantLimit() == 0)) {
            request.setStatus(State.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }  else {
            request.setStatus(State.PENDING);
        }

        request.setEvent(event);
        request.setRequester(user);

        return requestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public RequestDto cancelRequest(Integer userId, Integer requestId) {
        Request request = requestRepository.findRequestById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id " + requestId + " not found"));

        checkUser(userId);

        request.setStatus(State.CANCELED);

        return requestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getRequests(Integer userId) {
        checkUser(userId);
        return requestRepository.findRequestsByRequesterId(userId).stream()
                .map(requestMapper::toRequestDto)
                .toList();
    }

    private User checkUser(Integer userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }
}
