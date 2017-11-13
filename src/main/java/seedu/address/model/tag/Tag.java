package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;

//@@author nbriannl
/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric, and should not be blank";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";
    public static final int TAG_ACCEPTABLE_LENGTH = 25;

    public final String tagName;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        if (!isAcceptableTagLength(trimmedName)) {
            throw new IllegalValueException(generateExceptionMessageForLongTag(trimmedName));
        }
        this.tagName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    /**
     * Returns true if a valid tag name is too long.
     */
    public static boolean isAcceptableTagLength(String test) {
        return test.length() < TAG_ACCEPTABLE_LENGTH;
    }

    /**
     * Generates the exception message when trying to create a tag which is too long
     */
    public static String generateExceptionMessageForLongTag(String longTag) {
        return "The tag: " + longTag + " is too long!\n"
                + "Consider adding a remark instead?\n"
                + "Example: remark INDEX r/" + longTag + "\n"
                + "\n"
                + RemarkCommand.MESSAGE_USAGE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
