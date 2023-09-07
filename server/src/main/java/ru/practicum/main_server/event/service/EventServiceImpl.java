package ru.practicum.main_server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_server.category.model.Category;
import ru.practicum.main_server.category.service.CategoryService;
import ru.practicum.main_server.client.StatServerClient;
import ru.practicum.main_server.event.EventRepository;
import ru.practicum.main_server.event.model.*;
import ru.practicum.main_server.exception.BadRequestException;
import ru.practicum.main_server.exception.ConflictException;
import ru.practicum.main_server.exception.NotFoundException;
import ru.practicum.main_server.location.Location;
import ru.practicum.main_server.location.LocationRepository;
import ru.practicum.main_server.request.RequestRepository;
import ru.practicum.main_server.request.model.*;
import ru.practicum.main_server.user.UserRepository;
import ru.practicum.main_server.user.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main_server.event.model.EventState.*;
import static ru.practicum.main_server.request.model.RequestStatus.CONFIRMED;
import static ru.practicum.main_server.request.model.RequestStatus.REJECTED;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final StatServerClient statServerClient;


    @Transactional
    @Override
    public List<Event> findAll(Long userId, Pageable pageable) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        return eventRepository.findByInitiator(user, pageable).getContent();
    }

    @Transactional
    @Override
    public Event save(Long userId, EventDto eventDto) {
        if (eventDto.getEventDate().isBefore(LocalDateTime.now())) {
            throw new ConflictException("Event date must be in past");
        }

        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));

        Category category = categoryService.findById(eventDto.getCategory());

        Location location = locationRepository.findByLatAndLon(eventDto.getLocation().getLat(),
                eventDto.getLocation().getLon())
                .orElseGet(() -> locationRepository.save(eventDto.getLocation()));
        eventDto.setLocation(location);
        Event event = EventMapper.toEvent(eventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setConfirmedRequests(0L);
        event.setState(PENDING);
        event.setCreatedOn(LocalDateTime.now());
        event.setViews(0L);
        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public Event findById(Long userId, Long eventId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
        return eventRepository.findByIdAndInitiator(eventId, user)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
    }

    @Transactional
    @Override
    public Event update(Long userId, Long eventId, EventUpdateDto eventUpdateDto) {
        if (eventUpdateDto.getEventDate() != null && eventUpdateDto.getEventDate()
                .isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Event date must be in past");
        }

        Event event = findById(userId, eventId);

        if (event.getState().equals(PUBLISHED)) {
            throw new NotFoundException("Event is already published");
        }

        if (eventUpdateDto.getStateAction() != null) {
            switch (eventUpdateDto.getStateAction()) {
                case CANCEL_REVIEW:
                    if (!event.getState().equals(PENDING)) {
                        event.setState(CANCELED);
                        break;
                    } else {
                        throw new ConflictException("Event not pending");
                    }
                case SEND_TO_REVIEW:
                    if (event.getState().equals(CANCELED)) {
                        event.setState(PENDING);
                        break;
                    } else {
                        throw new ConflictException("Event not cancel");
                    }
                default:
                    throw new ConflictException("State out of range");
            }
        }
        return eventRepository.save(updateEvent(eventUpdateDto, event));
    }

    @Transactional
    @Override
    public List<Request> findRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
        if (!event.getInitiator().equals(user)) {
            throw new ConflictException("User id=" + userId + " mot create event id=" + eventId);
        }
        return requestRepository.findAllByRequesterAndEvent(user, event);
    }

    @Transactional
    @Override
    public RequestUpdateDto updateRequest(Long userId, Long eventId, RequestUpdateStatusDto requestUpdateStatusDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
        if (!event.getInitiator().equals(user)) {
            throw new ConflictException("User id=" + userId + " mot create event id=" + eventId);
        }
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new ConflictException("Accept for request mot required");
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ConflictException("Limit of requests");
        }
        List<Request> requests = requestRepository.findAllByIdInAndStatus(requestUpdateStatusDto.getRequestIds(),
                RequestStatus.PENDING);
        if (requests.isEmpty()) {
            throw new ConflictException("User may change only waiting requests");
        }

        RequestUpdateDto requestUpdateDto = new RequestUpdateDto(new ArrayList<>(), new ArrayList<>());
        if (requestUpdateStatusDto.getStatus().equals(REJECTED)) {
            requests.forEach(request -> request.setStatus(REJECTED));
            requestUpdateDto.setRejectedRequests(requestRepository.saveAll(requests)
                    .stream()
                    .map(RequestMapper::toRequestDto)
                    .collect(Collectors.toList()));
        }
        if (requestUpdateStatusDto.getStatus().equals(CONFIRMED)) {
            for (Request r : requests) {
                if (event.getConfirmedRequests().equals(event.getParticipantLimit())) {
                    r.setStatus(REJECTED);
                    requestUpdateDto.getRejectedRequests().add(RequestMapper.toRequestDto(requestRepository.save(r)));
                } else {
                    r.setStatus(CONFIRMED);
                    requestUpdateDto.getConfirmedRequests().add(RequestMapper.toRequestDto(requestRepository.save(r)));
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                }
            }
        }
        eventRepository.save(event);
        return requestUpdateDto;
    }

    @Transactional
    @Override
    public List<Event> findAllBySearch(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable,
                                       HttpServletRequest request) {
        if (rangeEnd != null && rangeStart != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Bad datetime range");
        }

        Page<Event> events;
        if (rangeEnd == null && rangeStart == null) {
            events = eventRepository.findByPublicParameterNoDate(text, categories, paid, LocalDateTime.now(), pageable);
        } else {
            events = eventRepository.findByPublicParameter(text, categories, paid, rangeStart, rangeEnd, pageable);
        }

        if (onlyAvailable) {
            return events.stream()
                    .filter(e -> e.getParticipantLimit() > e.getConfirmedRequests() || e.getParticipantLimit() == 0)
                    .collect(Collectors.toList());
        }

        statServerClient.createStat(request);
        return events.getContent();
    }

    @Transactional
    @Override
    public Event findEventById(Long id, HttpServletRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " not found"));
        if (!event.getState().equals(PUBLISHED)) {
            throw new NotFoundException("Event must be published");
        }
        statServerClient.createStat(request);
        event.setViews(statServerClient.getView(event.getId()));
        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public List<Event> findEventsByAdmin(List<Long> users, List<EventState> states, List<Integer> categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable) {
        if (rangeEnd != null && rangeStart != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Bad datetime range");
        }

        return eventRepository.findByAdminParameter(users, states, categories, rangeStart, rangeEnd, pageable)
                .getContent();
    }

    @Transactional
    @Override
    public Event updateEventByAdmin(Long eventId, EventUpdateDto eventUpdateDto) {
        if (eventUpdateDto.getEventDate() != null && eventUpdateDto.getEventDate().isAfter(LocalDateTime.now().plusHours(1))) {
            throw new BadRequestException("Bad datetime range");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));

        if (eventUpdateDto.getStateAction() != null) {
            if (event.getState().equals(PUBLISHED)) {
                throw new ConflictException("Event already published");
            }
            switch (eventUpdateDto.getStateAction()) {
                case REJECT_EVENT:
                    event.setState(CANCELED);
                    break;
                case PUBLISH_EVENT:
                    event.setState(PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                default:
                    throw new ConflictException("State not valid");
            }
        }
        return eventRepository.save(updateEvent(eventUpdateDto, event));
    }

    private Event updateEvent(EventUpdateDto eventUpdateDto, Event event) {
        if (eventUpdateDto.getCategory() != null) {
            event.setCategory(categoryService.findById(eventUpdateDto.getCategory()));
        }
        if (eventUpdateDto.getAnnotation() != null) {
            event.setAnnotation(eventUpdateDto.getAnnotation());
        }
        if (eventUpdateDto.getDescription() != null) {
            event.setDescription(eventUpdateDto.getDescription());
        }
        if (eventUpdateDto.getLocation() != null) {
            event.setLocation(eventUpdateDto.getLocation());
        }
        if (eventUpdateDto.getPaid() != null) {
            event.setPaid(eventUpdateDto.getPaid());
        }
        if (eventUpdateDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        }
        if (eventUpdateDto.getRequestModeration() != null) {
            event.setRequestModeration(eventUpdateDto.getRequestModeration());
        }
        if (eventUpdateDto.getTitle() != null) {
            event.setTitle(eventUpdateDto.getTitle());
        }
        return event;
    }
}