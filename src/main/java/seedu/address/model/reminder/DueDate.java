package seedu.address.model.reminder;
//@@author RonakLakhotia
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents a Reminders DueDate in Weaver.
 */

public class DueDate {

    /**
     * Represents a Reminders DueDate
     * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
     */

    public static final int DAYS_IN_FEBRUARY = 28;
    public static final String DUE_DATE_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";

    public static final int FEBRUARY = 2;
    public static final int FIRST_INDEX = 0;
    public static final int INVALID_INDEX_OF_SEPARATOR = -1;
    public static final int LAST_INDEX = 1;
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Due Date must be a Valid Date and in the following format: \n"
                    + "'.' and '-' can be used as separators. \n";

    public static final int [] MONTHS_WITH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final Logger logger = LogsCenter.getLogger(DueDate.class);

    public final String date;




    public DueDate(String date) throws IllegalValueException {

        String trimmedDate;
        String nullValueOfDate = "";
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if (!date.equals(nullValueOfDate)) {
            trimmedDate = date.trim();
            this.date = trimmedDate;
        } else {
            this.date = nullValueOfDate;
        }

    }
    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidDate(String dueDate) {

        String trimmedDate = dueDate.trim();
        if (trimmedDate.isEmpty()) {
            return false;
        }
        if (!trimmedDate.matches(DUE_DATE_VALIDATION_REGEX)) {
            logger.info("Invalid format of date.");
            return false;
        }
        boolean isValidDate = checkIfValidDate(dueDate);

        return isValidDate;

    }

    /**
     * Checks if number of days in a given month are valid
     * Returns true if the given date is valid, that is has valid number of days in a month.
     */
    public static boolean checkIfValidDate(String date) {


        int lastIndexOfSeparator = getIndexOfSeparator(date, LAST_INDEX);
        int firstIndexOfSeparator = getIndexOfSeparator(date, FIRST_INDEX);
        int yearNumber = getYearNumber(lastIndexOfSeparator, date);
        int monthNumber = getMonthNumber(firstIndexOfSeparator, lastIndexOfSeparator, date);
        boolean isLeapYear = checkIfLeapYear(yearNumber);
        int dayNumber = getDayNumber(firstIndexOfSeparator, date);
        boolean isValidDateOfFebruary = checkIfValidDateOfFebruary(
                date, monthNumber, yearNumber, dayNumber, isLeapYear);
        boolean isValidNumberOfDaysInMonth = checkIfValidNumberOfDaysInMonth(monthNumber, dayNumber);

        if (isValidDateOfFebruary && isValidNumberOfDaysInMonth) {
            return true;
        }
        return false;

    }
    /**
     * Returns true if number of days in February are valid else return false.
     */
    public static boolean checkIfValidDateOfFebruary(String date, int month, int year, int day, boolean isLeapYear) {

        if (month == FEBRUARY) {

            if (isLeapYear && day > (DAYS_IN_FEBRUARY + 1) || (!isLeapYear && day > DAYS_IN_FEBRUARY)) {
                logger.warning("Invalid number of days in February");
                return false;
            }
        }
        return true;
    }
    /**
     * Returns true number of number of days in a month are valid else returns false.
     */
    public static boolean checkIfValidNumberOfDaysInMonth(int month, int day) {

        if (MONTHS_WITH_DAYS[month - 1] < day && month != FEBRUARY) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if year is a leap year.
     */
    public static boolean checkIfLeapYear(int yearNumber) {

        if (yearNumber % 400 == 0 || (yearNumber % 4 == 0 && yearNumber % 100 != 0)) {
            return true;
        }
        return false;
    }
    /**
     * Returns the year number in the date.
     */
    public static int getYearNumber(int lastIndexOfSeparator, String date) {

        String year = date.substring(lastIndexOfSeparator + 1);
        return Integer.parseInt(year);
    }
    /**
     * Returns the month number in the date.
     */
    public static int getMonthNumber(int firstIndexOfSeparator, int lastIndexOfSeparator, String date) {

        String month = date.substring(firstIndexOfSeparator + 1, lastIndexOfSeparator);
        return Integer.parseInt(month);
    }
    /**
     * Returns the day number in the date.
     */
    public static int getDayNumber(int firstIndexOfSeparator, String date) {

        String dayNumber = date.substring(0, firstIndexOfSeparator);
        return Integer.parseInt(dayNumber);
    }
    /**
     * Returns the index of the separator '-' or '.' depending on the position.
     */

    public static int getIndexOfSeparator(String date, int position) {

        int storesIndex = INVALID_INDEX_OF_SEPARATOR;
        for (int loopVariable = 0; loopVariable < date.length(); loopVariable++) {

            if (date.charAt(loopVariable) == '.' || date.charAt(loopVariable) == '-') {
                storesIndex = loopVariable;

                if (position == FIRST_INDEX) {
                    break;
                }
            }
        }
        assert storesIndex > INVALID_INDEX_OF_SEPARATOR;
        return storesIndex;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DueDate
                && this.date.equals(((DueDate) other).date));
    }
    @Override
    public int hashCode() {
        return date.hashCode();
    }


}
