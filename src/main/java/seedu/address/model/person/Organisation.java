package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's Company in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrganisation(String)}
 */

//@@author darrinloh
public class Organisation {

    public static final String MESSAGE_ORGANISATION_CONSTRAINTS =
            "Person organisation can take any values";

    /*
     * The first character of the organisation must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ORGANISATION_VALIDATION_REGEX = "[^\\s].*";
    public static final String ORGANISATION_NOT_ASSIGNED = "~";

    public final String value;

    /**
     * Validates given organisation.
     *
     * @throws IllegalValueException if given organisation string is invalid.
     */
    public Organisation(String organisation) throws IllegalValueException {

        if (organisation == null) {
            this.value = ORGANISATION_NOT_ASSIGNED;
        } else {
            if (!isValidOrganisation(organisation)) {
                throw new IllegalValueException(MESSAGE_ORGANISATION_CONSTRAINTS);
            }
            this.value = organisation;
        }
    }

    /**
     * Returns true if a given string is a valid person organisation.
     */
    public static boolean isValidOrganisation(String test) {
        return test.matches(ORGANISATION_VALIDATION_REGEX) || test.matches(ORGANISATION_NOT_ASSIGNED);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Organisation // instanceof handles nulls
                && this.value.equals(((Organisation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

//@@author darrinloh
