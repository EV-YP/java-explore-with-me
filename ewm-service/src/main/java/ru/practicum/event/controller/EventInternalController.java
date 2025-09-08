package ru.practicum.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.internal.EventInternalServiceImpl;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventInternalController {
    private final EventInternalServiceImpl eventInternalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable Integer userId, @RequestBody @Valid NewEventDto event) {
        return eventInternalService.addEvent(userId, event);
    }

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable Integer userId,
                                         @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(defaultValue = "10") @Positive Integer size) {
        return eventInternalService.getEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventInternalService.getEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Integer userId,
                                    @PathVariable Integer eventId,
                                    @RequestBody @Valid UpdateEventUserRequest event) {
        return eventInternalService.updateEvent(userId, eventId, event);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getUserRequests(@PathVariable Integer userId,
                                                         @PathVariable Integer eventId) {
        return eventInternalService.getUserRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@RequestBody @Valid EventRequestStatusUpdateRequest request,
                                                         @PathVariable Integer userId,
                                                         @PathVariable Integer eventId) {
        return eventInternalService.updateRequests(request, userId, eventId);
    }

}
