package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Module in ContactHub.
 * Guarantees: immutable; name is valid as declared in {@link #isValidModuleName(String)}
 */
public class Module {

    public static final String MESSAGE_MODULE_CONSTRAINTS = "Module names should be alphanumeric";
    public static final String MODULE_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String moduleName;

    /**
     * Validates given Module name.
     *
     * @throws IllegalValueException if the given Module name string is invalid.
     */
    public Module(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidModuleName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_MODULE_CONSTRAINTS);
        }
        this.moduleName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid Module name.
     */
    public static boolean isValidModuleName(String test) {
        return test.matches(MODULE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Module // instanceof handles nulls
                && this.moduleName.equals(((Module) other).moduleName)); // state check
    }

    @Override
    public int hashCode() {
        return moduleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + moduleName + ']';
    }

}
