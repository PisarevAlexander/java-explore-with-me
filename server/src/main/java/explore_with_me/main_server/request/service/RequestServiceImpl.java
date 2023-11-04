package explore_with_me.main_server.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import explore_with_me.main_server.event.EventRepository;
import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.event.model.EventState;
import explore_with_me.main_server.event.service.EventService;
import explore_with_me.main_server.exception.BadRequestException;
import explore_with_me.main_server.exception.ConflictException;
import explore_with_me.main_server.exception.NotFoundException;
import explore_with_me.main_server.request.RequestRepository;
import explore_with_me.main_server.request.model.Request;
import explore_with_me.main_server.request.model.RequestDto;
import explore_with_me.main_server.request.model.RequestMapper;
import explore_with_me.main_server.request.model.RequestStatus;
import explore_with_me.main_server.user.model.User;
import explore_with_me.main_server.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final EventService eventService;
    private final EventRepository eventRepository;

    @Override
    public List<RequestDto> findAllByUser(Long userId) {
        User user = userService.getById(userId);
        List<Request> requests = requestRepository.findAllByRequester(user);
        if (requests.isEmpty()) {
            return new ArrayList<>();
        }
        return requests.stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto create(Long userId, Long eventId) {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);

        if (event.getInitiator().equals(user)) {
            throw new ConflictException("User create this event");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Event must be published");
        }
        if (event.getParticipantLimit() > 0 && event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ConflictException("Limit request reached");
        }
        if (requestRepository.existsByRequesterAndEvent(user, event)) {
            throw new ConflictException("User already send request");
        }

        Request request = new Request();
        request.setCreated(LocalDateTime.now());
        request.setEvent(event);
        request.setRequester(user);
        if (event.getRequestModeration() && event.getParticipantLimit() > 0) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1L);
            eventRepository.save(event);
        }
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto update(Long userId, Long requestId) {
        User user = userService.getById(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id=" + requestId + " not found"));
        if (!request.getRequester().equals(user)) {
            throw new BadRequestException("User must create event");
        }
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            Event event = request.getEvent();
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }
}