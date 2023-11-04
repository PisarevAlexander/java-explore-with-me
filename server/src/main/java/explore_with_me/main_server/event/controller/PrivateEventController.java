package explore_with_me.main_server.event.controller;

import explore_with_me.main_server.OffsetBasedPageRequest;
import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.event.model.EventDto;
import explore_with_me.main_server.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import explore_with_me.main_server.event.model.EventUpdateDto;
import explore_with_me.main_server.request.model.RequestDto;
import explore_with_me.main_server.request.model.RequestUpdateDto;
import explore_with_me.main_server.request.model.RequestUpdateStatusDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users/{userId}/events")
@Slf4j
@Validated
public class PrivateEventController {

    private final EventService eventService;

    @GetMapping
    public List<Event> findAll(@PathVariable Long userId,
                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                               @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get events for userId = {}", userId);
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        return eventService.findAll(userId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@Valid @RequestBody EventDto eventDto, @PathVariable Long userId) {
        log.info("Create event by userId = {}", userId);
        return eventService.save(userId, eventDto);
    }

    @GetMapping("/{eventId}")
    public Event findById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Get event by userId = {}, eventId = {}", userId, eventId);
        return eventService.findByEventAndUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public Event update(@PathVariable Long userId, @PathVariable Long eventId,
                        @Valid @RequestBody EventUpdateDto eventUpdateDtoDto) {
        log.info("Update event by userId = {}, eventId = {}", userId, eventId);
        return eventService.update(userId, eventId, eventUpdateDtoDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> findRequest(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Get request for userId = {}, eventId = {}", userId, eventId);
        return eventService.findRequest(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public RequestUpdateDto updateRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                          @Valid @RequestBody RequestUpdateStatusDto requestUpdateStatusDto) {
        log.info("Update request for userId = {}, eventId = {}", userId, eventId);
        return eventService.updateRequest(userId, eventId, requestUpdateStatusDto);
    }
}