package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.State;
import ru.practicum.StatsRequestDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {

    private Integer id;

    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    @JsonFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    @JsonFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private LocalDateTime publishedOn = LocalDateTime.now();

    private Boolean requestModeration;

    private State state;

    private String title;

    private Integer views;
}
