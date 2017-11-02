package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author lawwman
/**
 * Represents a Person's debt or amount owed in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDebt(String)}
 */
public class Debt {

    public static final String MESSAGE_DEBT_CONSTRAINTS = "Debt must have at least 1 digit and be either "
            + "a positive integer or a positive number with two decimal places";
    // validation regex validates empty string. Check for presence of at least 1 digit is needed.
    public static final String DEBT_VALIDATION_REGEX = "^(?=.*\\d)\\d*(?:\\.\\d\\d)?$";
    public static final String DEBT_ZER0_VALUE = "0";
    private String value;

    /**
     * Validates given debt.
     *
     * @throws IllegalValueException if given debt string is invalid.
     */
    public Debt(String debt) throws IllegalValueException {
        requireNonNull(debt);
        String trimmedDebt = debt.trim();
        if (!isValidDebt(trimmedDebt)) {
            throw new IllegalValueException(MESSAGE_DEBT_CONSTRAINTS);
        }
        this.value = String.format("%.2f", Double.valueOf(trimmedDebt));
    }

    /**
     * Validates given debt.
     *
     * @throws IllegalValueException if given debt double is invalid.
     */
    public Debt(Double debt) throws IllegalValueException {
        requireNonNull(debt);
        String debtInString = String.format("%.2f", Double.valueOf(debt));
        if (!isValidDebt(debtInString)) {
            throw new IllegalValueException(MESSAGE_DEBT_CONSTRAINTS);
        }
        this.value = debtInString;
    }

    /**
     * Returns true if a given string is a valid person debt.
     */
    public static boolean isValidDebt(String test) {
        return test.matches(DEBT_VALIDATION_REGEX) && test.length() >= 1;
    }

    /**
     * Returns the double value represented by the string {@code value}
     */
    public double toNumber() {
        return Double.valueOf(value);
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

    public int compareTo(Debt other) {
        return (int) (this.toNumber() - other.toNumber());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
