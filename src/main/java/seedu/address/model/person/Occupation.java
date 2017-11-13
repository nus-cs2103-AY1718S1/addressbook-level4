package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author JavynThun
/**
 * Represents a Person's occupation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOccupation(String)}
 */
public class Occupation {

    public static final String MESSAGE_OCCUPATION_CONSTRAINTS =
            "Person occupation should be 2 alphanumeric strings separated by ','"
                    + " in the form of [COMPANY NAME] , [JOB TITLE]";
    public static final String OCCUPATION_VALIDATION_REGEX = "[\\w\\s]+\\,\\s[\\w\\s]+";

    public final String value;

    /**
     * Validates given occupation.
     *
     * @throws IllegalValueException if given occupation string is invalid.
     */
    public Occupation(String occupation) throws IllegalValueException {
        //requireNonNull(occupation);
        if (occupation == null) {
            this.value = "";
        } else {
            String trimmedOccupation = occupation.trim();
            if (trimmedOccupation.length() > 0 && !isValidOccupation(trimmedOccupation)) {
                throw new IllegalValueException(MESSAGE_OCCUPATION_CONSTRAINTS);
            }
            this.value = trimmedOccupation;
        }
    }

    /**
     * Returns if a given string is a valid person occupation.
     */
    public static boolean isValidOccupation(String test) {
        return test.matches(OCCUPATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Occupation // instanceof handles nulls
                && this.value.equals(((Occupation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
