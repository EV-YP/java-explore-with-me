package ru.practicum.comment.service.external;

import ru.practicum.comment.dto.CommentDto;

import java.util.List;

public interface CommentExternalService {

    List<CommentDto> getComments(Integer eventId, Integer from, Integer size);

    CommentDto getComment(Integer commentId);
}
