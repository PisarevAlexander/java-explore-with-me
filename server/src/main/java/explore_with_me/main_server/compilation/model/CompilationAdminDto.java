package explore_with_me.main_server.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationAdminDto {

    private List<Long> events;
    private Boolean pinned;

    @Size(min = 1, message = "title size to short")
    @Size(max = 50, message = "title size to long")
    private String title;
}
