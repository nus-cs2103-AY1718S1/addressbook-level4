package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";
    //@@author WangJieee
    private static String[] colors = {"CornflowerBlue", "Tomato", "DarkSlateGray", "Crimson", "DarkBlue", "DarkGreen",
                                      "FireBrick", "OrangeRed", "Orchid", "blue", "Gold", "red", "MediumSeaGreen",
                                      "PaleVioletRed", "Peru", "RebeccaPurple", "RoyalBlue", "SeaGreen", "Coral",
                                      "DarkOrange", "DarkOliveGreen", "DarkRed", "DarkSalmon", "DarkSeaGreen", "Teal"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static int colourIndex = 0;
    public final String tagName;
    public final String tagColour;
    //@@author

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
        this.tagColour = getColorForTag(tagName);
    }

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
                && this.tagName.equals(((Tag) other).tagName))
                && this.tagColour.equals(((Tag) other).tagColour); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, tagColour);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    //@@author WangJieee
    /**
     * Assign a color to a tag if it does not have an existing color.
     * @return the color assigned to that tag
     */
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[colourIndex]);
            updateColourIndex();
        }

        return tagColors.get(tagValue);
    }

    /**
     * update the index of colour
     */
    private static void updateColourIndex() {
        if (colourIndex == colors.length - 1) {
            colourIndex = 0;
        } else {
            colourIndex++;
        }
    }
    //@@author
}
