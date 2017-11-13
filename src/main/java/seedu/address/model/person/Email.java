package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";
    public static final String EMAIL_EMPTY = "-";

    public final String value;
    private String userName;
    private String domainName;

    //@@author mavistoh
    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        if (EMAIL_EMPTY.equals(email) || email == null) {
            this.value = EMAIL_EMPTY;
        } else {
            String trimmedEmail = email.trim();
            if (!isValidEmail(trimmedEmail)) {
                throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
            }
            this.value = trimmedEmail;
            String[] splitEmail = trimmedEmail.split("@");
            userName = splitEmail[0];
            domainName = splitEmail[1];
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX) || test.matches(EMAIL_EMPTY);
    }
    //@@author

    public String getUserName() {
        return userName;
    }

    public String getDomainName() {
        return domainName;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && this.value.equals(((Email) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(Email compareTarget) {
        return this.toString().compareToIgnoreCase(compareTarget.toString());
    }
}
