package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Contains utility methods for formatting dates fields in {@code Person}.
 */
public class DateUtil {

    public static final String DATE_FORMAT = "E',' dd MMM', Year' yyyy";
    // for input format of DD-MM-YYYY
    public static final String DATE_VALIDATION_REGEX = "([0-3][0-9](-))([0-1][0-9](-))(\\d{4})";

    /**
     * Formats a Date class to a string value of format DAY, DD MM, 'Year' YYYY.
     * @param date the date to format
     * @return formatted date value
     */
    public static String formatDate(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
        return ft.format(date);
    }
    /**
     * Formats String date of format DD-MM-YYYY to DAY, DD MM, 'Year' YYYY.
     * @param dateToFormat the date to format
     * @return formatted date value
     */
    public static String formatDate(String dateToFormat) {
        SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
        int year = Integer.parseInt(dateToFormat.substring(6, 10));
        int day = Integer.parseInt(dateToFormat.substring(0, 2));
        int month = Integer.parseInt(dateToFormat.substring(3, 5)) - 1; // GregorianCalendar uses 0-based for month
        Date date = new GregorianCalendar(year, month, day).getTime();
        return ft.format(date);
    }

    /**
     * Converts a string of format DAY, DD MM, 'Year' YYYY to a Date class
     */
    public static Date convertStringToDate(String date) {
        int day = Integer.parseInt(date.substring(5, 7));
        int month = getMonthFromString(date.substring(8, 11)) - 1;
        int year = Integer.parseInt(date.substring(18, 22));
        Date dateToReturn = new GregorianCalendar(year, month, day).getTime();
        return dateToReturn;
    }

    /**
     * checks if date of format DD-MM-YYYY is valid.
     * @param dateToValidate the date to validate
     * @return boolean to determine if date is valid
     */
    public static boolean isValidDateFormat(String dateToValidate) {
        requireNonNull(dateToValidate);
        if (!dateToValidate.matches(DATE_VALIDATION_REGEX)) {
            return false;
        } else {
            int month = Integer.parseInt(dateToValidate.substring(3, 5));
            int day = Integer.parseInt(dateToValidate.substring(0, 2));
            int year = Integer.parseInt(dateToValidate.substring(6, 10));
            boolean valid;
            switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                if (day > 30) {
                    valid = false;
                } else {
                    valid = true;
                }
                break;
            case 2:
                if (checkLeapYear(year)) {
                    if (day <= 29) {
                        valid = true;
                    } else {
                        valid = false;
                    }
                } else {
                    if (day <= 28) {
                        valid = true;
                    } else {
                        valid = false;
                    }
                }
                break;
            default:
                if (day > 31) {
                    valid = false;
                } else {
                    valid = true;
                }
                break;
            }
            return valid;
        }
    }


    /**
     * checks if date is a leap year
     * @param year the year to check if it is a leap year
     * @return boolean to determine if date is a leap year
     */
    public static boolean checkLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else {
            if ((year % 100 == 0) && (year % 400 != 0)) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Compares the 2 dates. If date1 is before date2, a boolean value of true will be returned.
     * Vice versa if date2 is before date1.
     * If date1 is on the same day as date2, the return value would still be true.
     */
    public static boolean compareDates(Date date1, Date date2) {
        if (date2.before(date1)) {
            return false;
        } else {
            return true;
        }
    }

    public static int getMonthFromString(String month) {
        int monthToReturn;
        switch(month) {
        case "Jan":
            monthToReturn = 1;
            break;
        case "Feb":
            monthToReturn = 2;
            break;
        case "Mar":
            monthToReturn = 3;
            break;
        case "Apr":
            monthToReturn = 4;
            break;
        case "May":
            monthToReturn = 5;
            break;
        case "Jun":
            monthToReturn = 6;
            break;
        case "Jul":
            monthToReturn = 7;
            break;
        case "Aug":
            monthToReturn = 8;
            break;
        case "Sep":
            monthToReturn = 9;
            break;
        case "Oct":
            monthToReturn = 10;
            break;
        case "Nov":
            monthToReturn = 11;
            break;
        case "Dec":
            monthToReturn = 12;
            break;
        default:
            monthToReturn = 0;
            break;
        }
        return monthToReturn;
    }
}
