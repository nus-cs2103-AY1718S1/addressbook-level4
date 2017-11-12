package seedu.room.model.event;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.room.commons.exceptions.IllegalValueException;

//@@author sushinoya
/**
 * Represents a Event's Datetime in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDatetime(String)}
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event datetime should contain dd/mm/yyyy hhmm to hhmm";

    public static final String DATE_CONSTRAINTS_VIOLATION =
            "The date does not exist";

    public static final String NUMBER_CONSTRAINTS_VIOLATION =
            "Date, time and duration can only be represented using digits";

    public static final String INVALID_TIME_FORMAT =
            "Time should be represented only by digits and in the format hhmm";

    public static final String MULTIPLE_DAY_EVENT_ERROR =
            "The event should end on the same day";

    public final String value;
    public final LocalDateTime datetime;

    /**
     * Validates given datetime.
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public Datetime(String datetime) throws IllegalValueException {
        requireNonNull(datetime);
        try {
            if (!isValidDatetime(datetime)) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }

            if (!isSingleDayEvent(datetime)) {
                throw new IllegalValueException(MULTIPLE_DAY_EVENT_ERROR);
            }

            //Update datetime
            String[] components = datetime.split(" ");
            String date = components[0];
            int starttime = Integer.parseInt(components[1]);;
            int duration;
            int endtime;

            //If the format is dd/mm/yyyy hhmm k
            if (components.length == 3) {
                duration = Integer.parseInt(components[2]);
                endtime = (starttime + 100 * duration) % 2400;

            //If the format is dd/mm/yyyy hhmm to hhmm
            } else if (components.length == 4) {
                endtime = Integer.parseInt(components[3]);
            } else {
                endtime = 0;
            }

            String endtimeString = this.toValidTimeString(endtime);
            String starttimeString = this.toValidTimeString(starttime);

            //Store as a LocalDateTime object
            this.datetime = this.toLocalDateTime(components[0] + " " + starttimeString);
            this.value = date + " " + starttimeString + " to " + endtimeString;

        } catch (DateTimeException e) {
            throw new IllegalValueException(DATE_CONSTRAINTS_VIOLATION);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(NUMBER_CONSTRAINTS_VIOLATION);
        }
    }


    /**
     * Returns a LocalDateTime object for the Datetime object
     */
    public LocalDateTime getLocalDateTime() {
        return this.datetime;
    }

    /**
     * Returns true if a given string is a valid datetime in the format dd/mm/yyyy hhmm k.
     */
    public static boolean isValidDatetime(String test) {
        String[] components = test.split(" ");
        String date = components[0];
        String startTime = components[1];

        //If the format is dd/mm/yyyy hhmm k
        if (components.length == 3) {
            String duration = components[2];
            return isValidDate(date) && isValidTime(startTime) && isValidDuration(duration);
        //If the format is dd/mm/yyyy hhmm to hhmm
        } else if (components.length == 4) {
            String endtime = components[3];
            return isValidDate(date) && isValidTime(startTime) && isValidTime(endtime);
        } else {
            return false;
        }
    }

    /**
     * Returns true if the event ends on the same day and false if it overflows to the next day
     */
    public static boolean isSingleDayEvent(String test) {
        String[] components = test.split(" ");
        String startTime = components[1];

        //If the format is dd/mm/yyyy hhmm k
        if (components.length == 3) {
            String duration = components[2];
            return endOnTheSameDay (startTime, duration);
        //If the format is dd/mm/yyyy hhmm to hhmm
        } else if (components.length == 4) {
            String endtime = components[3];
            return endTimeAfterStart(startTime, endtime);
        } else {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid date in the format dd/mm/yyyy.
     * @throws NumberFormatException if string contains non-digit characters.
     * @throws DateTimeException if the date represented by the string is invalid
     */
    public static boolean isValidDate(String date) throws DateTimeException, NumberFormatException {

        if (date.length() != 10) {
            return false;
        }

        String[] dateComponents = date.split("/");

        if (dateComponents.length != 3) {
            return false;
        }

        int day = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int year = Integer.parseInt(dateComponents[2]);

        LocalDate localdate;
        localdate = LocalDate.of(year, month, day);
        return true;
    }

    /**
     * Returns true if a given string is a valid time in the format hhmm.
     * @throws NumberFormatException if string contains non-digit characters.
     */
    public static boolean isValidTime(String time) throws NumberFormatException {

        int timeInt = Integer.parseInt(time); //throws NumberFormatException if "time" contains non-digit characters

        if (time.length() != 4) {
            return false;
        }

        char[] timeArray = time.toCharArray();
        int hour = Integer.parseInt(new String(timeArray, 0, 2));

        int minute = 10 * Integer.parseInt(Character.toString(timeArray[2]))
                        + Integer.parseInt(Character.toString(timeArray[3]));

        return hour >= 0 && hour < 24 && minute >= 0 && minute < 60;

    }

    /**
     * Returns true if a given string is a valid duration.
     * @throws NumberFormatException if string contains non-digit characters
     */
    public static boolean isValidDuration(String duratonString) throws NumberFormatException {
        double duration =  Double.parseDouble(duratonString);
        return duration > 0 && duration < 24;
    }

    /**
     * Returns a LocalDateTime object for the input in the format dd/MM/yyyy HHmm
     * @throws DateTimeException if the datetime represented by the string is invalid
     */
    public LocalDateTime toLocalDateTime(String value) throws DateTimeException {
        String[] valueComponents = value.split(" ");
        String dateWithStartTime = valueComponents[0] + " " + valueComponents[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        return LocalDateTime.parse(dateWithStartTime, formatter);
    }

    /**
     * Returns a time string in the format HHmm for an integer input in the format hmm or hhmm
     * @throws IllegalValueException if the time represented by the integer is invalid
     */
    public String toValidTimeString(int time) throws IllegalValueException {
        String updatedString = String.valueOf(time);
        if (time < 1000) {
            while (updatedString.length() < 4) {
                updatedString = "0" + updatedString;
            }
        } else {
            updatedString = String.valueOf(time);
        }
        if (this.isValidTime(updatedString)) {
            return updatedString;
        } else {
            throw new IllegalValueException(INVALID_TIME_FORMAT);
        }
    }

    /**
     * Returns true if the event ends on the same day and false if it overflows to the next day
     */
    public static Boolean endOnTheSameDay(String startTimeString, String durationString) {
        assert(isValidTime(startTimeString));
        assert(isValidDuration(durationString));

        int startTime = Integer.parseInt(startTimeString);
        int duration = Integer.parseInt(durationString);
        int endTime = startTime + 100 * duration;
        if (endTime > 2359) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns true if the event ends on the same day and false if it overflows to the next day
     */
    public static Boolean endTimeAfterStart(String startTimeString, String endtimeString) {
        assert(isValidTime(startTimeString));
        assert(isValidTime(endtimeString));

        int startTime = Integer.parseInt(startTimeString);
        int endTime = Integer.parseInt(endtimeString);
        if (startTime > endTime) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Datetime // instanceof handles nulls
                && this.value.equals(((Datetime) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
