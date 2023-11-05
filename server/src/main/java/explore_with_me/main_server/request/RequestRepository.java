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

/**
 * Request repository
 */

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    /**
     * Find all requests by requester
     * @param requester the requester
     * @return the list of requests
     */

    List<Request> findAllByRequester(User requester);

    /**
     * Exists by requester and event boolean
     * @param user  the user
     * @param event the event
     * @return the boolean
     */

    Boolean existsByRequesterAndEvent(User user, Event event);

    /**
     * Find all by event list
     * @param event the event
     * @return the list of requests
     */

    List<Request> findAllByEvent(Event event);

    /**
     * Find all by id in and status list
     * @param id     the id
     * @param status the status
     * @return the list of requests
     */

    List<Request> findAllByIdInAndStatus(Collection<Long> id, RequestStatus status);

    /**
     * Find confirmed requests
     * @param eventId the event id
     * @param status  the status
     * @return the requests count
     */

    @Query("SELECT count(r.id) " +
            "FROM Request AS r " +
            "WHERE r.event.id in :eventId " +
            "AND r.status = :status")
    Long findConfirmedRequests(@Param("eventId") Long eventId,
                               @Param("status") RequestStatus status);
}