package ru.practicum.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.comment.model.Status;
import ru.practicum.event.dto.EventCommentsDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private String text;

    private String authorName;

    private EventCommentsDto event;

    private LocalDateTime createdAt;

    private Status status;
}
