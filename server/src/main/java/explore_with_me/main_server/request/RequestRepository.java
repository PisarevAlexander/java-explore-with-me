package explore_with_me.main_server.request;

import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.request.model.Request;
import explore_with_me.main_server.request.model.RequestStatus;
import explore_with_me.main_server.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequester(User requester);

    Boolean existsByRequesterAndEvent(User user, Event event);

    List<Request> findAllByEvent(Event event);

    List<Request> findAllByIdInAndStatus(Collection<Long> id, RequestStatus status);

    @Query("SELECT count(r.id) " +
            "FROM Request AS r " +
            "WHERE r.event.id in :eventId " +
            "AND r.status = :status")
    Long findConfirmedRequests(@Param("eventId") Long eventId,
                               @Param("status") RequestStatus status);
}
