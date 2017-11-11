package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    // @@author donjar
    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, dashes, parentheses, and spaces. Also, there should be at "
                    + "least 3 numbers in the string.";
    public static final String PHONE_REPLACEMENT_REGEX = "[() -]";
    // @@author
    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Phone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        this.value = extractPhone(trimmedPhone);
    }

    // @@author donjar
    /**
     * Returns a phone number from the given string, by stripping certain special characters.
     */
    public static String extractPhone(String phoneNumber) throws IllegalValueException {
        String strippedPhoneNumber = phoneNumber.replaceAll(PHONE_REPLACEMENT_REGEX, "");
        if (!isValidPhone(strippedPhoneNumber)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        return strippedPhoneNumber;
    }
    // @@author

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
