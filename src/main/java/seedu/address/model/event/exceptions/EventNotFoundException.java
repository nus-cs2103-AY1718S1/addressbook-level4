package seedu.address.model.event.exceptions;

//@@author chernghann
/**
 * Signals that the operation is unable to find the specified event
 */

public class EventNotFoundException extends Exception {
    public EventNotFoundException() {
        super("event not found!");
    }
}
