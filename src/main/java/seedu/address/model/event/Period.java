//@@author shuang-yang
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

/**
 * Represents an Event's period of repetition in the address book.
 * Guarantees: is valid as declared in {@link #isValidPeriod(String)}
 */
public class Period {
    public static final String MESSAGE_PERIOD_CONSTRAINTS =
            "Period should be positive integers smaller than 366 (days)";

    /*
     *
     */
    public static final String PERIOD_VALIDATION_REGEX = "([0-9]|[1-9][0-9]|[1-2][0-9][0-9]|3[0-5][0-9]|36[0-6])";

    public final String period;

    /**
     * Validates given period.
     *
     * @throws IllegalValueException if given period string is invalid.
     */
    public Period(String period) throws IllegalValueException {
        requireNonNull(period);
        String trimmedPeriod = period.trim();
        if (!isValidPeriod(trimmedPeriod)) {
            throw new IllegalValueException(MESSAGE_PERIOD_CONSTRAINTS);
        }
        this.period = trimmedPeriod;
    }

    /**
     * Returns true if a given string is a valid event period.
     * @param test string
     */
    public static boolean isValidPeriod(String test) {
        return !test.equals("") && test.matches(PERIOD_VALIDATION_REGEX);
    }

    /**
     * Generate an Optional Period object based on given string.
     * @param periodString
     * @return an Optional Period object
     */
    public static Optional<Period> generatePeriod(String periodString) {
        Optional<Period> period;
        try {
            period = ParserUtil.parsePeriod(Optional.of(periodString));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Period not recognized.");
        }
        return period;
    }


    @Override
    public String toString() {
        return period;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Period // instanceof handles nulls
                && this.period.equals(((Period) other).period)); // state check
    }

    @Override
    public int hashCode() {
        return period.hashCode();
    }
}
