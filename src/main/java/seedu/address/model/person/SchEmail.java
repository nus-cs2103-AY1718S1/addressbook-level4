package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSchEmail(String)}
 */
public class SchEmail {

    public static final String MESSAGE_SCH_EMAIL_CONSTRAINTS =
            "School emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String SCH_EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";
    public static final String SCH_EMAIL_TEMPORARY = "NIL";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public SchEmail(String schEmail) throws IllegalValueException {
        if(schEmail == null){
            this.value = SCH_EMAIL_TEMPORARY;
        } else {
            String trimmedSchEmail = schEmail.trim();
            if (!isValidSchEmail(trimmedSchEmail)) {
                throw new IllegalValueException(MESSAGE_SCH_EMAIL_CONSTRAINTS);
            }
            this.value = trimmedSchEmail;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidSchEmail(String test) {
        return test.matches(SCH_EMAIL_VALIDATION_REGEX)
                || test.matches(SCH_EMAIL_TEMPORARY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SchEmail // instanceof handles nulls
                && this.value.equals(((SchEmail) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
