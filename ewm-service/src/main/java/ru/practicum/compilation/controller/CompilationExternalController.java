package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationsListRequestParams;
import ru.practicum.compilation.service.external.CompilationExternalServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationExternalController {
    private final CompilationExternalServiceImpl compilationService;

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable Integer compId) {
        return compilationService.getCompilation(compId);
    }

    @GetMapping
    public List<CompilationDto> getCompilations(@ModelAttribute CompilationsListRequestParams params) {
        return compilationService.getCompilations(params);
    }
}
