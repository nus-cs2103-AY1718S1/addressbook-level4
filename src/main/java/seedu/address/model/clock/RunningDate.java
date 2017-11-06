package seedu.address.model.clock;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

/**
 * Represents a running date inside the address book application.
 */
public class RunningDate {

    private DayOfWeek dayOfWeek;
    private int dayOfMonth;
    private Month month;
    private int year;

    public RunningDate() {
        setCurrentDate();
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setCurrentDate() {
        this.dayOfWeek = LocalDateTime.now().getDayOfWeek();
        this.dayOfMonth = LocalDateTime.now().getDayOfMonth();
        this.month = LocalDateTime.now().getMonth();
        this.year = LocalDateTime.now().getYear();
    }

    @Override
    public String toString() {
        return dayOfWeek.toString() + " " + dayOfMonth + " " + month.toString() + " " + year;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RunningDate // instanceof handles nulls
                && this.dayOfWeek.equals(((RunningDate) other).dayOfWeek) // state checks onwards
                && this.dayOfMonth == ((RunningDate) other).dayOfMonth
                && this.month.equals(((RunningDate) other).month)
                && this.year == ((RunningDate) other).year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, dayOfMonth, month, year);
    }

}
