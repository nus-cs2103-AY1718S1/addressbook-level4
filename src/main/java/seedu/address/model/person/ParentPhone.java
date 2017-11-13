//@@author Lenaldnwj
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's ParentPhone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidParentPhone(String)}
 */
public class ParentPhone {

    public static final String MESSAGE_PARENTPHONE_CONSTRAINTS = "Parent numbers should be exactly 8 digits long";

    public static final String PARENTPHONE_VALIDATION_REGEX = "(\\d\\d\\d\\d\\d\\d\\d\\d)";

    public final String value;

    /**
     * Validates given ParentPhone.
     *
     * @throws IllegalValueException if given ParentPhone string is invalid.
     */
    public ParentPhone(String parentPhone) throws IllegalValueException {
        requireNonNull(parentPhone);
        String trimmedParentPhone = parentPhone.trim();
        if (!isValidParentPhone(trimmedParentPhone)) {
            throw new IllegalValueException(MESSAGE_PARENTPHONE_CONSTRAINTS);
        }
        this.value = trimmedParentPhone;
    }

    /**
     * Returns true if a given string is a valid parentPhone name.
     */
    public static boolean isValidParentPhone(String test) {
        return test.matches(PARENTPHONE_VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ParentPhone // instanceof handles nulls
                && this.value.equals(((ParentPhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
