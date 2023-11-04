package explore_with_me.main_server.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotNull
    @Size(min = 10, message = "title size to short")
    @Size(max = 1500, message = "title size to long")
    private String text;
}
