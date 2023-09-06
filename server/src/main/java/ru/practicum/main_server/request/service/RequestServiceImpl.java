package ru.practicum.main_server.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_server.event.EventRepository;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.event.model.EventState;
import ru.practicum.main_server.exception.BadRequestException;
import ru.practicum.main_server.exception.ConflictException;
import ru.practicum.main_server.exception.NotFoundException;
import ru.practicum.main_server.request.RequestRepository;
import ru.practicum.main_server.request.model.Request;
import ru.practicum.main_server.request.model.RequestStatus;
import ru.practicum.main_server.user.UserRepository;
import ru.practicum.main_server.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public List<Request> findAllByUser(Long userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with Id=" + userId + " not found"));
        return requestRepository.findAllByRequester(user);
    }

    @Transactional
    @Override
    public Request create(Long userId, Long eventId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found"));
        if (event.getInitiator().equals(user)) {
            throw new ConflictException("User create this event");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Event must be published");
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ConflictException("Limit request reached");
        }
        if (requestRepository.existsByRequesterAndEvent(user, event)) {
            throw new ConflictException("User already send request");
        }

        Request request = new Request();
        request.setCreated(LocalDateTime.now());
        request.setEvent(event);
        request.setRequester(user);
        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }
        return requestRepository.save(request);
    }

    @Transactional
    @Override
    public Request update(Long userId, Long requestId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " not found"));
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
        return request;
    }
}