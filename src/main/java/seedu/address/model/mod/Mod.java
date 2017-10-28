package seedu.address.model.mod;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Mod in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Mod {

    public static final String MESSAGE_MOD_CONSTRAINTS = "Mods names should be alphanumeric";
    public static final String MOD_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String modName;

    /**
     * Validates given mod name.
     *
     * @throws IllegalValueException if the given mod name string is invalid.
     */
    public Mod(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_MOD_CONSTRAINTS);
        }
        this.modName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid mod name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(MOD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mod // instanceof handles nulls
                && this.modName.equals(((Mod) other).modName)); // state check
    }

    @Override
    public int hashCode() {
        return modName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + modName + ']';
    }

}
