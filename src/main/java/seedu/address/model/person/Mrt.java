package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's mrt station in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMrt(String)}
 */
public class Mrt {

    public static final String MESSAGE_MRT_CONSTRAINTS =
            "Person's MRT station should be an existing MRT station in: "
                    + "North-South, East-West, Circle and North-West Line /n"  //other lines to be added soon
                    + "Example: Jurong East";

    /*
    * The first character of the address must not be a whitespace,
    * otherwise " " (a blank string) becomes a valid input.
    */
    public static final String MRT_VALIDATION_REGEX = "[^\\s].*";

    public static final Set<String> VALID_MRT = ValidMrt.VALID_MRT_SET.keySet();

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Mrt(String mrt) throws IllegalValueException {
        requireNonNull(mrt);
        if (!isValidMrt(mrt)) {
            throw new IllegalValueException(MESSAGE_MRT_CONSTRAINTS);
        }
        this.value = mrt;
    }

    /**
     * Returns if a given string is a valid Mrt station.
     */
    public static boolean isValidMrt(String test) {
        boolean check1 = test.matches(MRT_VALIDATION_REGEX);
        boolean check2 = VALID_MRT.contains(test);
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

