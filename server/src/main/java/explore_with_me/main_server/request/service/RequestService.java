package explore_with_me.main_server.request.service;

import explore_with_me.main_server.request.model.RequestDto;

import java.util.List;

/**
 * The interface Request service
 */

public interface RequestService {

    /**
     * Find all requests
     * @param userID the user id
     * @return the list of requests
     */

    List<RequestDto> findAllByUser(Long userID);

    /**
     * Create request
     * @param userId  the user id
     * @param eventId the event id
     * @return the request dto
     */

    RequestDto create(Long userId, Long eventId);

    /**
     * Update request
     * @param userId    the user id
     * @param requestId the request id
     * @return the request dto
     */

    RequestDto update(Long userId, Long requestId);
}