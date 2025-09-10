package ru.practicum.event.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.StatsRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventExternalParams {

    private String text;

    public List<Integer> categories;

    private Boolean paid;

    @DateTimeFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private LocalDateTime rangeStart;

    @DateTimeFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private LocalDateTime rangeEnd;

    private Boolean onlyAvailable = false;

    private String sort;

    @PositiveOrZero
    private Integer from = 0;

    @Positive
    private Integer size = 10;
}
