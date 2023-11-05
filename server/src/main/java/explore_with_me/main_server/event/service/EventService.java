package explore_with_me.main_server.event.service;

import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.event.model.EventDto;
import explore_with_me.main_server.event.model.EventState;
import org.springframework.data.domain.Pageable;
import explore_with_me.main_server.event.model.EventUpdateDto;
import explore_with_me.main_server.request.model.RequestDto;
import explore_with_me.main_server.request.model.RequestUpdateDto;
import explore_with_me.main_server.request.model.RequestUpdateStatusDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Event service
 */

public interface EventService {

    /**
     * Get event by id
     * @param eventId the event id
     * @return the event
     */

    Event getById(Long eventId);

    /**
     * Find all event
     * @param userId   the user id
     * @param pageable the pageable
     * @return the list of event
     */

    List<Event> findAll(Long userId, Pageable pageable);

    /**
     * Save event
     * @param userId   the user id
     * @param eventDto the event dto
     * @return the event
     */

    Event save(Long userId, EventDto eventDto);

    /**
     * Find event by event id and user id
     * @param userId  the user id
     * @param eventId the event id
     * @return the event
     */

    Event findByEventAndUserId(Long userId, Long eventId);

    /**
     * Update event
     * @param userId            the user id
     * @param eventId           the event id
     * @param eventUpdateDtoDto the event update DTO
     * @return the event
     */

    Event update(Long userId, Long eventId, EventUpdateDto eventUpdateDtoDto);

    /**
     * Find request list
     * @param userId  the user id
     * @param eventId the event id
     * @return the list of request
     */

    List<RequestDto> findRequest(Long userId, Long eventId);

    /**
     * Update request
     * @param userId                 the user id
     * @param eventId                the event id
     * @param requestUpdateStatusDto the request update status dto
     * @return the request DTO
     */

    RequestUpdateDto updateRequest(Long userId, Long eventId, RequestUpdateStatusDto requestUpdateStatusDto);

    /**
     * Find all events
     * @param text          the text
     * @param categories    the categories
     * @param paid          the paid
     * @param rangeStart    the range start
     * @param rangeEnd      the range end
     * @param onlyAvailable the only available
     * @param pageable      the pageable
     * @param request       the request
     * @return the list of events
     */

    List<Event> findAllBySearch(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable,
                                HttpServletRequest request);

    /**
     * Find event by id
     * @param id      the id
     * @param request the request
     * @return the event
     */

    Event findEventById(Long id, HttpServletRequest request);

    /**
     * Find events by admin
     * @param users      the users
     * @param states     the states
     * @param categories the categories
     * @param rangeStart the range start
     * @param rangeEnd   the range end
     * @param pageable   the pageable
     * @return the list of events
     */

    List<Event> findEventsByAdmin(List<Long> users, List<EventState> states, List<Integer> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    /**
     * Update event by admin
     * @param eventId        the event id
     * @param eventUpdateDto the event update dto
     * @return the event
     */

    Event updateEventByAdmin(Long eventId, EventUpdateDto eventUpdateDto);
}