package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;
import seedu.address.testutil.TypicalPersons;

public class AddAppointmentCommandTest {

    @Test
    public void equals() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/08/08 10:10"));
        AddAppointmentCommand command = new AddAppointmentCommand("asd", calendar);

        assertEquals(command, new AddAppointmentCommand("asd", calendar));
        assertNotEquals(command, new AddAppointmentCommand("das", calendar));
    }

    @Test
    public void execute() throws ParseException, CommandException {

        Calendar calendar = Calendar.getInstance();
        //Invalid date (i.e date before current instance)
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2010/08/08 10:10"));
        AddAppointmentCommand command = new AddAppointmentCommand("asd", calendar);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        CommandResult result = command.execute();

        //Invalid date message returned
        assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_DATE);

        //Set to valid date
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2019/08/08 10:10"));
        command = new AddAppointmentCommand("asd", calendar);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        result = command.execute();

        //No such person
        assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_PERSON);

        command = new AddAppointmentCommand(TypicalPersons.ALICE.getName().toString(), calendar);
        command.setData(model);
        result = command.execute();
        Appointment appointment = new Appointment(TypicalPersons.ALICE.getName().toString(), calendar);

        //Command success
        assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS + "Meet " + appointment.toString());

        //No appointment set
        command = new AddAppointmentCommand();
        command.setData(model);
        result = command.execute();
        assertEquals(result.feedbackToUser, "Rearranged contacts to show upcoming appointments.");

    }

}
