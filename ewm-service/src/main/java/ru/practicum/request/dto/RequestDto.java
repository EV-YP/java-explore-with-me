package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.StatsRequestDto;
import ru.practicum.event.model.State;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    private Integer id;

    @JsonFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private final LocalDateTime created = LocalDateTime.now();

    private Integer event;

    private Integer requester;

    private State status;
}
