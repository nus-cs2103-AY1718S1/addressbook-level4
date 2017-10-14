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

    public static final String dateFormat = "yyyy/MM/dd HH:mm";
    public static final DateFormat dateFormatter = new SimpleDateFormat(dateFormat);

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
            return this.personString + " at " + dateFormatter.format(date);
        } else {
            return "No appointment set with" + personString;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bloodtype // instanceof handles nulls
                && this.personString.equals(((Appointment) other).personString))
                && this.date.equals(((Appointment) other).date); // state check
    }
}
