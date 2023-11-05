package explore_with_me.main_server.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import explore_with_me.main_server.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Event DTO object
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    @NotNull
    @Size(min = 20, message = "annotation size to short")
    @Size(max = 2000, message = "annotation size to long")
    private String annotation;

    private Integer category;

    @NotNull
    @Size(min = 20, message = "description size to short")
    @Size(max = 7000, message = "description size to long")
    private String description;

    /**
     * The Event date.
     */
    @NotNull(message = "eventDate can't be null")
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    private Location location;

    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;

    @NotNull
    @Size(min = 3, message = "title size to short")
    @Size(max = 120, message = "title size to long")
    private String title;
}