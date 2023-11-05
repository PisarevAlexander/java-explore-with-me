package explore_with_me.main_server.event.model;

/**
 * The enum Sort value
 */

public enum SortValue {

    EVENT_DATE("eventDate"),
    VIEWS("views");

    private String title;

    SortValue(String title) {
        this.title = title;
    }

    /**
     * Gets title
     * @return the title
     */

    public String getTitle() {
        return title;
    }
}