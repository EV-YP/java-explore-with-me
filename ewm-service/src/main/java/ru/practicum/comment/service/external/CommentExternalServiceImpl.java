package ru.practicum.comment.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.Status;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentExternalServiceImpl implements CommentExternalService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final EventRepository eventRepository;

    @Override
    public List<CommentDto> getComments(Integer eventId, Integer from, Integer size) {

        isEventExist(eventId);
        Pageable pageable = PageRequest.of(from, size);

        return commentRepository.findCommentsByEventId(eventId, pageable).stream()
                .map(commentMapper::toCommentDto)
                .toList();
    }

    @Override
    public CommentDto getComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));

        if (comment.getStatus().equals(Status.CREATED)) {
            return commentMapper.toCommentDto(comment);
        } else {
            return commentMapper.toUpdatedCommentDto(comment);
        }
    }

    private void isEventExist(Integer eventId) {

        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Event with id " + eventId + " not found");
        }
    }
}
