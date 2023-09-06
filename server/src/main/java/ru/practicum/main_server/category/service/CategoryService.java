package ru.practicum.main_server.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_server.category.model.Category;
import ru.practicum.main_server.category.model.CategoryDto;

import java.util.List;

public interface CategoryService {

    Category create(CategoryDto categoryDto);

    void delete(Integer catId);

    Category update(Integer catId, CategoryDto categoryDto);

    List<Category> getAll(Pageable pageable);

    Category findById(Integer catId);
}