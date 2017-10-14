package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author lawwman
/**
 * Represents a Person's debt or amount owed in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDebt(String)}
 */
public class Debt {

    public static final String MESSAGE_DEBT_CONSTRAINTS =
            "Debt can only contain numbers, and should have 1 or more digits";
    public static final String DEBT_ZERO_VALUE = "0";
    public static final String DEBT_VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Validates given debt.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Debt(String debt) throws IllegalValueException {
        requireNonNull(debt);
        String trimmedDebt = debt.trim();
        if (!isValidDebt(trimmedDebt)) {
            throw new IllegalValueException(MESSAGE_DEBT_CONSTRAINTS);
        }
        this.value = trimmedDebt;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidDebt(String test) {
        return test.matches(DEBT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Debt // instanceof handles nulls
                && this.value.equals(((Debt) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
