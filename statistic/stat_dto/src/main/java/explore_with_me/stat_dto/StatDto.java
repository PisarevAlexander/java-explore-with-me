package explore_with_me.stat_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Stat dto object
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {

    @NotNull
    private String app;

    @NotNull
    private String uri;

    @NotNull
    private String ip;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}