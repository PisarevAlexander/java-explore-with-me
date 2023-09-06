package ru.practicum.main_server.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main_server.location.Location;


import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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

    @NotNull(message = "eventDate can't be null")
    @Future
    LocalDateTime eventDate;

    @NotNull
    private Location location;

    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;

    @NotNull
    @Size(min = 3, message = "title size to short")
    @Size(max = 120, message = "title size to long")
    private String title;
}