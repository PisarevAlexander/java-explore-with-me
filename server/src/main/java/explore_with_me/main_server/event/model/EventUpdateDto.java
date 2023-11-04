package explore_with_me.main_server.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import explore_with_me.main_server.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateDto {

    @Size(min = 20, message = "annotation size to short")
    @Size(max = 2000, message = "annotation size to long")
    private String annotation;

    private Integer category;

    @Size(min = 20, message = "description size to short")
    @Size(max = 7000, message = "description size to long")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @Valid
    private Location location;
    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;
    private Boolean requestModeration;
    private AdminState stateAction;

    @Size(min = 3, message = "title size to short")
    @Size(max = 120, message = "title size to long")
    private String title;
}
