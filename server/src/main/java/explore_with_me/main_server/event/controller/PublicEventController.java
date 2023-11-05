package explore_with_me.main_server.event.controller;

import explore_with_me.main_server.OffsetBasedPageRequest;
import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.event.model.SortValue;
import explore_with_me.main_server.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Public event controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("events")
@Slf4j
@Validated
public class PublicEventController {

    private final EventService eventService;

    /**
     * Find all events by search
     * GET events
     * @param text          the text
     * @param categories    the categories list
     * @param paid          the paid
     * @param rangeStart    the range start
     * @param rangeEnd      the range end
     * @param onlyAvailable the only available
     * @param sort          the sort
     * @param from          the from
     * @param size          the size
     * @param request       the request
     * @return the list of events json
     */

    @GetMapping
    public List<Event> findAllBySearch(@RequestParam(required = false) String text,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false) Boolean paid,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                       @RequestParam(required = false) SortValue sort,
                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                       @Positive @RequestParam(defaultValue = "10") Integer size,
                                       HttpServletRequest request) {
        Pageable pageable;
        if (sort != null) {
            pageable = new OffsetBasedPageRequest(from, size, Sort.by(sort.getTitle()).descending());
        } else {
            pageable = new OffsetBasedPageRequest(from, size);
        }
        log.info("Get sorted events");
        return eventService.findAllBySearch(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable,
                request);
    }

    /**
     * Find event by id
     * GET events/{id}
     * @param id      the id
     * @param request the request
     * @return the event json
     */

    @GetMapping("/{id}")
    public Event findEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("Get event by id={}", id);
        return eventService.findEventById(id, request);
    }
}