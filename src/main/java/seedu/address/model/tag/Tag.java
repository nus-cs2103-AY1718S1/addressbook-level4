package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.awt.Color;

import java.util.Random;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String DEFAULT_COLOR = "grey";

    private static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    private static Random random = new Random();

    public final String tagName;
    private String tagColor;

    //@@author Eric
    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name, String tagColor) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = trimmedName;
        this.tagColor = tagColor;
    }

    public String getTagColor() {
        return this.tagColor;
    }


    public void setColor(String color) {
        tagColor = color;
    }


    public void setRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);
        String colorInHexString = convertColorToHexadecimal(randomColor);
        tagColor = colorInHexString;
    }


    public void setOffColor() {
        tagColor = "grey";
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

    //@@author Eric
    /**
     * Converts a color to hexadecimal string
     *
     */
    private static String convertColorToHexadecimal(Color color) {
        return String.format("#%06x", color.getRGB() & 0x00FFFFFF);
    }
}
