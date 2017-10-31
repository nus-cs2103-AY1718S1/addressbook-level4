package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
// @@author HuWanqing
/**
 * Represents a Event's description in the address book.
 */

public class EventDescription {


    public static final String MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS =
            "Event description should not be blank";

    /*
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[^\\s].*";

    public final String eventDesc;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventDescription(String desc) throws IllegalValueException {
        requireNonNull(desc);
        String trimmedDesc = desc.trim();
        if (!isValidDesc(trimmedDesc)) {
            throw new IllegalValueException(MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS);
        }
        this.eventDesc = trimmedDesc;
    }

    /**
     * Returns true if a given string is a valid event description.
     */
    public static boolean isValidDesc(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return eventDesc;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDescription // instanceof handles nulls
                && this.eventDesc.equals(((EventDescription) other).eventDesc)); // state check
    }

    @Override
    public int hashCode() {
        return eventDesc.hashCode();
    }

}
