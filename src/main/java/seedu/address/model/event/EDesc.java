package seedu.address.model.event;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

public class EDesc {


    public static final String MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS =
            "Event description should not be blank";

    /*
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String EventDesc;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EDesc(String desc) throws IllegalValueException {
        requireNonNull(desc);
        String trimmedName = desc.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS);
        }
        this.EventDesc = trimmedName;
    }

    /**
     * Returns true if a given string is a valid event description.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return EventDesc;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EDesc // instanceof handles nulls
                && this.EventDesc.equals(((EDesc) other).EventDesc)); // state check
    }

    @Override
    public int hashCode() {
        return EventDesc.hashCode();
    }

}
