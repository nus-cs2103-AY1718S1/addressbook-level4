package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's time in the address book.
 */
public class EventTime {


    public static final String MESSAGE_EVENT_TIME_CONSTRAINTS =
            "Event time should be in dd/mm/yyyy form, satisfy the reality, and be after 01/01/1900.";

    /**
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EVNET_TIME_VALIDATION_REGEX = "\\d\\d/\\d\\d/"
            + "(19|20)\\d\\d$";

    public final String eventTime;
    private String year;
    private String month;
    private String day;

    //@@author Adoby7
    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!isValidFormat(trimmedTime)) {
            throw new IllegalValueException(MESSAGE_EVENT_TIME_CONSTRAINTS);
        }
        this.eventTime = trimmedTime;
        splitTime(trimmedTime);

        if (!isValidTime()) {
            throw new IllegalValueException(MESSAGE_EVENT_TIME_CONSTRAINTS);
        }
    }

    /**
     * Splits the time into year, day, month
     */
    private void splitTime(String trimmedTime) {
        String[] splitTime = trimmedTime.split("/");
        day = splitTime[0];
        month = splitTime[1];
        year = splitTime[2];
    }

    public String orderForSort() {
        return year + month + day;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public boolean isValidTime() {
        return isValidMonth(Integer.parseInt(month))
                && isValidDay(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    /**
     * Returns true if a given string is a valid time
     */
    public static boolean isValidEventTime(String eventTime) {
        String trimmedTime = eventTime.trim();
        if (!isValidFormat(trimmedTime)) {
            return false;
        }
        String[] splitTime = trimmedTime.split("/");
        String day = splitTime[0];
        String month = splitTime[1];
        String year = splitTime[2];

        return isValidMonth(Integer.parseInt(month))
                && isValidDay(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    /**
     * Returns true if a given day is valid
     */
    private static boolean isValidDay(int year, int month, int day) {
        int[] daysInMonth = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year)) {
            return day >= 1 && day <= daysInMonth[month - 1] + 1;
        }

        return day >= 1 && day <= daysInMonth[month - 1];
    }

    /**
     * Returns true if a given month is valid
     */
    private static boolean isValidMonth(int month) {
        return month <= 12 && month >= 1;
    }

    /**
     * Returns true if a given year is a leap year
     */
    private static boolean isLeapYear(int year) {
        return (year % 400 == 0)
                || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * Returns true if a given time is valid formatted.
     */
    public static boolean isValidFormat(String test) {
        return test.matches(EVNET_TIME_VALIDATION_REGEX);
    }

    // @@author HuWanqing
    public String getDaysLeft() {
        long day = getDays();
        if (day < 0) {
            return Long.toString(-day) + "↑";
        }
        return Long.toString(day) + "↓";
    }

    public Long getDays() {
        long day = 0;
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String today = format.format(now);
        try {
            Date ddl = format.parse(eventTime);
            Date thisDay = format.parse(today);
            day = (ddl.getTime() - thisDay.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return day;
        }
        return day;
    }

    @Override
    public String toString() {
        return eventTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.EventTime // instanceof handles nulls
                && this.eventTime.equals(((seedu.address.model.event.EventTime) other).eventTime)); // state check
    }

    @Override
    public int hashCode() {
        return eventTime.hashCode();
    }
}
