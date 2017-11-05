package seedu.address.model.event.exceptions;



//@@author junyango

/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends Exception {
    public EventNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}

