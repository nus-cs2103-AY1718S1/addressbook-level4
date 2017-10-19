package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

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
    public final ArrayList<String> phonelist;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */

    public Phone(List<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        String phoneStr = "";
        ArrayList<String> phones = new ArrayList<String>();
        for (String s : phone) {
            String trimmedPhone = s.trim();
            if (!isValidPhone(trimmedPhone)) {
                throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
            }
            phones.add(s);
            phoneStr += s + "\n";

        }
        phoneStr = phoneStr.substring(0, phoneStr.length() - 1);
        this.phonelist = phones;
        value = phoneStr;
    }

    public Phone(String phone) throws IllegalValueException {
        requireNonNull(phone);

        String[] numbers = phone.split("\n");

        ArrayList<String> phones = new ArrayList<String>();

        for (int i = 0; i < numbers.length; i++) {
            String trimmedPhone = numbers[i].trim();
            if (!isValidPhone(trimmedPhone)) {
                throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
            }
            phones.add(numbers[i]);
        }
        this.phonelist = phones;
        this.value = phone;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
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
