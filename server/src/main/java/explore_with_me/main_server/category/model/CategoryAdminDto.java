package explore_with_me.main_server.category.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAdminDto {

    @Size(min = 1, message = "name size to short")
    @Size(max = 50, message = "name size to long")
    private String name;
}
