package ru.practicum.comment.service.internal;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;

public interface CommentInternalService {
    CommentDto addComment(NewCommentDto commentDto, Integer userId, Integer eventId);

    CommentDto updateComment(UpdateCommentDto commentDto, Integer userId, Integer commentId);

    void deleteComment(Integer commentId, Integer userId);
}
