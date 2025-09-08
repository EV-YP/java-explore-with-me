package ru.practicum.service;

import ru.practicum.EndpointHitDto;
import ru.practicum.StatsRequestDto;
import ru.practicum.ViewStatsDto;

import java.util.List;

public interface StatService {

    List<ViewStatsDto> getStats(StatsRequestDto statsRequestDto);

    EndpointHitDto saveHit(EndpointHitDto endpointHitDto);
}
