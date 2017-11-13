package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author limcel
public class ScheduleCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void noPrefixTest() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("1 tomorrow 10am");
    }
    @Test
    public void notEnoughArgumentsTest() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("s/next summer 12pm");
    }
    @Test
    public void noArgumentsTest() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("");
    }
    @Test
    public void parse_validArgs_returnsScheduleCommand() throws ParseException, java.text.ParseException {
        ScheduleCommand scheduleToAdd = parser.parse("1 s/25 December 2018 10am");
        Calendar date = Calendar.getInstance();
        date.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-25 10:00:00"));
        assertEquals(new ScheduleCommand(Index.fromOneBased(1), date), scheduleToAdd);
    }

    @Test
    public void parse_invalidArgs_returnsScheduleCommand() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("1 s/this date cannot be read");
    }
}
