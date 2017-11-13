package seedu.address.model.module.exceptions;

//@@author junming403
/**
 * Signals that the lesson want to unmark is not in the marked list.
 */
public class NotRemarkedLessonException extends Exception {
    public NotRemarkedLessonException() {
        super("The lesson is not in the remarked list");
    }
}
