//@@author duyson98

package seedu.address.model.clock;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a running date inside the address book application.
 */
public class RunningDate {

    private DayOfWeek dayOfWeek;
    private int dayOfMonth;
    private MonthOfYear month;
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

    public MonthOfYear getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setCurrentDate() {
        this.dayOfWeek = DayOfWeek.valueOf(LocalDateTime.now().getDayOfWeek().getValue());
        this.dayOfMonth = LocalDateTime.now().getDayOfMonth();
        this.month = MonthOfYear.valueOf(LocalDateTime.now().getMonth().getValue());
        this.year = LocalDateTime.now().getYear();
    }

    @Override
    public String toString() {
        return dayOfWeek.toString() + ", " + dayOfMonth + " " + month.toString() + ", " + year;
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

/**
 * Represents days of a week.
 */
enum DayOfWeek {

    Monday(1), Tuesday(2), Wedsnesday(3), Thurday(4), Friday(5), Saturday(6), Sunday(7);

    private static Map<Integer, DayOfWeek> map = new HashMap<>();
    private int index;

    DayOfWeek(int index) {
        this.index = index;
    }

    static {
        for (DayOfWeek item : DayOfWeek.values()) {
            map.put(item.index, item);
        }
    }

    public static DayOfWeek valueOf(int index) {
        return map.get(index);
    }

}

/**
 * Represents months of a year.
 */
enum MonthOfYear {

    January(1), February(2), March(3), April(4), May(5), June(6), July(7), August(8), September(9),
    October(10), November(11), December(12);

    private static Map<Integer, MonthOfYear> map = new HashMap<>();
    private int index;

    MonthOfYear(int index) {
        this.index = index;
    }

    static {
        for (MonthOfYear item : MonthOfYear.values()) {
            map.put(item.index, item);
        }
    }

    public static MonthOfYear valueOf(int index) {
        return map.get(index);
    }

}
