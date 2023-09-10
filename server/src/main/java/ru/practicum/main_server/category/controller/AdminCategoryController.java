package ru.practicum.main_server.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.category.model.Category;
import ru.practicum.main_server.category.model.CategoryAdminDto;
import ru.practicum.main_server.category.model.CategoryDto;
import ru.practicum.main_server.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/categories")
@Slf4j
@Validated
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Create category = {}", categoryDto);
        return categoryService.create(categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer catId) {
        log.info("Delete category with id = {}", catId);
        categoryService.delete(catId);
    }

    @PatchMapping("/{catId}")
    public Category update(@Valid @RequestBody CategoryAdminDto categoryAdminDto,
                           @PathVariable Integer catId) {
        log.info("Update category with id = {}", catId);
        return categoryService.update(catId, categoryAdminDto);
    }
}
