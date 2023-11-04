package explore_with_me.main_server.category.service;

import explore_with_me.main_server.category.model.Category;
import explore_with_me.main_server.category.model.CategoryAdminDto;
import explore_with_me.main_server.category.model.CategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Category create(CategoryDto categoryDto);

    void delete(Integer catId);

    Category update(Integer catId, CategoryAdminDto categoryAdminDto);

    List<Category> getAll(Pageable pageable);

    Category findById(Integer catId);
}
