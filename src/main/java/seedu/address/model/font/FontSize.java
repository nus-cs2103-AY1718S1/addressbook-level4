package seedu.address.model.font;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the font size of the AddressBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidFontSize(String)}
 */
public class FontSize {

    public static final String MESSAGE_FONT_SIZE_CONSTRAINTS =
            "Font size can only be either \"xsmall\", \"small\","
                    + " \"normal\", \"large\",  or \"xlarge\" (case-sensitive).";
    public static final String[] FONT_SIZE_VALIDATION = {"xsmall", "small", "normal", "large", "xlarge"};
    public final String value;

    /**
     * Validates given font size.
     *
     * @throws IllegalValueException if given font size is invalid.
     */
    public FontSize(String fontSize) throws IllegalValueException {
        requireNonNull(fontSize);
        if (!isValidFontSize(fontSize)) {
            throw new IllegalValueException(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
        this.value = fontSize;
    }

    /**
     * Returns true if a given string is a valid font size.
     */
    public static boolean isValidFontSize(String test) {
        for (String s : FONT_SIZE_VALIDATION) {
            if (test.equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other.equals(this.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
