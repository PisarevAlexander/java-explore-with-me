package explore_with_me.main_server.category.controller;

import explore_with_me.main_server.OffsetBasedPageRequest;
import explore_with_me.main_server.category.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import explore_with_me.main_server.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Slf4j
@Validated
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                  @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get category page");
        Pageable pageable = new OffsetBasedPageRequest(from, size);
        return categoryService.getAll(pageable);
    }

    @GetMapping("/{catId}")
    public Category findById(@PathVariable Integer catId) {
        log.info("Get category by id = {}", catId);
        return categoryService.findById(catId);
    }
}
