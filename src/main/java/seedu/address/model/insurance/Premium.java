package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.EmptyFieldException;

//@@author OscarWang114
/**
 * Represents a insurance's premium in LISA.
 * Guarantees: immutable; is valid as declared in {@link #isValidPremium(String)}
 */
public class Premium {


    public static final String MESSAGE_PREMIUM_CONSTRAINTS =
            "Premium can only contain numbers with one optional decimal point";
    public static final String PREMIUM_VALIDATION_REGEX = "\\d+(\\.\\d+)?";
    public final Double value;


    /**
     * Validates given premium.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Premium(String premium) throws IllegalValueException {
        requireNonNull(premium);
        String trimmedPremium = premium.trim();
        if (trimmedPremium.isEmpty()) {
            throw new EmptyFieldException(PREFIX_PREMIUM);
        }
        if (!isValidPremium(trimmedPremium)) {
            throw new IllegalValueException(MESSAGE_PREMIUM_CONSTRAINTS);
        }
        Double premiumValue =  Double.parseDouble(trimmedPremium);
        this.value = premiumValue;
    }

    /**
     * Returns true if a given string is a valid insurance premium.
     */
    public static boolean isValidPremium(String test) {
        return test.matches(PREMIUM_VALIDATION_REGEX);
    }

    public Double toDouble() {
        return this.value;
    }

    //@@author Juxarius
    @Override
    public String toString() {
        return "S$ " + String.format("%.2f", value);
    }
    //@@author

    //@@author OscarWang114
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Premium// instanceof handles nulls
                && this.value.equals(((Premium) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    //
}
