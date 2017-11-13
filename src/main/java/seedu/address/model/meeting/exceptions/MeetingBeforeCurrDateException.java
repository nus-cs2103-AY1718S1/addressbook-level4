package seedu.address.model.meeting.exceptions;

//@@author Melvin-leo
/**
 * Signals that the operation is unable to add meeting due to Date and time before log in time.
 */
public class MeetingBeforeCurrDateException extends Exception {

    public MeetingBeforeCurrDateException() {
        super("Operation would result in invalid meetings");
    }
}
