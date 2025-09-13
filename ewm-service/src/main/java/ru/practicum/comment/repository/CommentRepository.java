package ru.practicum.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment save(Comment comment);

    List<Comment> findCommentsByEventId(Integer eventId, Pageable pageable);

    Optional<Comment> findById(Integer commentId);

    Comment findCommentByEventIdAndAuthorId(Integer eventId, Integer authorId);

    Optional<Comment> findCommentById(Integer commentId);
}

