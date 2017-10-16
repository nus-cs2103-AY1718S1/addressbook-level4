package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateUtil.MESSAGE_DATE_CONSTRAINTS;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

/**
 * Represents a Schedule's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidScheduleDate(String)}
 */
public class ScheduleDate {

    public static final String MESSAGE_SCHEDULE_DATE_CONSTRAINTS = String.format(MESSAGE_DATE_CONSTRAINTS,
            "a schedule");

    public final String value;

    /**
     * Validates given schedule date.
     *
     * @throws IllegalValueException if given {@code date} string is invalid.
     */
    public ScheduleDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidScheduleDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_SCHEDULE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given string is a valid date
     */
    private boolean isValidScheduleDate(String test) {
        return DateUtil.isValid(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleDate // instanceof handles nulls
                && this.value.equals(((ScheduleDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

