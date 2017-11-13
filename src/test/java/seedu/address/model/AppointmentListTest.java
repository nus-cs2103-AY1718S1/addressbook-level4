package seedu.address.model;

import static junit.framework.TestCase.assertFalse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.parser.AddAppointmentParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentList;



//@@author Eric
public class AppointmentListTest {

    @Test
    public void equals() throws ParseException {

        AppointmentList emptyList = new AppointmentList();
        AppointmentList emptyList2 = new AppointmentList();

        assertEquals(emptyList, emptyList2);
        try {
            String apptString = "Lunch, tomorrow 5pm to 7pm";
            List<Appointment> appts = new ArrayList<>();
            appts.add(getAppointmentFromString(apptString));

            AppointmentList appointmentList = new AppointmentList(appts);
            AppointmentList appointmentList1 = new AppointmentList(appts);

            assertEquals(appointmentList, appointmentList1);
        } catch (ParseException e) {
            throw new ParseException("wrong appointment string");
        }
    }

    @Test
    public void contains() throws ParseException {

        try {
            String apptString = "Lunch, tomorrow 5pm to 7pm";
            Appointment appt = getAppointmentFromString(apptString);
            List<Appointment> appts = new ArrayList<>();
            appts.add(appt);

            AppointmentList appointmentList = new AppointmentList(appts);
            assertTrue(appointmentList.contains(appt));

            String apptStringNotInList = "Not in List, tomorrow 5pm";
            appt = getAppointmentFromString(apptStringNotInList);

            assertFalse(appointmentList.contains(appt));

        } catch (ParseException e) {
            throw new ParseException("wrong appointment string");
        }
    }

    @Test
    public void addAppointmentsInChronologicalOrder() throws ParseException {
        try {
            List<Appointment> appts = new ArrayList<>();

            appts.add(getAppointmentFromString("Lunch, tomorrow 2pm to 3pm"));
            appts.add(getAppointmentFromString("Lunch, tomorrow 12pm to 2pm"));
            appts.add(getAppointmentFromString("Lunch, tomorrow 1pm to 2pm"));

            //empty list should be sorted
            AppointmentList appointmentList = new AppointmentList();
            assertTrue(sorted(appointmentList));

            //Should be sorted
            appointmentList = new AppointmentList(appts);
            assertTrue(sorted(appointmentList));

        } catch (ParseException e) {
            throw new ParseException("wrong appointment string");
        }
    }

    /**
     * Util method to help check if the appts are sorted
     */
    private boolean sorted(AppointmentList appts) {
        List<Appointment> apptList = appts.toList();
        for (int i = 0; i < apptList.size() - 1; i++) {
            if (apptList.get(i + 1).getDate().before(apptList.get(i).getDate())) {
                return false;
            }
        }
        return true;
    }


    private Appointment getAppointmentFromString(String str) throws ParseException {
        return AddAppointmentParser.getAppointmentFromString(str);
    }
}
