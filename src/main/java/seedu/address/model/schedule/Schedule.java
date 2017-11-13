package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

//@@author limcel
/**
 * Represents the user's schedule in the address book.
 */
public class Schedule {

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

    @Override
    public String toString() {
        if (date == null) {
            return "Schedule not fixed with " + getPersonName();
        } else {
            return "Schedule is fixed with " + getPersonName() + " on " + date;
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
        return Objects.hash(personName, date);
    }
}
