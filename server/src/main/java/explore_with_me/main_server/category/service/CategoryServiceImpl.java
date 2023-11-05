package explore_with_me.main_server.category.service;

import explore_with_me.main_server.category.CategoryRepository;
import explore_with_me.main_server.category.model.Category;
import explore_with_me.main_server.category.model.CategoryAdminDto;
import explore_with_me.main_server.category.model.CategoryDto;
import explore_with_me.main_server.event.EventRepository;
import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import explore_with_me.main_server.category.model.CategoryMapper;
import explore_with_me.main_server.exception.ConflictException;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Category service
 */

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    public final CategoryRepository categoryRepository;
    public final EventRepository eventRepository;

    /**
     * Create category
     * @param categoryDto the category dto
     * @return the category
     */

    @Override
    public Category create(CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new ConflictException("Name must be unique");
        }
        return categoryRepository.save(CategoryMapper.toCategory(categoryDto));
    }

    /**
     * Delete category
     * @param catId the cat id
     */

    @Override
    public void delete(Integer catId) {
        Category category = findById(catId);
        List<Event> events = eventRepository.findAllByCategory(category);
        if (events.isEmpty()) {
            categoryRepository.deleteById(catId);
        } else {
            throw new ConflictException("Events include delete category");
        }
    }

    /**
     * Update category
     * @param catId            the cat id
     * @param categoryAdminDto the category admin dto
     * @return the category
     */

    @Override
    public Category update(Integer catId, CategoryAdminDto categoryAdminDto) {
        Category category = findById(catId);
        if (category.getName().equals(categoryAdminDto.getName())) {
            return category;
        }
        if (categoryRepository.findByName(categoryAdminDto.getName()).isPresent()) {
            throw new ConflictException("Name must be unique");
        }
        category.setName(categoryAdminDto.getName());
        return categoryRepository.save(category);
    }

    /**
     * Gets all category
     * @param pageable the pageable
     * @return the all
     */

    @Override
    public List<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent();
    }

    /**
     * Find category by id
     * @param catId the cat id
     * @return the category
     */

    @Override
    public Category findById(Integer catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id=" + catId + " was not found"));
    }
}