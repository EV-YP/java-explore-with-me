package ru.practicum.event.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.event.model.State;
import ru.practicum.StatsRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEventsDto {

    private List<Integer> users;

    private List<State> states;

    private List<Integer> categories;

    @DateTimeFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private LocalDateTime rangeStart = LocalDateTime.now();

    @DateTimeFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private LocalDateTime rangeEnd;

    @PositiveOrZero
    private Integer from = 0;

    @Positive
    private Integer size = 10;
}
