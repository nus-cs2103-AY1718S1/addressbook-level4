package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Schedule's date string in the address book.
 * Guarantees: immutable; Valid schedule date
 */
public class ScheduleDate {

    public static final String MESSAGE_SCHEDULE_DATE_CONSTRAINTS =
            "Schedule date should be in the following format: "
            + "YYYY-MM-DD HH:MM";


    public final String scheduleDate;

    /**
     * Validates given name.
     *
     * @throws ParseException if given name string is invalid.
     */
    public ScheduleDate(String scheduleDate) throws ParseException {
        requireNonNull(scheduleDate);
        DateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        try {
            dateInput.parse(scheduleDate).toString();
        } catch (java.text.ParseException e) {
            throw new ParseException(MESSAGE_SCHEDULE_DATE_CONSTRAINTS);
        }

        this.scheduleDate = scheduleDate;

    }

    @Override
    public String toString() {
        return scheduleDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleDate // instanceof handles nulls
                && this.scheduleDate.equals(((ScheduleDate) other).scheduleDate)); // state check
    }

    @Override
    public int hashCode() {
        return scheduleDate.hashCode();
    }

}
