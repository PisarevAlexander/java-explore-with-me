package explore_with_me.main_server.compilation.controller;

import explore_with_me.main_server.compilation.model.Compilation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import explore_with_me.main_server.compilation.model.CompilationAdminDto;
import explore_with_me.main_server.compilation.model.CompilationDto;
import explore_with_me.main_server.compilation.service.CompilationService;

import javax.validation.Valid;

/**
 * Admin compilation controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Slf4j
@Validated
public class AdminCompilationController {

    private final CompilationService compilationService;

    /**
     * Create compilation
     * POST /admin/compilations
     * @param compilationDto the compilation DTO
     * @return the compilation json
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Compilation create(@Valid @RequestBody CompilationDto compilationDto) {
        log.info("Create compilation={}", compilationDto);
        return compilationService.create(compilationDto);
    }

    /**
     * Delete compilation
     * DELETE /admin/compilations/{compId}
     * @param compId the compilation id
     */

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        log.info("Delete compilation id={}", compId);
        compilationService.delete(compId);
    }

    /**
     * Update compilation
     * PATCH /admin/compilations/{compId}
     * @param compId              the compilation id
     * @param compilationAdminDto the compilation admin DTO
     * @return the compilation json
     */

    @PatchMapping("/{compId}")
    public Compilation update(@PathVariable Long compId,
                              @Valid @RequestBody CompilationAdminDto compilationAdminDto) {
        log.info("Update compilation id={}", compId);
        return compilationService.update(compId, compilationAdminDto);
    }
}