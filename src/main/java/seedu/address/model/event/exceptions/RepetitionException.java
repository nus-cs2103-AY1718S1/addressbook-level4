//@@author shuang-yang
package seedu.address.model.event.exceptions;

/**
 * Signals that there is error with scheduling repetition of an event.
 */

public class RepetitionException extends Exception {
    public RepetitionException() {
        super("Unable to schedule repetition.");
    }
}
