package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.service.internal.CommentInternalService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentInternalController {

    private final CommentInternalService commentInternalService;
    private static final String USER_ID = "X-User-Id";
    private static final String EVENT_ID = "X-Event-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@RequestBody NewCommentDto commentDto,
                                 @RequestHeader(USER_ID) Integer userId,
                                 @RequestHeader(EVENT_ID) Integer eventId) {
        return commentInternalService.addComment(commentDto, userId, eventId);
    }

    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@RequestHeader(USER_ID) Integer userId,
                                    @RequestBody UpdateCommentDto updateCommentDto,
                                    @PathVariable Integer commentId) {
        return commentInternalService.updateComment(updateCommentDto, userId, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@RequestHeader(USER_ID) Integer userId,
                              @PathVariable Integer commentId) {
        commentInternalService.deleteComment(commentId, userId);
    }
}
