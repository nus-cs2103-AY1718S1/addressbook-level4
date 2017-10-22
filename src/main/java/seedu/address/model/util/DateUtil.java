package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Contains utility methods for formatting dates fields in {@code Person}.
 */
public class DateUtil {

    public static final String DATE_FORMAT = "E',' dd MMM', Year' yyyy";
    // for input format of DD-MM-YYYY
    public static final String DATE_VALIDATION_REGEX = "([0-3][0-9](-))([0-1][0-9](-))(\\d{4})";
    public static final int JAN = 1;
    public static final int FEB = 2;
    public static final int MAR = 3;
    public static final int APR = 4;
    public static final int MAY = 5;
    public static final int JUN = 6;
    public static final int JUL = 7;
    public static final int AUG = 8;
    public static final int SEP = 9;
    public static final int OCT = 10;
    public static final int NOV = 11;
    public static final int DEC = 12;

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
            case APR:
            case JUN:
            case SEP:
            case NOV:
                if (day > 30) {
                    valid = false;
                } else {
                    valid = true;
                }
                break;
            case FEB:
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

    /**
     * generate a date that is 1 month behind current date
     */
    public static Date generateOutdatedDebtDate(Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * Get the difference in number of months between the 2 dates.
     * @param date1 is assumed to be AFTER date2.
     * @return return difference. If there is no difference, return 0.
     */
    public static int getNumberOfMonthBetweenDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        // if date1 is only 1 year ahead of date2
        if (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) == 1) {
            return cal1.get(Calendar.MONTH) + 1
                    + (11 - cal2.get(Calendar.MONTH));
        } else if (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) > 1) {
            return cal1.get(Calendar.MONTH) + 1
                    + (11 - cal2.get(Calendar.MONTH))
                    + (12 * (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) - 1));
        } else {
            return cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
        }
    }

    public static int getMonthFromString(String month) {
        int monthToReturn;
        switch(month) {
        case "Jan":
            monthToReturn = JAN;
            break;
        case "Feb":
            monthToReturn = FEB;
            break;
        case "Mar":
            monthToReturn = MAR;
            break;
        case "Apr":
            monthToReturn = APR;
            break;
        case "May":
            monthToReturn = MAY;
            break;
        case "Jun":
            monthToReturn = JUN;
            break;
        case "Jul":
            monthToReturn = JUL;
            break;
        case "Aug":
            monthToReturn = AUG;
            break;
        case "Sep":
            monthToReturn = SEP;
            break;
        case "Oct":
            monthToReturn = OCT;
            break;
        case "Nov":
            monthToReturn = NOV;
            break;
        case "Dec":
            monthToReturn = DEC;
            break;
        default:
            monthToReturn = 0;
            break;
        }
        return monthToReturn;
    }
}
