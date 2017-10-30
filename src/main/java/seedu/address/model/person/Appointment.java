package seedu.address.model.person;

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
    private static final String MESSAGE_DURATION_CONSTRAINT = "Duration must be an integer";
    private static final String MESSAGE_DATETIME_CONSTRAINT = "Date time must be valid format, dd/MM/yyyy HH:mm";

    public final String value;

    /**
     * Validates given appointment.
    */
    public Appointment(String appointment) throws IllegalValueException {
        requireNonNull(appointment);
        if (appointment.equals("")) {
            this.value = appointment;
        } else {
            try {
                String[] split = appointment.split("\\s+");
                String date = split[0];
                String time = split[1];
                String duration = split[2];
                String startDateTime = date + " " + time;
                String endDateTime = getEndDateTime(startDateTime, duration);
                if (!isValidDuration(duration)) {
                    throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINT);
                }
                if (!isValidDateTime(startDateTime) || !isValidDateTime(endDateTime)) {
                    throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINT);
                }
                this.value = appointment;
            } catch (ArrayIndexOutOfBoundsException iob) {
                throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
            } catch (IllegalValueException ive) {
                throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid appointment.
     */
    public static boolean isValidDateTime(String datetime) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        LocalDateTime appointmentDateTime = LocalDateTime.parse(datetime, formatter);
        return appointmentDateTime.isAfter(date);
    }

    /**
     * Returns true if given duration is an integer
     */
    public static boolean isValidDuration(String duration) throws IllegalValueException {
        try {
            Integer.parseInt(duration);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_DURATION_CONSTRAINT);
        }
        return true;
    }

    /**
     * Assumes startDateTime and duration are valid
     * @param startDateTime dd/MM/yyyy format
     * @param duration must be integer
     * @return String end date and time of appointment
     */
    private static String getEndDateTime(String startDateTime, String duration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
        LocalDateTime end = start.plusMinutes(Integer.parseInt(duration));
        String endDateTime = end.format(formatter);
        return endDateTime;
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
