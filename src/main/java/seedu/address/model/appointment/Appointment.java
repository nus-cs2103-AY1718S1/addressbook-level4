//@@author namvd2709
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's appointment created in the address book.
 * Guarantees: immutable
 */
public class Appointment {
    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS =
            "Appointment must be in exact format dd/MM/yyyy hh:mm duration, the date must be older than today";
    public static final String DATETIME_PATTERN = "dd/MM/yyyy HH:mm";
    private static final String MESSAGE_DURATION_CONSTRAINT = "Duration must be a positive integer in minutes";
    private static final String MESSAGE_DATETIME_CONSTRAINT = "Date time cannot be in the past";

    public final String value;
    public final LocalDateTime start;
    public final LocalDateTime end;

    /**
     * Validates given appointment.
    */
    public Appointment(String appointment) throws IllegalValueException {
        requireNonNull(appointment);
        if (appointment.equals("")) {
            this.value = appointment;
            this.start = null;
            this.end = null;
        } else {
            try {
                String[] split = appointment.split("\\s+");
                String date = split[0];
                String time = split[1];
                String duration = split[2];
                if (!isValidDuration(duration)) {
                    throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINT);
                }
                LocalDateTime startDateTime = getDateTime(date + " " + time);
                LocalDateTime endDateTime = getEndDateTime(startDateTime, duration);
                if (!isAfterToday(startDateTime) || !isAfterToday(endDateTime)) {
                    throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINT);
                }
                this.value = appointment;
                this.start = startDateTime;
                this.end = endDateTime;
            } catch (ArrayIndexOutOfBoundsException iob) {
                throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    public static LocalDateTime getDateTime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        return LocalDateTime.parse(datetime, formatter);
    }

    /**
     * Returns true if a given string is a valid appointment.
     */
    public static boolean isAfterToday(LocalDateTime datetime) {
        LocalDateTime now = LocalDateTime.now();
        return datetime.isAfter(now);
    }

    /**
     * Returns true if given duration is an integer, using regex
     */
    public static boolean isValidDuration(String duration) {
        return duration.matches("^-?\\d+$")
                    && Integer.parseInt(duration) > 0;
    }

    /**
     * Assumes startDateTime and duration are valid
     * @param startDateTime dd/MM/yyyy format
     * @param duration must be integer
     * @return String end date and time of appointment
     */
    private static LocalDateTime getEndDateTime(LocalDateTime startDateTime, String duration) {
        return startDateTime.plusMinutes(Integer.parseInt(duration));
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.value.equals(((Appointment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
