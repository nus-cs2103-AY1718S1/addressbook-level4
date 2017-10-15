package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;

public class AddAppointmentCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/08/08 10:10"));
        Appointment appointment = new Appointment("asd", calendar);
        AddAppointmentCommand command = new AddAppointmentCommand(appointment);

    }

}
