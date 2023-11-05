package explore_with_me.main_server.category;

import explore_with_me.main_server.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Category repository.
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Find Category by name.
     * @param name the name
     * @return the Category optional
     */

    Optional<Category> findByName(String name);
}