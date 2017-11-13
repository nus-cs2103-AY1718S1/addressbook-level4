package seedu.address.model.meeting.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author nelsonqyj
/**
 * Signals that the operation will result in duplicate Meeting objects.
 */
public class MeetingClashException extends IllegalValueException {

    public MeetingClashException() {
            super("Operation would result in clashing meetings");
    }
}

