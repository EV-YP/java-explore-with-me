package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointHitDto;
import ru.practicum.StatsRequestDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.service.StatServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatController {

    private final StatServiceImpl statService;

    @GetMapping("/stats")
    private List<ViewStatsDto> viewStats(@ModelAttribute @Valid StatsRequestDto statsRequestDto) {
        return statService.getStats(statsRequestDto);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    private EndpointHitDto saveHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        return statService.saveHit(endpointHitDto);
    }
}
