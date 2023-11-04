package explore_with_me.main_server.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private List<Long> events = new ArrayList<>();
    private Boolean pinned;

    @NotBlank
    @Size(min = 1, message = "title size to short")
    @Size(max = 50, message = "title size to long")
    private String title;
}
