//@@author sebtsh
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's company in the address book.
 */

public class Company {
    public static final String MESSAGE_COMPANY_CONSTRAINTS =
            "Person company can take any values, and it should not be blank";

    /*
     * The first character of the company must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String COMPANY_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given company.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Company(String company) throws IllegalValueException {
        requireNonNull(company);
        if (!isValidCompany(company)) {
            throw new IllegalValueException(MESSAGE_COMPANY_CONSTRAINTS);
        }
        this.value = company;
    }

    /**
     * Returns true if a given string is a valid company.
     */
    public static boolean isValidCompany(String test) {
        return test.matches(COMPANY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Company // instanceof handles nulls
                && this.value.equals(((Company) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
