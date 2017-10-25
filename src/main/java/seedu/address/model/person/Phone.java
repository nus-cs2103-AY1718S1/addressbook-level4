package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
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
        if (!isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }

        this.value = trimmedPhone;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return formatPhone(value);
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

    /**
     * Format given phone number into typical mobile format
     * For example for Singapore numbers: xxxx-xxxx
     */
    public static String formatPhone(String trimmedPhone) {
        int phoneLength = trimmedPhone.length();
        String formattedPhone = generateFormattedPhone(trimmedPhone, phoneLength);

        return formattedPhone;
    }


    /**
     * Generates and returns a string with digits in the phone and hyphens inserted to get right format
     */
    private static String generateFormattedPhone(String trimmedPhone, int phoneLength) {
        int digitAdded = 0;
        StringBuilder formattedPhone = new StringBuilder();
        for (int count = phoneLength - 1; count >= 0; count--) {
            formattedPhone.insert(0, trimmedPhone.charAt(count));

            digitAdded += 1;
            if (count == 0) { continue; }

            if (isHyphenNeeded(digitAdded)) {
                formattedPhone.insert(0, "-");
                digitAdded = 0;
            }
        }

        return formattedPhone.toString();
    }


    /**
     * Returns true if 4 digits added to the string and hyphen needs to be inserted
     */
    private static boolean isHyphenNeeded(int digitAdded) {
        return (digitAdded % 4 == 0);
    }

}
