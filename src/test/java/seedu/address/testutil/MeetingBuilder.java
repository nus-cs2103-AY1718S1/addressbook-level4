package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Meeting;
import seedu.address.model.person.InternalId;

/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_LOCATION = "COM1-02-01";
    public static final String DEFAULT_NOTES = "Testing";

    private Meeting meeting;

    public MeetingBuilder() {
        try {
            LocalDateTime datetime = LocalDateTime.now().plusMonths(1);
            InternalId id = new InternalId(1);
            ArrayList<InternalId> participants = new ArrayList<>();
            participants.add(id);
            this.meeting = new Meeting(datetime, DEFAULT_LOCATION, DEFAULT_NOTES, participants);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default meeting's values are invalid.");
        }
    }

    public Meeting build() {
        return this.meeting;
    }

}
