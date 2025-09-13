package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.StatsRequestDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {

    @NotNull
    @Size(max = 5000)
    @JsonProperty("text")
    private String text;

    @DateTimeFormat(pattern = StatsRequestDto.DATE_FORMAT)
    private final LocalDateTime createdAt = LocalDateTime.now();
}
