package seedu.address.model.module;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidClassType(String)}
 */
public class ClassType {
    public static final String MESSAGE_CLASSTYPE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String CLASSTYPE_VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
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
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidClassType(String test) {
        return test.matches(CLASSTYPE_VALIDATION_REGEX);
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
