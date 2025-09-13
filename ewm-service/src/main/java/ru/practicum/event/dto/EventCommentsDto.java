package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCommentsDto {

    private Integer id;

    private String title;

    private String annotation;

    private String description;

    private LocalDateTime eventDate;
}
