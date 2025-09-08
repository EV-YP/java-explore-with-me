package ru.practicum.event.service.admin;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.SearchEventsDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;

import java.util.List;

public interface EventAdminService {
    List<EventFullDto> getEvents(SearchEventsDto searchEventsDto);

    EventFullDto updateEvent(Integer eventId, UpdateEventAdminRequest request);
}
