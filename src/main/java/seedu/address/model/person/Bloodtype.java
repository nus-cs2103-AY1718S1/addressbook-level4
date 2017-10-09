package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's blood type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class Bloodtype {

    public static final String MESSAGE_BLOODTYPE_CONSTRAINTS =
            "Person blood type should only contain one or two alpha characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * More specific blood types and difference are taken into account.
     * Example: O-, AB+

     */
    public static final String BLOODTYPE_VALIDATION_REGEX = "\\p{ASCII}{1,3}";

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
        this.type = bloodType;
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
