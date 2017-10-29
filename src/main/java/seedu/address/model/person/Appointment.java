package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//@@author Eric
/**
 *  Appointment class to hold all the appointment information of an appointment
 *  */
public class Appointment {

    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private String personString;
    private Date date;



    private Date endDate;

    public Appointment(String person) {
        this.personString = person;
    }
    public Appointment(String person, Calendar calendar) {
        requireNonNull(calendar);
        Date date = calendar.getTime();
        this.personString = person;
        this.date = date;
    }

    public Appointment(String person, Calendar calendar, Calendar calendarEnd) {
        requireNonNull(calendar);
        Date date = calendar.getTime();
        this.personString = person;
        this.date = date;
        this.endDate = calendarEnd.getTime();
    }

    public String getPersonName() {
        return this.personString;
    }

    public Date getDate() {
        return this.date;
    }

    public Date getEndDate() {
        return endDate;
    }
    @Override
    public String toString() {
        if (date != null) {
            return "Appointment on " + DATE_FORMATTER.format(date);
        } else {
            return "No appointment set with " + personString;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.personString.equals(((Appointment) other).personString)
                && this.getDate().toInstant().equals(((Appointment) other).getDate().toInstant()));
    }
}
