package seedu.address.model.person;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

//@@author Eric
public class AppointmentTest {

    @Test
    public void equals() {

        Calendar calendar = Calendar.getInstance();
        String apptString = "Test";
        Appointment appt = new Appointment(apptString, calendar, calendar);
        Appointment appt2 = new Appointment(apptString, calendar, calendar);

        assertEquals(appt, appt2);
    }

    @Test
    public void toStringTest() {
        Calendar calendar = Calendar.getInstance();
        String apptString = "Test";
        Appointment appt = new Appointment(apptString, calendar, calendar);

        assertEquals(appt.toString(), "Appointment on " + appt.getDateInStringFormat());
    }
}
