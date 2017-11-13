package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's Next-of-Kin's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNokPhone(String)}
 */
public class NokPhone {


    public static final String MESSAGE_NOK_PHONE_CONSTRAINTS =
            "NOK Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String NOK_PHONE_VALIDATION_REGEX = "\\d{3,}";
    public static final String NOK_PHONE_UNDEFINED_DEFAULT = "NIL";

    public final String value;

    /**
     * Validates given NOK phone number.
     *
     * @throws IllegalValueException if given NOK phone string is invalid.
     */
    public NokPhone(String nokPhone) throws IllegalValueException {
        if (nokPhone == null) {
            this.value = NOK_PHONE_UNDEFINED_DEFAULT;
        } else {
            if (!isValidNokPhone(nokPhone)) {
                throw new IllegalValueException(MESSAGE_NOK_PHONE_CONSTRAINTS);
            }
            this.value = nokPhone;
        }
    }

    /**
     * Returns true if a given string is a valid NOK phone number.
     */
    public static boolean isValidNokPhone(String test) {
        return test.matches(NOK_PHONE_VALIDATION_REGEX) || test.equals(NOK_PHONE_UNDEFINED_DEFAULT);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NokPhone // instanceof handles nulls
                && this.value.equals(((NokPhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
