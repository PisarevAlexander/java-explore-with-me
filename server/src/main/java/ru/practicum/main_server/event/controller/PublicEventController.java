package ru.practicum.main_server.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.OffsetBasedPageRequest;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.event.model.SortValue;
import ru.practicum.main_server.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("events")
@Slf4j
@Validated
public class PublicEventController {

    private final EventService eventService;

    @GetMapping
    public List<Event> findAllBySearch(@RequestParam(required = false) String text,
                                       @RequestParam(required = false) List<Integer> categories,
                                       @RequestParam(required = false) Boolean paid,
                                       @RequestParam(required = false) LocalDateTime rangeStart,
                                       @RequestParam(required = false) LocalDateTime rangeEnd,
                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                       @RequestParam(required = false) SortValue sort,
                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                       @Positive @RequestParam(defaultValue = "10") Integer size,
                                       HttpServletRequest request) {
        Pageable pageable = new OffsetBasedPageRequest(from, size, Sort.by(sort.getTitle()).descending());
        log.info("Get sorted events");
        return eventService.findAllBySearch(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable,
                request);
    }

    @GetMapping("/{id}")
    public Event findEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("Get event by id={}", id);
        return eventService.findEventById(id, request);
    }
}