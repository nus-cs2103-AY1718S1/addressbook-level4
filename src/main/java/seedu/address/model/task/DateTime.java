//@@author ShaocongDong
package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Generic dateTime Class specially catered for our task (start time and end time)
 * we apply abstraction design pattern here and we check differently from how current public package does
 * Current format is very rigid, changing it to be more flexible will be a future enhancement
 */
public class DateTime {
    public static final String MESSAGE_DATE_TIME_FORMAT_CONSTRAINTS =
            "The date time input should follow this format: dd-mm-YYYY "
                    + "hh:mm[am/pm] day-month-year hour(12):minute am/pm";

    public static final String MESSAGE_DATE_TIME_VALUE_CONSTRAINTS =
            "The format is correct but the values are not: dd-mm-YYYY "
                    + "hh:mm[am/pm] day-month-year hour(12):minute am/pm";

    /**
     * The data time input in our app currently have following rigit format
     * dd-mm-YYYY hh:mm[am/pm] day-month-year hour:minute am/pm
     */
    public static final String DATE_TIME_VALIDATION_REGEX =
            "\\d{2}-\\d{2}-\\d{4}\\s{1}\\d{2}:\\d{2}pm|\\d{2}-\\d{2}-\\d{4}\\s{1}\\d{2}:\\d{2}am";

    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private String state;

    /**
     * Constructor and checker for the date time object
     *
     * @param dateTime
     * @throws IllegalValueException
     */
    public DateTime(String dateTime) throws IllegalValueException {
        //check the format:
        if (!isValidDateTime(dateTime)) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_FORMAT_CONSTRAINTS);
        }

        //Now we can safely proceed to decompose the inputs
        day = Integer.parseInt(dateTime.substring(0, 2));
        month = Integer.parseInt(dateTime.substring(3, 5));
        year = Integer.parseInt(dateTime.substring(6, 10));
        hour = Integer.parseInt(dateTime.substring(11, 13));
        minute = Integer.parseInt(dateTime.substring(14, 16));
        state = dateTime.substring(16);

        // value checking helper
        boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));

        //check the values:
        if (month < 0 || month > 12) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        } else if (day < 0) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if (day > 31) {
                    throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
                }
            } else if (month == 2) {
                if ((isLeapYear && day > 29) || (!isLeapYear && day > 28)) {
                    throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
                }
            } else {
                if (day > 30) {
                    throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
                }
            }
        }

        if (hour < 0 || hour > 12) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        }

        if (minute < 0 || minute > 60) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        }

        if (!(state.equals("am") || state.equals("pm"))) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        }
        // At this point, the values and formats are both correct.
    }

    public static boolean isValidDateTime(String test) {
        return test.matches(DATE_TIME_VALIDATION_REGEX);
    }

    /**
     * Convert our date time object to String
     *
     * @return
     */
    public String toString() {
        String dayString = helperFormat(Integer.toString(day));
        String monthString = helperFormat(Integer.toString(month));
        String yearString = Integer.toString(year);
        String hourString = helperFormat(Integer.toString(hour));
        String minuteString = helperFormat(Integer.toString(minute));
        return dayString + "-" + monthString + "-" + yearString + " " + hourString + ":" + minuteString + state;
    }

    /**
     * Hashcode getter for our date time object
     *
     * @return the string representation's hash code
     */
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Helper for making toString format correct
     *
     * @param input
     * @return
     */
    private String helperFormat(String input) {
        if (input.length() < 2) {
            return "0" + input;
        }
        return input;
    }

    public int getDay () {
        return day;
    }

    public int getMonth () {
        return month;
    }

    public int getYear () {
        return year;
    }

    public int getHour () {
        return hour;
    }

    public int getMinute () {
        return minute;
    }

    public String getState () {
        return state;
    }

    /**
     * comparing two date time object part by part
     * @param others , a dateTime object
     * @return 1 if the argument DateTime is bigger
     */
    public int compareTo(DateTime others) {
        int othersDay = others.getDay();
        int othersMonth = others.getMonth();
        int othersYear = others.getYear();
        int othersHour = others.getHour();
        int othersMinute = others.getMinute();
        String othersState = others.getState();
        if (year < othersYear) {
            return 1;
        } else if (year > othersYear) {
            return -1;
        } else {
            if (month < othersMonth) {
                return 1;
            } else if (month > othersMonth) {
                return -1;
            } else {
                if (day < othersDay) {
                    return 1;
                } else if (day > othersDay) {
                    return -1;
                } else {
                    // at this point, the two has exactly the same day
                    if (state.equals("am") && othersState.equals("pm")) {
                        return 1;
                    } else if (state.equals("pm") && othersState.equals("am")) {
                        return -1;
                    } else {
                        // same state, now we compare the time
                        if (hour < othersHour) {
                            return 1;
                        } else if (hour > othersHour) {
                            return -1;
                        } else {
                            if (minute < othersMinute) {
                                return 1;
                            } else if (minute > othersMinute) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Compare two DateTime object
     * @param other , another DateTime object
     * @return true if they are of the same object or of the same value
     */
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && (this.compareTo((DateTime) other) == 0));
    }


}
