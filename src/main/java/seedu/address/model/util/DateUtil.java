package seedu.address.model.util;

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
     * checks if date of format DD-MM-YYYY is valid.
     * @param dateToValidate the date to validate
     * @return boolean to determine if date is valid
     */
    public static boolean isValidDateFormat(String dateToValidate) {
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
    private static boolean checkLeapYear(int year) {
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
}
