package ru.practicum.main_server.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.compilation.model.Compilation;
import ru.practicum.main_server.compilation.model.CompilationDto;
import ru.practicum.main_server.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Slf4j
@Validated
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public Compilation create(@Valid @RequestBody CompilationDto compilationDto) {
        log.info("Create compilation={}", compilationDto);
        return compilationService.create(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable Long compId) {
        log.info("Delete compilation id={}", compId);
        compilationService.delete(compId);
    }

    @PatchMapping("/{compId}")
    public Compilation update(@PathVariable Long compId, @RequestBody CompilationDto compilationDto) {
        log.info("Update compilation id={}", compId);
        return compilationService.update(compId, compilationDto);
    }
}