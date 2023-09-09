package ru.practicum.main_server.event.model;

public enum SortValue {

    EVENT_DATE("eventDate"),
    VIEWS("views");

    private String title;

    SortValue(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
