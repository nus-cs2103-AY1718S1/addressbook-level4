package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's DateOfBirth in the address book.
 */

public class DateOfBirth {

    /**
     * Represents a Person's Date Of birth
     * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
     */

    public static final String BIRTHDAY_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";


    public static final int DAYS_IN_FEBRUARY = 28;
    public static final int FEBRUARY = 2;
    public static final int FIRST_INDEX = 0;
    public static final int LAST_INDEX = 1;

    public static final int [] MONTHS_WITH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Date of Birth must be a Valid Date that is days should be less than 32 , months should be less"
                    + " than 12\n.For the month of February make sure days are less than 29 unless it is a leap year.\n"
                    + "The following format should be followed: \n"
            + "'.' and '/' can be used as separators. \n";

    public final String date;


    public DateOfBirth(String date) throws IllegalValueException {

        String trimmedDate;
        if (!isValidBirthday(date)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        if (!date.equals("")) {
            trimmedDate = date.trim();
            this.date = trimmedDate;
        } else {
            this.date = "";
        }

    }
    @Override
    public String toString() {
        return date;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String birthday) {

        String trimmedBirthday = birthday.trim();

        if (trimmedBirthday.isEmpty()) {
            return true;
        }
        if (!trimmedBirthday.matches(BIRTHDAY_VALIDATION_REGEX)) {
            return false;
        }
        boolean isValidDate = checkIfValidDate(birthday);

        return isValidDate;
    }
    /**
     * Checks for invalid conditions of a date
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
     * Checks if number of days in February are valid
     */
    public static boolean checkIfValidDateOfFebruary(String date, int month, int year, int day, boolean isLeapYear) {

        if (month == FEBRUARY) {

            if (isLeapYear && day > (DAYS_IN_FEBRUARY + 1) || (!isLeapYear && day > DAYS_IN_FEBRUARY)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks if number of days in a given month are valid
     */
    public static boolean checkIfValidNumberOfDaysInMonth(int month, int day) {

        if (MONTHS_WITH_DAYS[month - 1] < day) {
            return false;
        }

        return true;

    }
    /**
     * Checks if it is a leap year
     */
    public static boolean checkIfLeapYear(int yearNumber) {

        if (yearNumber % 400 == 0 || (yearNumber % 4 == 0 && yearNumber % 100 != 0)) {
            return true;
        }
        return false;
    }

    public static int getYearNumber(int lastIndexOfSeparator, String date) {

        String year = date.substring(lastIndexOfSeparator + 1);
        return Integer.parseInt(year);
    }
    public static int getMonthNumber(int firstIndexOfSeparator, int lastIndexOfSeparator, String date) {

        String month = date.substring(firstIndexOfSeparator + 1, lastIndexOfSeparator);
        return Integer.parseInt(month);
    }
    public static int getDayNumber(int firstIndexOfSeparator, String date) {

        String dayNumber = date.substring(0, firstIndexOfSeparator);
        return Integer.parseInt(dayNumber);
    }

    public static int getIndexOfSeparator(String date, int position) {

        int storesIndex = -1;
        for (int loopVariable = 0; loopVariable < date.length(); loopVariable++) {

            if (date.charAt(loopVariable) == '.' || date.charAt(loopVariable) == '-') {
                storesIndex = loopVariable;

                if (position == FIRST_INDEX) {
                    break;
                }
            }
        }
        return storesIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateOfBirth
                && this.date.equals(((DateOfBirth) other).date));
    }
    @Override
    public int hashCode() {
        return date.hashCode();
    }


}
