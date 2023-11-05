package explore_with_me.main_server.exception;

/**
 * Conflict exception
 */

public class ConflictException extends RuntimeException {

    /**
     * Instantiates a new Conflict exception.
     * @param message the message
     */

    public ConflictException(String message) {
        super(message);
    }
}