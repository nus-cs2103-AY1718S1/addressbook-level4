package seedu.address.model.relationship;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

public class ConfidenceEstimate {
    public static final ConfidenceEstimate UNSPECIFIED = new ConfidenceEstimate();
    public static final String MESSAGE_CONFIDENCE_ESTIMATE_CONSTRAINTS =
            "Confidence estimates should be a single number without spaces";

    public final Double value;

    /**
     * The default ConfidenceEstimate constructor when confidence estimate is not specified by the user
     */
    private ConfidenceEstimate() {
        value = (double) 0;
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

    /**
     * Returns true if a given string is a valid confidence estimate.
     */
    public static boolean isValidConfidenceEstimate(String test) {
        try {
            Double d = Double.parseDouble(test);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConfidenceEstimate // instanceof handles nulls
                && this.value.equals(((ConfidenceEstimate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
