package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.EmptyFieldException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public final String value;

    //@@author OscarWang114
    /**
     * Initialize a Email object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public Email() {
        this.value = "";
    }
    //@@author

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        //@@author Juxarius
        if (trimmedEmail.isEmpty()) {
            throw new EmptyFieldException(PREFIX_EMAIL);
        }
        //@@author
        if (!isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = trimmedEmail;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
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

}
