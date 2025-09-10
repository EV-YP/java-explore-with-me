package ru.practicum.event.service.internal;

import ru.practicum.event.dto.*;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface EventInternalService {
    EventFullDto addEvent(Integer userId, NewEventDto event);

    EventFullDto getEvent(Integer userId, Integer eventId);

    EventFullDto updateEvent(Integer userId, Integer eventId, UpdateEventUserRequest event);

    List<RequestDto> getUserRequests(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult updateRequests(EventRequestStatusUpdateRequest request, Integer userId, Integer eventId);

    List<EventShortDto> getEvents(Integer userId, Integer from, Integer size);
}
