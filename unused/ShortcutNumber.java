//@@author yanji1221
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's shortcut number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidShortcutNumber(String)}
 */
public class ShortcutNumber {

    public static final String MESSAGE_SHORTCUT_NUMBER_CONSTRAINTS =
            "Shortcut numbers can only contain numbers, and should be digits";
    public static final String SHORTCUT_NUMBER_VALIDATION_REGEX = "\\d";
    public final String value;

    /**
     * Validates given shortcut number.
     *
     * @throws IllegalValueException if given shortcut number string is invalid.
     */
    public ShortcutNumber(String shortcutNumber) throws IllegalValueException {
        if (!isValidShortcutNumber(shortcutNumber)) {
            throw new IllegalValueException(MESSAGE_SHORTCUT_NUMBER_CONSTRAINTS);
        }
        this.value = shortcutNumber;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidShortcutNumber(String test) {
        return test.matches(SHORTCUT_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    /*@Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShortcutNumber // instanceof handles nulls
                && this.value.equals(((ShortcutNumber) other).value)); // state check
    }*/

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
