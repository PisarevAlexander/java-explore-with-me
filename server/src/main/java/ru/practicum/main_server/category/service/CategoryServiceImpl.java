package ru.practicum.main_server.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_server.category.model.Category;
import ru.practicum.main_server.category.CategoryRepository;
import ru.practicum.main_server.category.model.CategoryDto;
import ru.practicum.main_server.category.model.CategoryMapper;
import ru.practicum.main_server.event.EventRepository;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.exception.ConflictException;
import ru.practicum.main_server.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    public final CategoryRepository categoryRepository;
    public final EventRepository eventRepository;

    @Transactional
    @Override
    public Category create(CategoryDto categoryDto) {
        return categoryRepository.save(CategoryMapper.toCategory(categoryDto));
    }

    @Transactional
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

    @Transactional
    @Override
    public Category update(Integer catId, CategoryDto categoryDto) {
        Category category = findById(catId);
        category.setName(categoryDto.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public List<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent();
    }

    @Transactional
    @Override
    public Category findById(Integer catId) {
        return categoryRepository.findCategoriesById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id=" + catId + " was not found"));
    }
}