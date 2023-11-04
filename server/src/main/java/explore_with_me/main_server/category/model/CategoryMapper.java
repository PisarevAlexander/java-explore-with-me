package explore_with_me.main_server.category.model;

public class CategoryMapper {

    public static Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }
}
