package seedu.address.model.person.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author royceljh
/**
 * Represents a Event's header in the address book.
 * Guarantees: immutable; is valid as declared in {@link #EventDate(String)}
 */
public class EventDate {

    public static final String MESSAGE_EVENT_DATE_CONSTRAINTS =
            "Event must have a valid date input\n"
                    + "Format: year-month-day (yyyy-mm-dd)\n"
                    + "Example: 2018-02-20";

    public final String value;
    public final LocalDate eventLocalDate;
    private Period period;
    private String countDown;

    /**
     * Validates given eventDate.
     *
     * @throws IllegalValueException if given eventDate string is invalid.
     */
    public EventDate(String eventDate) throws IllegalValueException {
        requireNonNull(eventDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            eventLocalDate = LocalDate.parse(eventDate, formatter);
        } catch (DateTimeParseException ex) {
            throw new IllegalValueException(MESSAGE_EVENT_DATE_CONSTRAINTS, ex);
        }
        getCountDown();
        this.value = eventDate + "\n" + countDown;
    }

    private void getCountDown() {
        ZoneId sgt = ZoneId.of("GMT+8");
        LocalDate currentDate = LocalDate.now(sgt);
        this.period = currentDate.until(eventLocalDate);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        if (period.isNegative()) {
            this.countDown = "Event is overdue.";
        } else if (period.isZero()) {
            this.countDown = "Event is today!";
        } else {
            this.countDown = "Event in: " + years + "years " + months + "months " + days + "days";
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDate // instanceof handles nulls
                && this.value.equals(((EventDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
