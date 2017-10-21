//@@author A0162268B
package seedu.address.model.event.timing;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.event.DateParser;

/**
 * Represents an Timings's date in sales navigator.
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should represent";

    public final LocalDate date;
    public final String dateAsString;

    /**
     * Validates given title.
     *
     * @throws IllegalValueException if given title string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();

        DateParser parser = new DateParser();

        try {
            int[] dateInfo = parser.parse(trimmedDate);
            LocalDate dateToSave = LocalDate.of(dateInfo[0], dateInfo[1], dateInfo[2]);
            this.date = dateToSave;
        } catch (IllegalValueException e) {
            throw e;
        }

        this.dateAsString = trimmedDate;
    }

    @Override
    public String toString() {
        return dateAsString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return dateAsString.hashCode();
    }
}
