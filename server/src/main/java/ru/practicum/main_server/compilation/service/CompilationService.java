package ru.practicum.main_server.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_server.compilation.model.Compilation;
import ru.practicum.main_server.compilation.model.CompilationAdminDto;
import ru.practicum.main_server.compilation.model.CompilationDto;

import java.util.Collection;

public interface CompilationService {

    Compilation create(CompilationDto compilationDto);

    void delete(Long compId);

    Compilation update(Long compId, CompilationAdminDto compilationAdminDto);

    Collection<Compilation> findAll(Boolean pinned, Pageable pageable);

    Compilation findById(Long compId);
}
