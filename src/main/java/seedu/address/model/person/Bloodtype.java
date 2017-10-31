package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Ernest
/**
 * Represents a Person's blood type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class Bloodtype {

    public static final String MESSAGE_BLOODTYPE_CONSTRAINTS = "Person blood type should not be blank.\n"
            + "Valid inputs are: A, A+, A-, B, B+, B-, O, O+, O-, AB, AB+, AB-. \n"
            + "Both capital letters and small letters are allowed.";

    // Checks for a, b, ab, or o at start of string.
    // Characters are case insensitive.
    // Next check is for "+" or "-". "+" and "-" does not have to be added.
    // Credit to lena15n for assistance with regex
    public static final String NON_COMPULSORY_BLOODTYPE = "xxx";
    public static final String BLOODTYPE_VALIDATION_REGEX = "(?i)^(a|b|ab|o|xxx)[\\+|\\-]{0,1}$";

    public final String type;

    /**
     * Validates given bloodtype.
     *
     * @throws IllegalValueException if given bloodtype string is invalid.
     */
    public Bloodtype(String bloodType) throws IllegalValueException {
        requireNonNull(bloodType);
        String trimmedBloodType = bloodType.trim();
        if (!isValidBloodType(trimmedBloodType)) {
            throw new IllegalValueException(MESSAGE_BLOODTYPE_CONSTRAINTS);
        }
        this.type = bloodType.toUpperCase();
    }


    /**
     * Returns true if a given string is a valid person blood type.
     */
    public static boolean isValidBloodType(String test) {
        return test.matches(BLOODTYPE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bloodtype // instanceof handles nulls
                && this.type.equals(((Bloodtype) other).type)); // state check
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

}
