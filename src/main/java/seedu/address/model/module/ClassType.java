package seedu.address.model.module;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Lesson's class type in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidClassType(String)}
 */
public class ClassType {
    public static final String MESSAGE_CLASSTYPE_CONSTRAINTS =
            "Class type can only contain numbers, and should be at least 3 digits long";
    public static final String CLASSTYPE_VALIDATION_REGEX = "[a-zA-Z]{3}";
    public final String value;

    /**
     * Validates given class type.
     *
     * @throws IllegalValueException if given class type string is invalid.
     */
    public ClassType(String classType) throws IllegalValueException {
        requireNonNull(classType);
        String trimmedClassType = classType.trim();
        if (!isValidClassType(trimmedClassType)) {
            throw new IllegalValueException(MESSAGE_CLASSTYPE_CONSTRAINTS);
        }
        this.value = trimmedClassType;
    }

    /**
     * Returns true if a given string is a valid lesson class type.
     */
    public static boolean isValidClassType(String test) {
        if (test.matches(CLASSTYPE_VALIDATION_REGEX) && containKeyword(test)) {
            return true;
        }
        return false;
    }

    private static boolean containKeyword(String test) {
        return test.equalsIgnoreCase("lec") || test.equalsIgnoreCase("tut");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassType // instanceof handles nulls
                && this.value.equals(((ClassType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
