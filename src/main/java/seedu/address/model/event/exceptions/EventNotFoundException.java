//@@author A0162268B
package seedu.address.model.event.exceptions;

/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends Exception {
    public EventNotFoundException() {
        super("Unable to find specified event");
    }
}
