package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author kaiyu92

/**
 * Represents a Event's Datetime in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDatetime(String)}
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event datetime should only contain dd-mm-yyyy hhmm";

    private static final int VALID_DATETIME_LENGTH = 15;

    public final String value;

    /**
     * Validates given datetime.
     *
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public Datetime(String datetime) throws IllegalValueException {
        requireNonNull(datetime);
        String trimmedDatetime = datetime.trim();

        if (!isValidDatetime(trimmedDatetime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = trimmedDatetime;
    }

    /**
     * Returns true if a given string is a valid event datetime.
     */
    public static boolean isValidDatetime(String test) {
        Boolean validTime = false;
        Boolean validDate = false;

        if (test.length() != VALID_DATETIME_LENGTH) {
            return false;
        }

        try {
            int day = Integer.parseInt(test.substring(0, 2));
            int month = Integer.parseInt(test.substring(3, 5));
            int year = Integer.parseInt(test.substring(6, 10));
            int hour = Integer.parseInt(test.substring(11, 13));
            int min = Integer.parseInt(test.substring(13, 15));

            //Check Time Validation
            if (0 <= hour && hour <= 23) {
                if (0 <= min && min <= 59) {
                    validTime = true;
                }
            }

            //Check Date Validation
            if (day >= 1) {
                // For months with 30 days.
                if ((month == 4
                        || month == 6
                        || month == 9
                        || month == 11)
                        && day <= 30) {
                    validDate = true;
                }
                // For months with 31 days.
                if ((month == 1
                        || month == 3
                        || month == 5
                        || month == 7
                        || month == 8
                        || month == 10
                        || month == 12)
                        && day <= 31) {
                    validDate = true;
                }
                // For February.
                if (month == 2) {
                    if (day <= 28) {
                        validDate = true;
                    } else if (day == 29) {
                        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                            validDate = true;
                        }
                    }
                }
            } //else date is not valid
        } catch (NumberFormatException e) {
            return false;
        }
        return validTime && validDate;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Datetime // instanceof handles nulls
                && this.value.equals(((Datetime) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
