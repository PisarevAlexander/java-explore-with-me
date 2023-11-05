package explore_with_me.main_server.user;

import explore_with_me.main_server.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * User repository
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by name
     * @param name the name
     * @return the optional of user
     */

    Optional<User> findByName(String name);

    /**
     * Find all by id
     * @param id       the id
     * @param pageable the pageable
     * @return the page of users
     */

    Page<User> findAllByIdIn(Collection<Long> id, Pageable pageable);
}