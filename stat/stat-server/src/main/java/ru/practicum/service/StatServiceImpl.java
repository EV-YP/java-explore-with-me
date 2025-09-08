package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.StatsRequestDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.mapper.HitsMapper;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatServerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatServerRepository statServerRepository;
    private final ViewStatsMapper viewStatsMapper;
    private final HitsMapper hitsMapper;

    @Override
    public List<ViewStatsDto> getStats(StatsRequestDto statsRequestDto) {

        List<ViewStats> stats;
        if (statsRequestDto.getUnique() == null) {
            stats = statServerRepository.getStats(statsRequestDto.getStart(), statsRequestDto.getEnd()).stream()
                    .filter(viewStats -> statsRequestDto.getUris().contains(viewStats.getUri()))
                    .toList();
        } else if (statsRequestDto.getUnique().equals(true)) {
            stats = statServerRepository.getStatsUnique(statsRequestDto.getStart(), statsRequestDto.getEnd(),
                    statsRequestDto.getUris());
        } else {
            stats = statServerRepository.getStats(statsRequestDto.getStart(), statsRequestDto.getEnd());
        }

        return stats.stream()
                .map(viewStatsMapper::mapDtoToViewStats)
                .toList();
    }

    @Override
    public void saveHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = hitsMapper.mapDtoToEndpointHit(endpointHitDto);
        statServerRepository.saveHit(endpointHit);
    }
}
