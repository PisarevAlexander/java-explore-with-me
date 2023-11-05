package explore_with_me.main_server.category.service;

import explore_with_me.main_server.category.model.Category;
import explore_with_me.main_server.category.model.CategoryAdminDto;
import explore_with_me.main_server.category.model.CategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Category service.
 */

public interface CategoryService {

    /**
     * Create category
     * @param categoryDto the category dto
     * @return the category
     */

    Category create(CategoryDto categoryDto);

    /**
     * Delete category
     * @param catId the cat id
     */

    void delete(Integer catId);

    /**
     * Update category
     * @param catId            the cat id
     * @param categoryAdminDto the category admin dto
     * @return the category
     */

    Category update(Integer catId, CategoryAdminDto categoryAdminDto);

    /**
     * Gets all category
     * @param pageable the pageable
     * @return the all
     */

    List<Category> getAll(Pageable pageable);

    /**
     * Find category by id
     * @param catId the cat id
     * @return the category
     */

    Category findById(Integer catId);
}
