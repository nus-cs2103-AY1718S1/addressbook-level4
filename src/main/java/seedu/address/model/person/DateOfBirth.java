package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.EmptyFieldException;

/**
 * Represents a Person's date of birth in the address book.
 */
public class DateOfBirth {

    public static final String MESSAGE_DOB_CONSTRAINTS =
            "Person's date of birth should only contain numeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DOB_VALIDATION_REGEX = "[0-9][0-9]\\s+[0-9][0-9]\\s+[0-9][0-9][0-9][0-9].*";

    public final String finalDateOfBirth;

    /**
     * Initialise a DateOfBirth object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public DateOfBirth() {
        this.finalDateOfBirth = "";
    }

    /**
     * Validates given Date of Birth.
     *
     * @throws IllegalValueException if given date of birth string is invalid.
     */
    public DateOfBirth(String dob) throws IllegalValueException {
        requireNonNull(dob);
        if (dob.isEmpty()) {
            throw new EmptyFieldException(PREFIX_DOB);
        }
        if (!isValidDateOfBirth(dob)) {
            throw new IllegalValueException(MESSAGE_DOB_CONSTRAINTS);
        }
        this.finalDateOfBirth = dob;
    }

    /**
     * Returns true if a given string is a valid person date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        return test.matches(DOB_VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return finalDateOfBirth;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && this.finalDateOfBirth.equals(((DateOfBirth) other).finalDateOfBirth)); // state check
    }

    @Override
    public int hashCode() {
        return finalDateOfBirth.hashCode();
    }
}
