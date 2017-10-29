package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;

//@@author Eric
public class AddAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void equals() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/08/08 10:10"));
        AddAppointmentCommand command = new AddAppointmentCommand(Index.fromOneBased(1), calendar);

        assertEquals(command, new AddAppointmentCommand(Index.fromOneBased(1), calendar));
        assertNotEquals(command, new AddAppointmentCommand(Index.fromOneBased(2), calendar));
    }

    @Test
    public void execute() throws ParseException, CommandException {

        Index index1 = Index.fromOneBased(1);
        Index index100 = Index.fromOneBased(100);

        Calendar calendar = Calendar.getInstance();
        //Invalid date (i.e date before current instance)
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2005/08/08 10:10"));
        AddAppointmentCommand command = new AddAppointmentCommand(index1, calendar);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        CommandResult result = command.execute();

        //Invalid date message returned
        assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_DATE);

        //Set to valid date
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2019/08/08 10:10"));

        command = new AddAppointmentCommand(index1, calendar);
        command.setData(model);
        result = command.execute();
        Appointment appointment = new Appointment(model.getFilteredPersonList().get(index1.getZeroBased())
                .getName().toString(),
                calendar);

        //Command success
        assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS + "Meet "
                + appointment.getPersonName().toString() + " on "
                + appointment.getDate().toString());

        //No appointment set
        command = new AddAppointmentCommand();
        command.setData(model);
        result = command.execute();
        assertEquals(result.feedbackToUser, "Rearranged contacts to show upcoming appointments.");

        //Out of bounds index
        command = new AddAppointmentCommand(index100, calendar);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);

        //Out of bounds index
        thrown.expect(CommandException.class);
        command.execute();
    }

    @Test
    public void appointmentsWithDurationTest() throws ParseException, CommandException {
        Index index1 = Index.fromOneBased(1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2105/08/08 10:10"));
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2106/08/08 10:10"));
        AddAppointmentCommand command = new AddAppointmentCommand(index1, calendar, calendar2);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        CommandResult result = command.execute();
        Appointment appointment = new Appointment(model.getFilteredPersonList().get(index1.getZeroBased())
                .getName().toString(),
                calendar);
        assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS + "Meet "
                + appointment.getPersonName().toString() + " on "
                + appointment.getDate().toString());

    }


}
