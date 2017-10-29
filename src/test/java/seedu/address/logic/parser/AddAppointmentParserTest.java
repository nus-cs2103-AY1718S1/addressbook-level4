package seedu.address.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;
import seedu.address.testutil.TypicalPersons;

//@@author Eric
public class AddAppointmentParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddAppointmentParser parser = new AddAppointmentParser();

    @Test
    public void prefixesNotPresent() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("Alice 2018/02/10 10:10");
    }

    @Test
    public void illegalExpression() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("n/@@@@ d/2018/02/10 10:10");
    }

    @Test
    public void nonParsableString() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("appt 1 d/cant parse this string");
    }
    @Test
    public void parseDateExpression() throws ParseException, java.text.ParseException {

        AddAppointmentCommand command = parser.parse("appt 1 d/The 30th of April in the year 2018 12am");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/04/30 00:00"));
        assertEquals(new AddAppointmentCommand(Index.fromOneBased(1), calendar), command);

    }

    @Test
    public void parseEmptyExpression() {

        //No name and no date will just call the parser to return a command with no attributes initialized
        try {
            AddAppointmentCommand command = parser.parse("appointment");
            assertTrue(command.getIndex() == null);
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void parseOffAppointment() {
        try {
            AddAppointmentCommand command = parser.parse("appointment 1 d/off");
            assertTrue(command.getIndex().getOneBased() == 1);
            command.setData(new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs()));
            CommandResult result = command.execute();
            assertEquals(("Appointment with "
                    + TypicalPersons.getTypicalAddressBook().getPersonList().get(0).getName().toString()
                    + " set to off."), result.feedbackToUser);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void parseAppointmentsWithDuration() {

        try {
            AddAppointmentCommand command = parser.parse("appt 1 d/7am 5th april 2018 to 10am 5th april 2018");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/04/05 07:00"));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(Appointment.DATE_FORMATTER.parse("2018/04/05 10:00"));
            assertEquals(new AddAppointmentCommand(Index.fromOneBased(1), calendar, calendar2), command);
        } catch (java.text.ParseException | ParseException e) {
            e.printStackTrace();
        }
    }
}
