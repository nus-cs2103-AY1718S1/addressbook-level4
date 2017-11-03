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
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Calendar calendar = Calendar.getInstance();
        //Invalid date (i.e date before current instance)
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2005/08/08 10:10"));
        Command command = setCommand(index1, calendar);
        CommandResult result = command.execute();

        //Invalid date message returned
        assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_DATE);

        //Set to valid date
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2019/08/08 10:10"));
        command = setCommand(index1, calendar);
        result = command.execute();

        Appointment appointment = new Appointment(model.getFilteredPersonList().get(index1.getZeroBased())
                .getName().toString(),
                calendar);

        //Command success
        assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS + "Meet "
                + appointment.getPersonName() + " on "
                + appointment.getDate().toString());

        //No appointment set
        command = setCommand(null, null);
        result = command.execute();
        assertEquals(result.feedbackToUser, "Rearranged contacts to show upcoming appointments.");


    }

    @Test
    public void outOfBoundsIndex() throws CommandException {
        thrown.expect(CommandException.class);
        setCommand(Index.fromOneBased(100), null).execute();
    }

    /**
     * Util method to set appointment command
     */
    private Command setCommand(Index index, Calendar calendar) {

        AddAppointmentCommand command = new AddAppointmentCommand(index, calendar);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        return command;
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
