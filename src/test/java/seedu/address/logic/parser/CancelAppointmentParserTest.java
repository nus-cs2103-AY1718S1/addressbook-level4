package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CancelAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author Eric
public class CancelAppointmentParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseException() throws ParseException {
        String str = "this cant be parsed";
        CancelAppointmentParser parser = new CancelAppointmentParser();
        thrown.expect(ParseException.class);
        parser.parse(str);
    }

    @Test
    public void parseWithoutKeyWordWith() throws ParseException {
        String str = "Lunch Alice Pauline";
        CancelAppointmentParser parser = new CancelAppointmentParser();
        thrown.expect(ParseException.class);
        parser.parse(str);
    }

    @Test
    public void parseSuccess() throws ParseException {
        String str = "Lunch with Alice Pauline";
        CancelAppointmentParser parser = new CancelAppointmentParser();
        CancelAppointmentCommand command = (CancelAppointmentCommand) parser.parse(str);
        CancelAppointmentCommand command2 = new CancelAppointmentCommand("Alice Pauline", "Lunch");

        assertEquals(command, command2);

    }
}
