//@@author a0107442n
package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will cause a timeclash of events.
 */
public class EventTimeClashException extends Exception {
    public EventTimeClashException() {
        super("Operation would result in time clash in events.");
    }
}
