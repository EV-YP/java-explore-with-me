package ru.practicum.event.service.external;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventExternalParams;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

public interface EventExternalService {
    EventFullDto findEventById(Integer id, HttpServletRequest request);

    List<EventShortDto> findAllEvents(EventExternalParams params, HttpServletRequest request);
}
