package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  Appointment class to hold all the appointment information of an appointment
 *  */
public class Appointment {

    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private String personString;
    private Date date;


    public Appointment(String person) {
        this.personString = person;
    }
    public Appointment(String person, Calendar calendar) {
        requireNonNull(calendar);
        Date date = calendar.getTime();
        this.personString = person;
        this.date = date;
    }

    public String getPersonName() {
        return this.personString;
    }

    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        if (date != null) {
            return "Appointment with " + this.personString + " on " + DATE_FORMATTER.format(date);
        } else {
            return "No appointment set with " + personString;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bloodtype // instanceof handles nulls
                && this.personString.equals(((Appointment) other).personString))
                && this.date.toString().equals(((Appointment) other).date.toString()); // state check
    }
}
