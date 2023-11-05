package explore_with_me.stat_client.exception;

/**
 * Bad request exception
 */

public class BadRequestException extends RuntimeException {

    /**
     * Instantiates a new Bad request exception
     * @param message the message
     */

    public BadRequestException(String message) {
        super(message);
    }
}