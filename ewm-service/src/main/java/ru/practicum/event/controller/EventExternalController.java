package ru.practicum.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventExternalParams;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.external.EventExternalServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventExternalController {
    private final EventExternalServiceImpl eventExternalService;

    @GetMapping("/{id}")
    public EventFullDto findEventById(@PathVariable Integer id, HttpServletRequest request) {
        return eventExternalService.findEventById(id, request);
    }

    @GetMapping
    public List<EventShortDto> findAllEvents(@ModelAttribute @Valid EventExternalParams params,
                                             HttpServletRequest request) {
        return eventExternalService.findAllEvents(params, request);
    }
}
