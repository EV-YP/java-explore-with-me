package ru.practicum.comment.mapper;

import org.mapstruct.Mapper;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.dto.UpdatedCommentDto;
import ru.practicum.comment.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toCommentDto(Comment comment);

    Comment toComment(NewCommentDto commentDto);

    UpdatedCommentDto toUpdatedCommentDto(Comment comment);

    Comment toComment(UpdateCommentDto commentDto);
}
