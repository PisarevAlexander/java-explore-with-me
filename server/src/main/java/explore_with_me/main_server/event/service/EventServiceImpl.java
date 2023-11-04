package explore_with_me.main_server.event.service;

import explore_with_me.main_server.category.model.Category;
import explore_with_me.main_server.category.service.CategoryService;
import explore_with_me.main_server.client.StatServerClient;
import explore_with_me.main_server.event.EventRepository;
import explore_with_me.main_server.event.model.*;
import explore_with_me.main_server.exception.BadRequestException;
import explore_with_me.main_server.exception.ConflictException;
import explore_with_me.main_server.exception.NotFoundException;
import explore_with_me.main_server.location.Location;
import explore_with_me.main_server.location.LocationRepository;
import explore_with_me.main_server.request.RequestRepository;
import explore_with_me.main_server.request.model.*;
import explore_with_me.main_server.user.model.User;
import explore_with_me.main_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static explore_with_me.main_server.request.model.RequestStatus.CONFIRMED;
import static explore_with_me.main_server.request.model.RequestStatus.REJECTED;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final StatServerClient statServerClient;

    @Override
    public List<Event> findAll(Long userId, Pageable pageable) {
        User user = userService.getById(userId);
        return eventRepository.findByInitiator(user, pageable).getContent();
    }

    @Override
    public Event save(Long userId, EventDto eventDto) {
        if (eventDto.getEventDate().isBefore(LocalDateTime.now())) {
            throw new ConflictException("Event date must be in past");
        }

        User user = userService.getById(userId);
        Category category = categoryService.findById(eventDto.getCategory());
        Location location = locationRepository.findByLatAndLon(eventDto.getLocation().getLat(),
                        eventDto.getLocation().getLon())
                .orElseGet(() -> locationRepository.save(eventDto.getLocation()));

        eventDto.setLocation(location);
        Event event = EventMapper.toEvent(eventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setConfirmedRequests(0L);
        event.setState(EventState.PENDING);
        event.setCreatedOn(LocalDateTime.now());
        event.setViews(0L);
        return eventRepository.save(event);
    }

    @Override
    public Event findByEventAndUserId(Long userId, Long eventId) {
        User user = userService.getById(userId);
        return eventRepository.findByIdAndInitiator(eventId, user)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
    }

    @Override
    public Event getById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
    }

    @Override
    public Event update(Long userId, Long eventId, EventUpdateDto eventUpdateDto) {
        if (eventUpdateDto.getEventDate() != null && eventUpdateDto.getEventDate()
                .isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException("Event date must be in past");
        }

        Event event = findByEventAndUserId(userId, eventId);

        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Event is already published");
        }

        if (eventUpdateDto.getStateAction() != null) {
            switch (eventUpdateDto.getStateAction()) {
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                default:
                    throw new ConflictException("State out of range");
            }
        }
        return updateEventCategoryLocation(eventUpdateDto, event);
    }

    @Override
    public List<RequestDto> findRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
        User user = userService.getById(userId);
        if (!event.getInitiator().equals(user)) {
            throw new ConflictException("User id=" + userId + " mot create event id=" + eventId);
        }

        List<Request> requests = requestRepository.findAllByEvent(event);
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }
        return requests.stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestUpdateDto updateRequest(Long userId, Long eventId, RequestUpdateStatusDto requestUpdateStatusDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
        User user = userService.getById(userId);

        if (!event.getInitiator().equals(user)) {
            throw new ConflictException("User id=" + userId + " mot create event id=" + eventId);
        }
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new ConflictException("Accept for request mot required");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(requestRepository.findConfirmedRequests(eventId, CONFIRMED))) {
            throw new ConflictException("Limit of requests");
        }

        List<Request> requests = requestRepository.findAllByIdInAndStatus(requestUpdateStatusDto.getRequestIds(),
                RequestStatus.PENDING);

        if (requests.isEmpty()) {
            throw new ConflictException("User may change only waiting requests");
        }

        RequestUpdateDto requestUpdateDto = new RequestUpdateDto();

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

    @Override
    public Event findEventById(Long id, HttpServletRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " not found"));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("Event must be published");
        }
        statServerClient.createStat(request);
        event.setViews(statServerClient.getView(event.getId()));
        return eventRepository.save(event);
    }

    @Override
    public List<Event> findEventsByAdmin(List<Long> users, List<EventState> states, List<Integer> categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable) {
        if (rangeEnd != null && rangeStart != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Bad datetime range");
        }
        return eventRepository.findByAdminParameter(users, states, categories, rangeStart, rangeEnd, pageable)
                .getContent();
    }

    @Override
    public Event updateEventByAdmin(Long eventId, EventUpdateDto eventUpdateDto) {
        if (eventUpdateDto.getEventDate() != null && eventUpdateDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new BadRequestException("Bad datetime range");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));

        if (eventUpdateDto.getStateAction() != null) {
            if (!event.getState().equals(EventState.PENDING)) {
                throw new ConflictException("Event must be pending");
            }
            switch (eventUpdateDto.getStateAction()) {
                case REJECT_EVENT:
                    event.setState(EventState.CANCELED);
                    break;
                case PUBLISH_EVENT:
                    event.setState(EventState.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                default:
                    throw new ConflictException("State not valid");
            }
        }
        return updateEventCategoryLocation(eventUpdateDto, event);
    }

    private Event updateEventCategoryLocation(EventUpdateDto eventUpdateDto, Event event) {
        if (eventUpdateDto.getCategory() != null) {
            event.setCategory(categoryService.findById(eventUpdateDto.getCategory()));
        }
        if (eventUpdateDto.getLocation() != null) {
            System.out.println(eventUpdateDto.getLocation());
            Location location = locationRepository.findByLatAndLon(eventUpdateDto.getLocation().getLat(),
                            eventUpdateDto.getLocation().getLat())
                    .orElseGet(() -> locationRepository.save(eventUpdateDto.getLocation()));
            event.setLocation(location);
        }
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
        return eventRepository.save(event);
    }
}