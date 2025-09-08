package ru.practicum.compilation.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto addCompilation(NewCompilationDto compilationDto) {
        Compilation compilation = compilationMapper.toCompilation(compilationDto);

        if (compilationDto.getEvents() != null) {
            Set<Event> events = setEvents(compilationDto.getEvents());
            compilation.setEvents(events);
        }

        return compilationMapper.toDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Integer compilationId) {
        compilationRepository.delete(checkCompilation(compilationId));
    }

    @Override
    public CompilationDto updateCompilation(Integer compilationId, UpdateCompilationRequest compilationDto) {
        Compilation compilation = checkCompilation(compilationId);

        if (compilationDto.getEvents() != null) {
            Set<Event> events = setEvents(compilationDto.getEvents());
            compilation.setEvents(events);
        }

        return compilationMapper.toDto(compilationRepository.save(compilation));
    }

    private Compilation checkCompilation(Integer compilationId) {
        return compilationRepository.findCompilationById(compilationId)
                .orElseThrow(() -> new NotFoundException("Compilation with id " + compilationId + " not found"));
    }

    private Set<Event> setEvents(List<Integer> eventIds) {
        return new HashSet<>(eventRepository.findEventsByIdIn(eventIds));
    }
}
