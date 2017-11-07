package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//@@author Eric
/**
 *  Appointment class to hold all the start and end time of the appointment and the description
 *  */
public class Appointment {

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private String description;
    private Date date;
    private Date endDate;

    public Appointment(String description, Calendar calendar, Calendar calendarEnd) {
        requireNonNull(calendar);
        requireNonNull(description);
        this.description = description;
        this.date = calendar.getTime();
        if (calendarEnd != null) {
            this.endDate = calendarEnd.getTime();
        } else {
            calendar.add(Calendar.HOUR, 1);
            this.endDate = calendar.getTime();
        }
    }

    public String getDescription() {
        return this.description;
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
            return "No appointment";
        }
    }

    /**
     * @return starting appointment time in the format yyyy/MM/dd HH:mm
     */
    public String getDateInStringFormat() {
        return DATE_FORMATTER.format(date);
    }

    /**
     * @return ending appointment time in the format yyyy/MM/dd HH:mm
     */
    public String getDateEndInStringFormat() {
        return DATE_FORMATTER.format(endDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.getDateInStringFormat().equals(((Appointment) other).getDateInStringFormat()))
                && this.getDateEndInStringFormat().equals(((Appointment) other).getDateEndInStringFormat());
    }
}
