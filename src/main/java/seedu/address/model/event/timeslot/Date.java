package seedu.address.model.event.timeslot;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.event.DateParser;

//@@author reginleiff
/**
 * Represents an Timeslot's date in sales navigator.
 * Is valid as declared in {@link #isValidDate(int, int, int)}
 */
public class Date implements Comparable<Date> {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should represent a valid date in the gregorian calendar";

    private int day;
    private int month;
    private int year;
    private String date;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        requireNonNull(date);

        String trimmedDate = date.trim();
        DateParser parser = new DateParser();

        int[] dateInfo = parser.parse(trimmedDate);
        //Check if valid gregorian date
        this.day = dateInfo[0];
        this.month = dateInfo[1];
        this.year = dateInfo[2];

        if (isValidDate(year, month, day)) {
            this.date = trimmedDate;
        } else {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }

    }

    //@@author shuang-yang
    public Date(LocalDate localDate) {
        this.day = localDate.getDayOfMonth();
        this.month = localDate.getMonthValue();
        this.year = localDate.getYear();
    }
    //@@author

    /**
     * Checks if the given arguments for a date is valid in the gregorian calendar.
     */
    public boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public int compareTo(Date other) {
        int thisYear = this.getYear();
        int comparingYear = other.getYear();
        int thisMonth = this.getMonth();
        int comparingMonth = other.getMonth();
        int thisDay = this.getDay();
        int comparingDay = other.getDay();

        if (thisYear != comparingYear) {
            return thisYear - comparingYear;
        } else if (thisMonth != comparingMonth) {
            return thisMonth - comparingMonth;
        } else {
            return thisDay - comparingDay;
        }
    }

    // @@author shuang-yang
    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }
    //@@author

    /**
     * Sets date after {@code days} have elapsed. Works for negative numbers.
     */
    public Date addDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.set(this.year, this.month, this.day);
        cal.add(Calendar.DATE, days);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        try {
            return new Date(dateBuilder(day, month, year));
        } catch (IllegalValueException e) {
            return null;
        }
    }

    /**
     * Builds a string in the format of a correct date.
     */
    private String dateBuilder(int day, int month, int year) {
        String date = "";
        if (day < 10) {
            date += "0" + String.valueOf(day) + "/";
        } else {
            date += String.valueOf(day) + "/";
        }

        if (month < 10) {
            date += "0" + String.valueOf(month) + "/";
        } else {
            date += String.valueOf(month) + "/";
        }

        date += String.valueOf(year);

        return date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    //================================= Setter methods for testing ==========================================

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
