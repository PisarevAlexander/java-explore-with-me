package explore_with_me.main_server.user.service;

import explore_with_me.main_server.user.model.User;
import org.springframework.data.domain.Pageable;
import explore_with_me.main_server.user.model.UserDto;

import java.util.List;

/**
 * The interface User service
 */

public interface UserService {

    /**
     * Get by id.
     * @param userId the user id
     * @return the user
     */

    User getById(Long userId);

    /**
     * Create user
     * @param userDto the user dto
     * @return the user
     */

    User create(UserDto userDto);

    /**
     * Get all
     * @param ids      the ids
     * @param pageable the pageable
     * @return the list of users
     */

    List<User> getAll(List<Long> ids, Pageable pageable);

    /**
     * Delete user
     * @param userId the user id
     */

    void delete(Long userId);
}