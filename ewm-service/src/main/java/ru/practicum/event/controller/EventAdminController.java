package ru.practicum.event.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.SearchEventsDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.admin.EventAdminService;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {
    private final EventAdminService eventAdminService;

    @GetMapping
    public List<EventFullDto> getEvents(@ModelAttribute @Valid SearchEventsDto dto) {
        return eventAdminService.getEvents(dto);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@PathVariable Integer eventId,
                                    @RequestBody @Valid UpdateEventAdminRequest request) {
        return eventAdminService.updateEvent(eventId, request);
    }

}
