package ru.practicum.compilation.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CompilationsListRequestParams {

    private Boolean pinned;

    @PositiveOrZero
    private Integer from = 0;

    @Positive
    private Integer size = 10;
}
