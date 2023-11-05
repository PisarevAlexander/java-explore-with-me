package explore_with_me.main_server.category.controller;

import explore_with_me.main_server.category.model.Category;
import explore_with_me.main_server.category.model.CategoryAdminDto;
import explore_with_me.main_server.category.model.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import explore_with_me.main_server.category.service.CategoryService;

import javax.validation.Valid;

/**
 * Admin category controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/categories")
@Slf4j
@Validated
public class AdminCategoryController {

    private final CategoryService categoryService;

    /**
     * Create category
     * POST admin/categories
     * @param categoryDto the category dto
     * @return category json
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Create category = {}", categoryDto);
        return categoryService.create(categoryDto);
    }

    /**
     * Delete category
     * DELETE admin/categories/{catId}
     * @param catId the category id
     */

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer catId) {
        log.info("Delete category with id = {}", catId);
        categoryService.delete(catId);
    }

    /**
     * Update category
     * PATCH admin/categories/{catId}
     * @param categoryAdminDto the category admin dto
     * @param catId            the category id
     * @return category json
     */

    @PatchMapping("/{catId}")
    public Category update(@Valid @RequestBody CategoryAdminDto categoryAdminDto,
                           @PathVariable Integer catId) {
        log.info("Update category with id = {}", catId);
        return categoryService.update(catId, categoryAdminDto);
    }
}
