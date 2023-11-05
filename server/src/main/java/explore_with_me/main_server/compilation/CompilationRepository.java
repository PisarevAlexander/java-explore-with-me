package explore_with_me.main_server.compilation;

import explore_with_me.main_server.compilation.model.Compilation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Compilation repository
 */

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    /**
     * Find all compilation
     * @param pinned   the pinned
     * @param pageable the pageable
     * @return the page of compilation
     */

    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}