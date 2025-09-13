package ru.practicum.comment.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.service.external.CommentExternalService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentExternalController {

    private final CommentExternalService commentExternalService;
    private static final String EVENT_ID = "X-Event-Id";

    @GetMapping
    public List<CommentDto> getComments(@NotNull @RequestHeader(EVENT_ID) Integer eventId,
                                        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                        @RequestParam(defaultValue = "10") @Positive Integer size) {
        return commentExternalService.getComments(eventId, from, size);
    }

    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable Integer commentId) {
        return commentExternalService.getComment(commentId);
    }
}
