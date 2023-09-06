package ru.practicum.main_server.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.event.model.EventDto;
import ru.practicum.main_server.event.model.EventState;
import ru.practicum.main_server.event.model.EventUpdateDto;
import ru.practicum.main_server.request.model.Request;
import ru.practicum.main_server.request.model.RequestUpdateDto;
import ru.practicum.main_server.request.model.RequestUpdateStatusDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<Event> findAll(Long userId, Pageable pageable);

    Event save(Long userId, EventDto eventDto);

    Event findById(Long userId, Long eventId);

    Event update(Long userId, Long eventId, EventUpdateDto eventUpdateDtoDto);

    List<Request> findRequest(Long userId, Long eventId);

    RequestUpdateDto updateRequest(Long userId, Long eventId, RequestUpdateStatusDto requestUpdateStatusDto);

    List<Event> findAllBySearch(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable,
                                HttpServletRequest request);

    Event findEventById(Long id, HttpServletRequest request);

    List<Event> findEventsByAdmin(List<Long> users, List<EventState> states, List<Integer> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    Event updateEventByAdmin(Long eventId, EventUpdateDto eventUpdateDto);
}
