package ru.practicum.main_server.event.model;

public class EventMapper {

    public static Event toEvent(EventDto eventDto) {
        Event event = new Event();
        event.setAnnotation(eventDto.getAnnotation());
        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());
        event.setLocation(eventDto.getLocation());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setTitle(eventDto.getTitle());

        event.setPaid(eventDto.getPaid() == null ? Boolean.valueOf(false) : eventDto.getPaid());

        event.setParticipantLimit(eventDto.getParticipantLimit() == null ? Long.valueOf(0L) : eventDto.getParticipantLimit());

        event.setRequestModeration(eventDto.getRequestModeration() == null ? Boolean.valueOf(true) : eventDto.getRequestModeration());

        return event;
    }
}
