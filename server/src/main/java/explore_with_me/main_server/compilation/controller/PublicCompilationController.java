package explore_with_me.main_server.compilation.controller;

import explore_with_me.main_server.compilation.model.Compilation;
import explore_with_me.main_server.compilation.service.CompilationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import explore_with_me.main_server.OffsetBasedPageRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

/**
 * Public compilation controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Slf4j
public class PublicCompilationController {

    private final CompilationService compilationService;

    /**
     * Find all compilation
     * @param pinned the pinned
     * @param from   the from
     * @param size   the size
     * @return the collection compilation json
     */

    @GetMapping
    public Collection<Compilation> findAll(@RequestParam(required = false) Boolean pinned,
                                           @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                           @Positive @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        log.info("Get collection");
        return compilationService.findAll(pinned, pageable);
    }

    /**
     * Find  compilation by id
     * @param compId the compilation id
     * @return the compilation json
     */

    @GetMapping("/{compId}")
    public Compilation findById(@PathVariable Long compId) {
        log.info("Get collection id={}", compId);
        return compilationService.findById(compId);
    }
}