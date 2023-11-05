package explore_with_me.stat_client.controller;

import explore_with_me.stat_client.client.StatClient;
import explore_with_me.stat_dto.StatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Stat controller
 */

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatController {

    private final StatClient statClient;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Create stat response entity.
     * POST /hit
     * @param statDto the stat dto
     * @return the response entity
     */

    @PostMapping("/hit")
    public ResponseEntity<Object> createStat(@Valid @RequestBody StatDto statDto) {
        log.info("Post statistic request");
        return statClient.createStat(statDto);
    }

    /**
     * Get stats
     * GET /stats
     * @param start  the start
     * @param end    the end
     * @param uris   the uris
     * @param unique the unique
     * @return the stats
     */

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam @NotNull @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
                                           @RequestParam @NotNull @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("Get statistic request");
        return statClient.getStat(start, end, uris, unique);
    }

    /**
     * Find view response entity.
     * GET /view/{eventId}
     * @param eventId the event id
     * @return the response entity
     */

    @GetMapping("/view/{eventId}")
    public ResponseEntity<Object> findView(@PathVariable Long eventId) {
        return statClient.findView(eventId);
    }
}