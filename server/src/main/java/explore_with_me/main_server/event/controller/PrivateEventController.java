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


/**
 * Private event controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("users/{userId}/events")
@Slf4j
@Validated
public class PrivateEventController {

    private final EventService eventService;

    /**
     * Find all list
     * GET users/{userId}/events
     * @param userId the user id
     * @param from   the from
     * @param size   the size
     * @return the list of event json
     */

    @GetMapping
    public List<Event> findAll(@PathVariable Long userId,
                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                               @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get events for userId = {}", userId);
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        return eventService.findAll(userId, pageable);
    }

    /**
     * Create event
     * POST users/{userId}/events
     * @param eventDto the event dto
     * @param userId   the user id
     * @return the event json
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@Valid @RequestBody EventDto eventDto, @PathVariable Long userId) {
        log.info("Create event by userId = {}", userId);
        return eventService.save(userId, eventDto);
    }

    /**
     * Find event by id
     * GET users/{userId}/events/{eventId}
     * @param userId  the user id
     * @param eventId the event id
     * @return the event json
     */

    @GetMapping("/{eventId}")
    public Event findById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Get event by userId = {}, eventId = {}", userId, eventId);
        return eventService.findByEventAndUserId(userId, eventId);
    }

    /**
     * Update event by id
     * PATCH users/{userId}/events/{eventId}
     * @param userId            the user id
     * @param eventId           the event id
     * @param eventUpdateDtoDto the event update DTO
     * @return the event json
     */

    @PatchMapping("/{eventId}")
    public Event update(@PathVariable Long userId, @PathVariable Long eventId,
                        @Valid @RequestBody EventUpdateDto eventUpdateDtoDto) {
        log.info("Update event by userId = {}, eventId = {}", userId, eventId);
        return eventService.update(userId, eventId, eventUpdateDtoDto);
    }

    /**
     * Find requests
     * GET users/{userId}/events/{eventId}/requests
     * @param userId  the user id
     * @param eventId the event id
     * @return the list of requests json
     */

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> findRequest(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Get request for userId = {}, eventId = {}", userId, eventId);
        return eventService.findRequest(userId, eventId);
    }

    /**
     * Update request
     * PATCH users/{userId}/events/{eventId}/requests
     * @param userId                 the user id
     * @param eventId                the event id
     * @param requestUpdateStatusDto the request update status dto
     * @return the request update dto json
     */

    @PatchMapping("/{eventId}/requests")
    public RequestUpdateDto updateRequest(@PathVariable Long userId, @PathVariable Long eventId,
                                          @Valid @RequestBody RequestUpdateStatusDto requestUpdateStatusDto) {
        log.info("Update request for userId = {}, eventId = {}", userId, eventId);
        return eventService.updateRequest(userId, eventId, requestUpdateStatusDto);
    }
}