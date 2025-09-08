package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    CompilationDto toDto(Compilation compilation);

    Compilation toCompilation(CompilationDto dto);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "id", ignore = true)
    Compilation toCompilation(NewCompilationDto compilation);
}
