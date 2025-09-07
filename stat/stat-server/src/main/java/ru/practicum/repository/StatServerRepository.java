package ru.practicum.repository;

import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatServerRepository {

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end);

    List<ViewStats> getStatsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    void saveHit(EndpointHit endpointHit);
}
