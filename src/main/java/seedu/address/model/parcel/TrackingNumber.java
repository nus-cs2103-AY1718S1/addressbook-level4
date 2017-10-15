package seedu.address.model.parcel;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the article number of a parcel.
 * @link { https://en.wikipedia.org/wiki/Registered_mail}
 */
public class TrackingNumber {

    public static final String MESSAGE_TRACKING_NUMBER_CONSTRAINTS =
            "Parcel article number should start with 'RR', followed by 9 digits, and ends with 'SG'";
    public static final String ARTICLE_NUMBER_VALIDATION_REGEX = "^[R]{2}[0-9]{9}[S][G]$";

    public final String value;

    /**
     * Validates given trackingNumber.
     *
     * @throws IllegalValueException if given trackingNumber string is invalid.
     */
    public TrackingNumber(String trackingNumber) throws IllegalValueException {
        requireNonNull(trackingNumber);
        String trimmedArticleNumber = trackingNumber.trim();
        if (!isValidArticleNumber(trimmedArticleNumber)) {
            throw new IllegalValueException(MESSAGE_TRACKING_NUMBER_CONSTRAINTS);
        }
        this.value = trimmedArticleNumber;
    }

    /**
     * Returns true if a given string is a valid parcel article number.
     */
    public static boolean isValidArticleNumber(String test) {
        return test.matches(ARTICLE_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TrackingNumber // instanceof handles nulls
                && this.value.equals(((TrackingNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
