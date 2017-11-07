package seedu.room.model.event;

import static java.util.Objects.requireNonNull;

import seedu.room.commons.exceptions.IllegalValueException;

import java.time.DateTimeException;
import java.time.LocalDate;

//@@author sushinoya
/**
 * Represents a Event's Datetime in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDatetime(String)}
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event datetime should contain dd/mm/yyyy hhmm DURATION (in hours)";

    public static final String DATE_CONSTRAINTS_VIOLATION =
            "The date does not exist";

    public static final String NUMBER_CONSTRAINTS_VIOLATION =
            "Date, time and duration can only be represented using digits";

    public final String value;

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
            this.value = datetime +" hours";
        } catch (DateTimeException e) {
            throw new IllegalValueException(DATE_CONSTRAINTS_VIOLATION);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(NUMBER_CONSTRAINTS_VIOLATION);
        }
    }


    public static boolean isValidDatetime(String test) {
        String[] components = test.split(" ");

        if (components.length != 3) {
            return false;
        }
        return isValidDate(components[0]) && isValidTime(components[1]) && isValidDuration(components[2]);
    }


    /**
     * Returns true if a given string is a valid date in the format dd/mm/yyyy.
     * @throws NumberFormatException if string contains non-digit characters.
     * @throws DateTimeException if the date represented by the string is invalid
     */
    public static boolean isValidDate(String date) throws DateTimeException, NumberFormatException {
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

        int minute = 10 * Integer.parseInt(Character.toString(timeArray[2])) +
                          Integer.parseInt(Character.toString(timeArray[3]));

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