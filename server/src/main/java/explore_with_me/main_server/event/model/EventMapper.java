package explore_with_me.main_server.event.model;

public class EventMapper {

    public static Event toEvent(EventDto eventDto) {
        Event event = new Event();
        event.setAnnotation(eventDto.getAnnotation());
        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());
        event.setLocation(eventDto.getLocation());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setTitle(eventDto.getTitle());

        event.setPaid(eventDto.getPaid() == null ? false : eventDto.getPaid());

        event.setParticipantLimit(eventDto.getParticipantLimit() == null ? 0L : eventDto.getParticipantLimit());

        event.setRequestModeration(eventDto.getRequestModeration() == null ? true : eventDto.getRequestModeration());

        return event;
    }
}
