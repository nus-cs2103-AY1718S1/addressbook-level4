package seedu.address.model.relationship;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author wenmogu
/**
 * This is a value of how confident the user is towards the information recorded.
 */
public class ConfidenceEstimate {
    public static final ConfidenceEstimate UNSPECIFIED = new ConfidenceEstimate();
    public static final String MESSAGE_CONFIDENCE_ESTIMATE_CONSTRAINTS =
            "Confidence estimates should be a single number between 0 and 100 without spaces";

    public final double value;

    /**
     * The default ConfidenceEstimate constructor when confidence estimate is not specified by the user
     */
    private ConfidenceEstimate() {
        value = 0;
    }

    /**
     * Validates a given confidence estimate.
     *
     * @throws IllegalValueException if the given confidence estimate string is invalid.
     */
    public ConfidenceEstimate(String estimate) throws IllegalValueException {
        requireNonNull(estimate);
        String trimmedEstimate = estimate.trim();
        if (!isValidConfidenceEstimate(trimmedEstimate)) {
            throw new IllegalValueException(MESSAGE_CONFIDENCE_ESTIMATE_CONSTRAINTS);
        }
        this.value = Double.parseDouble(trimmedEstimate);
    }

    public ConfidenceEstimate(double estimate) throws IllegalValueException {
        if (estimate >= 0 && estimate <= 100) {
            value = estimate;
        } else {
            throw new IllegalValueException(MESSAGE_CONFIDENCE_ESTIMATE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid confidence estimate.
     */
    public static boolean isValidConfidenceEstimate(String test) {
        double d;
        try {
            d = Double.parseDouble(test);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return d >= 0 && d <= 100;
    }


    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConfidenceEstimate // instanceof handles nulls
                && this.value == (((ConfidenceEstimate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
