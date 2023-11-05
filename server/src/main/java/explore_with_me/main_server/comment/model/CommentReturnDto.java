package explore_with_me.main_server.comment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Comment return DTO object
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReturnDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    private Long initiator;
    private Long event;
    private String text;
}
