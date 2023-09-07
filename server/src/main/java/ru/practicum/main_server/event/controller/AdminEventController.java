package ru.practicum.main_server.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.OffsetBasedPageRequest;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.event.model.EventState;
import ru.practicum.main_server.event.model.EventUpdateDto;
import ru.practicum.main_server.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/events")
@Slf4j
@Validated
public class AdminEventController {

    private final EventService eventService;



    @GetMapping
    public List<Event> findEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                         @RequestParam(required = false) List<EventState> states,
                                         @RequestParam(required = false) List<Integer> categories,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        log.info("Get sorted events by admin");
        return eventService.findEventsByAdmin(users, states, categories, rangeStart, rangeEnd, pageable);
    }

    @PatchMapping("/{eventId}")
    public Event updateEventByAdmin(@PathVariable Long eventId, @Valid @RequestBody EventUpdateDto eventUpdateDto) {
        log.info("Update event id={} by admin", eventId);
        return eventService.updateEventByAdmin(eventId, eventUpdateDto);
    }
}