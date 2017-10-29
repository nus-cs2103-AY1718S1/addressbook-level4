package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import seedu.address.model.person.Name;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents the user's schedule in the address book.
 */
public class Schedule {

    public static final String DATE = "next Tuesday 4pm";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE);
    public static final String SCHEDULE_VALIDATION_REGEX = "([\\w\\.]+ [\\w\\.]+)|(\\(Schedule not recorded\\))";

    private String personName;
    private Date date;

    public Schedule(String name, Calendar calendar) {
        requireNonNull(name);
        requireNonNull(calendar);
        Date date = calendar.getTime();
        this.personName = name;
        this.date = date;
    }

    public Schedule(String name) {
        this.personName = name;
    }

    public String getPersonName() {
        return this.personName;
    }

    public Date getDate() {
        return this.date;
    }

    /**
     * Returns if a given string is a valid person Schedule.
     */
    public static boolean isValidSchedule(String test) {
        return test.matches(SCHEDULE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (date == null) {
            return "Schedule not fixed with " + getPersonName();
        } else {
            return "Schedule is fixed with " + getPersonName() + " on " + DATE_FORMAT.format(date);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.personName.equals(((Schedule) other).personName)
                && this.date.equals(((Schedule) other).date));
    }

    @Override
    public int hashCode() {
        return personName.hashCode();
    }

}
