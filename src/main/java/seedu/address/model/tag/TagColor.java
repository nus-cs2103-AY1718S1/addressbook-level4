package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author ChenXiaoman
/**
 * Represent a color of a tag
 */
public class TagColor {
    //@@author pjunwei95
    public static final String[] VALID_TAG_COLOR = {"red", "blue", "green", "teal", "aqua",
                                                    "black", "gray", "lime", "maroon", "navy",
                                                    "orange", "purple", "silver", "olive",
                                                    "white", "yellow", "transparent"};
    //@pjunwei95 until here ONLY
    public static final String MESSAGE_TAG_COLOR_CONSTRAINTS = "Valid colors are: "
            + Arrays.toString(VALID_TAG_COLOR);

    public static final String DEFAULT_TAG_COLOR = "orange";

    public final String tagColorName;

    /**
     * Validates given tagColor name.
     *
     * @throws IllegalValueException if the given tagColor name string is invalid.
     */
    public TagColor(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagColorName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_COLOR_CONSTRAINTS);
        }
        this.tagColorName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid tagColor name.
     */
    public static boolean isValidTagColorName(String test) {
        return Arrays.asList(VALID_TAG_COLOR).contains(test);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagColor // instanceof handles nulls
                && this.tagColorName.equals(((TagColor) other).tagColorName)); // state check
    }

    @Override
    public int hashCode() {
        return tagColorName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return tagColorName;
    }
}
