package ru.practicum.comment.service.internal;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.Status;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentInternalServiceImpl implements CommentInternalService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public CommentDto addComment(NewCommentDto commentDto, Integer userId, Integer eventId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);

        if (event.getEventDate().isBefore(commentDto.getCreatedAt())) {
            throw new ValidationException("Event date is before comment date");
        }

        Request request = requestRepository.findRequestsByEventIdAndRequesterId(eventId, userId);
        if (request == null) {
            throw new NotFoundException("Request for user with id " + userId +
                    " and event with id " + eventId + " not found");
        }

        if (!request.getStatus().equals(State.CONFIRMED)) {
            throw new ValidationException("Request is not confirmed");
        }

        Comment comment = commentMapper.toComment(commentDto);
        comment.setStatus(Status.CREATED);
        comment.setAuthor(user);
        comment.setEvent(event);

        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto updateComment(UpdateCommentDto commentDto, Integer userId, Integer commentId) {
        User user = checkUser(userId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));

        if (!Objects.equals(comment.getAuthor().getId(), user.getId())) {
            throw new ValidationException("You are not allowed to update this comment");
        }

        comment.setText(commentDto.getText());
        comment.setStatus(Status.UPDATED);
        comment.setUpdatedAt(commentDto.getUpdatedAt());

        return commentMapper.toUpdatedCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Integer commentId, Integer userId) {
        User user = checkUser(userId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));
        if (!Objects.equals(comment.getAuthor().getId(), user.getId())) {
            throw new ValidationException("You are not allowed to delete this comment");
        }
        commentRepository.deleteById(commentId);
    }

    private User checkUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    private Event checkEvent(Integer id) {
        return eventRepository.findEventById(id)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found"));
    }
}
