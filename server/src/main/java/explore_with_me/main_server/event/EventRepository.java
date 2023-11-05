package explore_with_me.main_server.event;

import explore_with_me.main_server.category.model.Category;
import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.event.model.EventState;
import explore_with_me.main_server.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Event repository
 */

public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find all by category list.
     * @param category the category
     * @return the list of events
     */

    List<Event> findAllByCategory(Category category);

    /**
     * Find all by id
     * @param ids the ids
     * @return the list of events
     */

    List<Event> findAllByIdIn(List<Long> ids);

    /**
     * Find by initiator
     * @param initiator the initiator
     * @param pageable  the pageable
     * @return the page of events
     */

    Page<Event> findByInitiator(User initiator, Pageable pageable);

    /**
     * Find by id and initiator
     * @param eventId the event id
     * @param userId  the user id
     * @return the event optional
     */

    Optional<Event> findByIdAndInitiator(Long eventId, User userId);

    /**
     * Find all events by public
     * @param text       the text
     * @param categories the categories
     * @param paid       the paid
     * @param rangeStart the range start
     * @param pageable   the pageable
     * @return the page of events
     */

    @Query("SELECT new explore_with_me.main_server.event.model.Event(e.id, e.annotation, e.category, e.confirmedRequests," +
            "e.createdOn, e.description, e.eventDate, e.initiator, e.location, e.paid, e.participantLimit, e.publishedOn, " +
            "e.requestModeration, e.state, e.title, e.views) " +
            "FROM Event as e " +
            "WHERE e.state = 1 " +
            "AND (COALESCE(:text, NULL) IS NULL OR (LOWER(e.annotation) LIKE LOWER(concat('%', :text, '%')) OR LOWER(e.description) LIKE LOWER(concat('%', :text, '%')))) " +
            "AND (COALESCE(:categories, NULL) IS NULL OR e.category.id IN :categories) " +
            "AND (COALESCE(:paid, NULL) IS NULL OR e.paid = :paid) " +
            "AND (e.eventDate >= :now)")
    Page<Event> findByPublicParameterNoDate(@Param("text") String text,
                                            @Param("categories") List<Integer> categories,
                                            @Param("paid") Boolean paid,
                                            @Param("now") LocalDateTime rangeStart,
                                            Pageable pageable);

    /**
     * Find all events by public parameter page
     * @param text       the text
     * @param categories the categories
     * @param paid       the paid
     * @param rangeStart the range start
     * @param rangeEnd   the range end
     * @param pageable   the pageable
     * @return the page of events
     */

    @Query("SELECT new explore_with_me.main_server.event.model.Event(e.id, e.annotation, e.category, e.confirmedRequests, " +
            "e.createdOn, e.description, e.eventDate, e.initiator, e.location, e.paid, e.participantLimit, e.publishedOn, " +
            "e.requestModeration, e.state, e.title, e.views) " +
            "FROM Event as e " +
            "WHERE e.state = 1 " +
            "AND (COALESCE(:text, NULL) IS NULL OR (LOWER(e.annotation) LIKE LOWER(concat('%', :text, '%')) OR LOWER(e.description) LIKE LOWER(concat('%', :text, '%')))) " +
            "AND (COALESCE(:categories, NULL) IS NULL OR e.category.id IN :categories) " +
            "AND (COALESCE(:paid, NULL) IS NULL OR e.paid = :paid) " +
            "AND (COALESCE(:rangeStart, NULL) IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (COALESCE(:rangeEnd, NULL) IS NULL OR e.eventDate <= :rangeEnd) ")
    Page<Event> findByPublicParameter(@Param("text") String text,
                                      @Param("categories") List<Integer> categories,
                                      @Param("paid") Boolean paid,
                                      @Param("rangeStart") LocalDateTime rangeStart,
                                      @Param("rangeEnd") LocalDateTime rangeEnd, Pageable pageable);

    /**
     * Find events by admin parameter page
     * @param users      the users
     * @param states     the states
     * @param categories the categories
     * @param rangeStart the range start
     * @param rangeEnd   the range end
     * @param pageable   the pageable
     * @return the page of events
     */

    @Query("SELECT new explore_with_me.main_server.event.model.Event(e.id, e.annotation, e.category, e.confirmedRequests, " +
            "e.createdOn, e.description, e.eventDate, e.initiator, e.location, e.paid, e.participantLimit, e.publishedOn, " +
            "e.requestModeration, e.state, e.title, e.views) " +
            "FROM Event as e " +
            "WHERE (COALESCE(:users, NULL) IS NULL OR e.initiator.id IN :users) " +
            "AND (COALESCE(:states, NULL) IS NULL OR e.state IN :states) " +
            "AND (COALESCE(:categories, NULL) IS NULL OR e.category.id IN :categories) " +
            "AND (COALESCE(:rangeStart, NULL) IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (COALESCE(:rangeEnd, NULL) IS NULL OR e.eventDate <= :rangeEnd) ")
    Page<Event> findByAdminParameter(@Param("users") List<Long> users,
                                     @Param("states") List<EventState> states,
                                     @Param("categories") List<Integer> categories,
                                     @Param("rangeStart") LocalDateTime rangeStart,
                                     @Param("rangeEnd") LocalDateTime rangeEnd, Pageable pageable);
}