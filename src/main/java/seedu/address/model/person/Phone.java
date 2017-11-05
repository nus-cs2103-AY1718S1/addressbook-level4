//@@author Lenaldnwj
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhoneUi(String)}
 */
public class Phone {

    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Users are to enter their numbers in this format, p/ student/(STUDENT_NUMBER) parent/(PARENT_NUMBER)\n"
                    + "For example, p/ student/97271111 parent/97979797\n"
                    + "Phone numbers can only contain numbers, and should be exactly 8 digits";
    public static final String PHONE_UI_VALIDATION_REGEX = "(Student: )(\\d\\d\\d\\d\\d\\d\\d\\d)";
    public final String value;

    /**
     * Validates the UI formatting phone number.
     *
     * @throws IllegalValueException if phone UI string is of invalid format.
     */
    public Phone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        String phoneFormat = formatPhone(trimmedPhone);
        if (!isValidPhoneUi(phoneFormat)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = phoneFormat;
    }

    /**
     * Returns true if a given string is a valid person phone number for display in UI.
     */
    public static boolean isValidPhoneUi(String test) {
        return test.matches(PHONE_UI_VALIDATION_REGEX);
    }

    /**
     * Returns formatted phone by appending Student:  before the student phone number.
     */
    public static String formatPhone(String phoneToFormat) {
        return "Student: " + phoneToFormat; }

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
