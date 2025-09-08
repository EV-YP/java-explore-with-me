package ru.practicum.compilation.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationsListRequestParams;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.StatsClient;
import ru.practicum.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Service
@RequiredArgsConstructor
public class CompilationExternalServiceImpl implements CompilationExternalService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;
    private final StatsClient statsClient;

    @Override
    public CompilationDto getCompilation(Integer compilationId) {
        Compilation compilation = checkCompilation(compilationId);
        CompilationDto compilationDto = compilationMapper.toDto(compilation);

        List<Event> events = compilation.getEvents().stream().toList();

        if ((events != null) && (!events.isEmpty())) {
            Map<Integer, Integer> views = getEvents(events);
            compilationDto.getEvents().stream()
                    .peek(eventShortDto -> eventShortDto.setViews(views.get(eventShortDto.getId())));
        }

        return compilationDto;
    }

    @Override
    public List<CompilationDto> getCompilations(CompilationsListRequestParams params) {
        int page = (int) Math.floor((double) params.getFrom() / params.getSize());
        Pageable pageable = PageRequest.of(page, params.getSize());
        Page<Compilation> compilations = params.getPinned() == null
                ? compilationRepository.findAll(pageable)
                : compilationRepository.findCompilationByPinned(params.getPinned(), pageable);

        List<Event> events = compilations.stream()
                .flatMap(c -> c.getEvents().stream())
                .distinct()
                .toList();

        List<CompilationDto> compilationDtos = compilations.getContent().stream()
                .map(compilationMapper::toDto)
                .toList();

        if ((events != null) && (!events.isEmpty())) {
            Map<Integer, Integer> views = getEvents(events);
            compilationDtos = compilationDtos.stream().peek(compilationDto -> {
                        compilationDto.getEvents().stream()
                                .peek(eventShortDto -> eventShortDto.setViews(views.get(eventShortDto.getId())
                                ));
                    }
            ).toList();
        }

        return compilationDtos;
    }

    private Compilation checkCompilation(Integer compId) {
        return compilationRepository.findCompilationById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id " + compId + " not found"));
    }

    private Map<Integer, Integer> getEvents(List<Event> events) {
        if (events == null) {
            return Collections.emptyMap();
        }

        List<Integer> ids = events.stream()
                .map(Event::getId)
                .toList();
        List<Event> events1 = eventRepository.findEventsByIdIn(ids);

        List<String> uris = events.stream()
                .map(event -> "/events/" + event.getId())
                .distinct()
                .toList();
        LocalDateTime start = LocalDateTime.now().minusMonths(6);
        LocalDateTime end = LocalDateTime.now().plusMinutes(1);
        List<ViewStatsDto> viewStatsDtoS = statsClient.viewStats(start, end, uris, true);

        Map<Integer, Integer> views = new HashMap<>();
        if (!viewStatsDtoS.isEmpty()) {
            for (ViewStatsDto viewStatsDto : viewStatsDtoS) {
                int eventId = parseInt(viewStatsDto.getUri().substring("/events/".length()));
                views.put(eventId, viewStatsDto.getHits());
            }
        }

        return views;
    }
}

