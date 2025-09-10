package ru.practicum.compilation.service.external;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationsListRequestParams;

import java.util.List;

public interface CompilationExternalService {
    CompilationDto getCompilation(Integer compId);

    List<CompilationDto> getCompilations(CompilationsListRequestParams params);
}
