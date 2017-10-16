package seedu.address.model.event;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

public class ETime {


    public static final String MESSAGE_EVENT_TIME_CONSTRAINTS =
            "Event description should not be blank";

    /*
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EVNET_TIME_VALIDATION_REGEX = "^(0[1-9]|[12][\\d]|3[01]|[1-9])[///./-]"
            + "(0[1-9]|1[012]|[1-9])[///./-](19|20)\\d\\d$";

    public final String EventTime;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public ETime(String desc) throws IllegalValueException {
        requireNonNull(desc);
        String trimmedName = desc.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_EVENT_TIME_CONSTRAINTS);
        }
        this.EventTime = trimmedName;
    }

    /**
     * Returns true if a given string is a valid event description.
     */
    public static boolean isValidName(String test) {
        return test.matches(MESSAGE_EVENT_TIME_CONSTRAINTS);
    }


    @Override
    public String toString() {
        return EventTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ETime // instanceof handles nulls
                && this.EventTime.equals(((ETime) other).EventTime)); // state check
    }

    @Override
    public int hashCode() {
        return EventTime.hashCode();
    }

}
