package ru.practicum.main_server.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.main_server.category.model.Category;
import ru.practicum.main_server.location.Location;
import ru.practicum.main_server.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ToString.Exclude
    private Category category;

    @Column(name = "confirmedRequests")
    private Long confirmedRequests;

    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "eventDate")
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    @ToString.Exclude
    private User initiator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @Column(name = "participantLimit")
    private Long participantLimit;

    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;

    @Column(name = "requestModeration", nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.ORDINAL)
    private EventState state;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "views", nullable = false)
    private Long views;
}
