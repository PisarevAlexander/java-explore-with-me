package explore_with_me.main_server.category.model;

/**
 * Category mapper
 */

public class CategoryMapper {

    /**
     * Category DTO to Category Mapper
     * @param categoryDto the category dto
     * @return the category
     */

    public static Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }
}