package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.StatsRequestDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.HitsMapper;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatServerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatServerRepository statServerRepository;
    private final ViewStatsMapper viewStatsMapper;
    private final HitsMapper hitsMapper;

    @Override
    public List<ViewStatsDto> getStats(StatsRequestDto statsRequestDto) {
        if (statsRequestDto.getStart().isAfter(statsRequestDto.getEnd())) {
            throw new ValidationException("Дата начала не может быть позже даты окончания");
        }

        List<ViewStatsDto> stats = new ArrayList<>();
        if (statsRequestDto.getUnique() != null && statsRequestDto.getUnique()) {
            stats = statServerRepository.getStatsUnique(statsRequestDto.getStart(), statsRequestDto.getEnd(),
                            statsRequestDto.getUris()).stream()
                    .map(viewStatsMapper::mapDtoToViewStats)
                    .toList();
        }

        if (statsRequestDto.getUnique() == null || statsRequestDto.getUnique().equals(false)) {
            stats = statServerRepository.getStats(statsRequestDto.getStart(), statsRequestDto.getEnd()).stream()
                    .filter(viewStats -> statsRequestDto.getUris().contains(viewStats.getUri()))
                    .map(viewStatsMapper::mapDtoToViewStats)
                    .toList();
        }

        return stats;
    }

    @Override
    public void saveHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = hitsMapper.mapDtoToEndpointHit(endpointHitDto);
        statServerRepository.saveHit(endpointHit);
    }
}
