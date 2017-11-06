package seedu.address.model.person;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

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
