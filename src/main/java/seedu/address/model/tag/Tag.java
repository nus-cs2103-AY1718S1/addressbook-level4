package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Random;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    private static final Random colourSelect = new Random();

    public final String tagName;
    public final String tagColour;

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
        this.tagName = trimmedName;
        this.tagColour = assignTagColour();
    }

    //@@author teclu
    /**
     * Assign a random colour to the tag.
     * @return A random colour hex code.
     */
    private String assignTagColour() {
        TagColours palette = new TagColours();
        String[] tagColours = palette.getTagColours();
        int randomIndex = colourSelect.nextInt(tagColours.length);

        return tagColours[randomIndex];
    }
    //@@author

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
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
