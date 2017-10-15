package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;
import seedu.address.testutil.TypicalPersons;

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

        assertEquals(command, new AddAppointmentCommand(new Appointment("asd", calendar)));
        assertNotEquals(command, new AddAppointmentCommand(new Appointment("das", calendar)));
    }

    @Test
    public void execute() throws ParseException, CommandException {

        Calendar calendar = Calendar.getInstance();
        //Invalid date (i.e date before current instance)
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2010/08/08 10:10"));
        Appointment appointment = new Appointment("asd", calendar);
        AddAppointmentCommand command = new AddAppointmentCommand(appointment);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        CommandResult result = command.execute();

        //Invalid date message returned
        assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_DATE);

        //Set to valid date
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2019/08/08 10:10"));
        appointment = new Appointment("asd", calendar);
        command = new AddAppointmentCommand(appointment);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        result = command.execute();

        //No such person
        assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_PERSON);

        appointment = new Appointment(TypicalPersons.ALICE.getName().toString(), calendar);
        command = new AddAppointmentCommand(appointment);
        command.setData(model);
        result = command.execute();

        //Command success
        assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS + "Meet " + appointment.toString());
    }

}
