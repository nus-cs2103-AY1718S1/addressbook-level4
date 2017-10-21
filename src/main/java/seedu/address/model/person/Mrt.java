package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.Set;

import static java.util.Objects.requireNonNull;
/**
 * Represents a Person's mrt station in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMrt(String)}
 */
public class Mrt {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Person's MRT station should be an existing MRT station";

    /*
    * The first character of the address must not be a whitespace,
    * otherwise " " (a blank string) becomes a valid input.
    */
    public static final String MRT_VALIDATION_REGEX = "[^\\s].*";

    public final Set<String> validMrt = ValidMrt.validMrt.keySet();

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Mrt(String mrt) throws IllegalValueException {
        requireNonNull(mrt);
        if (!isValidMrt(mrt)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = mrt;
    }

    /**
     * Returns if a given string is a valid Mrt station.
     */
    public boolean isValidMrt(String test) {
        boolean check1 = test.matches(MRT_VALIDATION_REGEX);
        boolean check2 = ValidMrt.contains(test);
        return check1 && check2;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mrt // instanceof handles nulls
                && this.value.equals(((Mrt) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

