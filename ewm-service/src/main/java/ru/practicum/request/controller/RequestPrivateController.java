package ru.practicum.request.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class RequestPrivateController {
    private final RequestServiceImpl requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto addRequest(@PathVariable @Positive Integer userId,
                                 @RequestParam @Positive Integer eventId) {
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable @Positive Integer userId,
                                    @PathVariable @Positive Integer requestId) {
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping
    List<RequestDto> getRequests(@PathVariable @Positive Integer userId) {
        return requestService.getRequests(userId);
    }
}
