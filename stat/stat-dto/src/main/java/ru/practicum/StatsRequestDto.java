package ru.practicum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsRequestDto {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @DateTimeFormat(pattern = DATE_FORMAT)
    @NotNull(message = "Нужно указать дату и время начала интервала")
    LocalDateTime start;

    @DateTimeFormat(pattern = DATE_FORMAT)
    @NotNull(message = "Нужно указать дату и время конца интервала")
    LocalDateTime end;

    List<String> uris;

    Boolean unique;
}
