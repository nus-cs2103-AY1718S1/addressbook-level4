package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author chairz
/**
 * Represents a Person's second phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone2 {


    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";
    public static final String PHONE2_VALIDATION_REGEX = "-";
    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Phone2(String phone2) throws IllegalValueException {
        if (phone2 == null) {
            this.value = "-";
        } else {
            requireNonNull(phone2);
            String trimmedPhone = phone2.trim();
            if (!isValidPhone(trimmedPhone) && !trimmedPhone.equals("-")) {
                throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
            }
            this.value = trimmedPhone;
        }
    }

    public Phone2() {
        this.value = "-"; }


    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX) || test.matches(PHONE2_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone2 // instanceof handles nulls
                && this.value.equals(((Phone2) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
