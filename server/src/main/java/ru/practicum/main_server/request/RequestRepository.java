package ru.practicum.main_server.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.request.model.Request;
import ru.practicum.main_server.request.model.RequestStatus;
import ru.practicum.main_server.user.model.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequester(User requester);

    Boolean existsByRequesterAndEvent(User user, Event event);

    List<Request> findAllByRequesterAndEvent(User user, Event event);

    List<Request> findAllByIdInAndStatus(List<Long> RequestStatus, RequestStatus pending);

}
