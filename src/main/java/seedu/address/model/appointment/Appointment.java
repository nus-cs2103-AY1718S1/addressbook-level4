//@@author namvd2709
package seedu.address.model.appointment;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a Person's appointment created in the address book.
 * Guarantees: immutable
 */
public class Appointment {
    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS =
            "Appointment must be in exact format dd/MM/yyyy hh:mm duration, the date must be older than today";
    public static final String DATETIME_PATTERN = "dd/MM/uuuu HH:mm";
    public static final String MESSAGE_DURATION_CONSTRAINT = "Duration must be a positive integer in minutes";
    public static final String MESSAGE_DATETIME_CONSTRAINT = "Date time cannot be in the past";
    public static final String MESSAGE_INVALID_DATETIME = "Date or time is invalid";

    public final String value;
    public final LocalDateTime start;
    public final LocalDateTime end;
    private ReadOnlyPerson person;

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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
                this.value = startDateTime.format(formatter) + " to " + endDateTime.format(formatter);
                this.start = startDateTime;
                this.end = endDateTime;
            } catch (ArrayIndexOutOfBoundsException iob) {
                throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
            } catch (DateTimeParseException dtpe) {
                throw new IllegalValueException(MESSAGE_INVALID_DATETIME);
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    public static LocalDateTime getDateTime(String datetime) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN)
                .withResolverStyle(ResolverStyle.STRICT);
        return LocalDateTime.parse(datetime, formatter);
    }

    public void setPerson(ReadOnlyPerson person) {
        this.person = person;
    }

    public ReadOnlyPerson getPerson() {
        return person;
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

    /**
     * Method to help get back original appointment, for passing back to constructor
     */
    public static String getOriginalAppointment(String formattedAppointment) {
        if (!formattedAppointment.equals("")) {
            int splitter = formattedAppointment.indexOf("to");
            String start = formattedAppointment.substring(0, splitter - 1);
            String end = formattedAppointment.substring(splitter + 3);
            LocalDateTime startDateTime = Appointment.getDateTime(start);
            LocalDateTime endDateTime = Appointment.getDateTime(end);
            long duration = startDateTime.until(endDateTime, MINUTES);
            return start + " " + String.valueOf(duration);
        }
        return formattedAppointment;
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
