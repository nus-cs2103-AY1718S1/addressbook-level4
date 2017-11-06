package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Melvin-leo
/**
 * Contains the tag for the meeting to show significance of meeting
 */
public class MeetingTag {
    public static final String MESSAGE_MEETTAG_CONSTRAINTS = "Importance tag should be from 0 to 2";
    public static final String MEETTAG_VALIDATION_REGEX = "[0-2]";

    public final String tagName;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public MeetingTag(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_MEETTAG_CONSTRAINTS);
        }
        this.tagName = trimmedName;
    }
    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(MEETTAG_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    @Override
    public String toString() {
        return tagName;
    }
}
