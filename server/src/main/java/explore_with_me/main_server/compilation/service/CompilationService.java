package explore_with_me.main_server.compilation.service;

import explore_with_me.main_server.compilation.model.Compilation;
import org.springframework.data.domain.Pageable;
import explore_with_me.main_server.compilation.model.CompilationAdminDto;
import explore_with_me.main_server.compilation.model.CompilationDto;

import java.util.Collection;

/**
 * The interface Compilation service
 */

public interface CompilationService {

    /**
     * Create compilation
     * @param compilationDto the compilation dto
     * @return the compilation
     */

    Compilation create(CompilationDto compilationDto);

    /**
     * Delete compilation
     * @param compId the compilation id
     */

    void delete(Long compId);

    /**
     * Update compilation
     * @param compId              the compilation id
     * @param compilationAdminDto the compilation admin dto
     * @return the compilation
     */

    Compilation update(Long compId, CompilationAdminDto compilationAdminDto);

    /**
     * Find all collection
     * @param pinned   the pinned
     * @param pageable the pageable
     * @return the collection
     */

    Collection<Compilation> findAll(Boolean pinned, Pageable pageable);

    /**
     * Find compilation by id
     * @param compId the compilation id
     * @return the compilation
     */

    Compilation findById(Long compId);
}
