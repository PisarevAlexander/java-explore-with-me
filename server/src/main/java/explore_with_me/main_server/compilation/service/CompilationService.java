package explore_with_me.main_server.compilation.service;

import explore_with_me.main_server.compilation.model.Compilation;
import org.springframework.data.domain.Pageable;
import explore_with_me.main_server.compilation.model.CompilationAdminDto;
import explore_with_me.main_server.compilation.model.CompilationDto;

import java.util.Collection;

public interface CompilationService {

    Compilation create(CompilationDto compilationDto);

    void delete(Long compId);

    Compilation update(Long compId, CompilationAdminDto compilationAdminDto);

    Collection<Compilation> findAll(Boolean pinned, Pageable pageable);

    Compilation findById(Long compId);
}
