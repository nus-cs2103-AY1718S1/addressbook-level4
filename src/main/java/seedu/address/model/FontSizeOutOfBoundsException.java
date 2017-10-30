package seedu.address.model;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the font size change given is out of bounds.
 */
public class FontSizeOutOfBoundsException extends IllegalValueException {
    public final int previousFontSize;
    public final int newFontSize;

    public FontSizeOutOfBoundsException(int previousFontSize, int newFontSize) {
        super("Changing the font size by this amount will result in an out of bounds font size.");
        this.previousFontSize = previousFontSize;
        this.newFontSize = newFontSize;
    }
}
