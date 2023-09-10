package ru.practicum.main_server.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.OffsetBasedPageRequest;
import ru.practicum.main_server.compilation.model.Compilation;
import ru.practicum.main_server.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Slf4j
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public Collection<Compilation> findAll(@RequestParam(required = false) Boolean pinned,
                                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        log.info("Get collection");
        return compilationService.findAll(pinned, pageable);
    }

    @GetMapping("/{compId}")
    public Compilation findById(@PathVariable Long compId) {
        log.info("Get collection id={}", compId);
        return compilationService.findById(compId);
    }
}