package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author freesoup
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty args
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //only FIELD
        assertParseFailure(parser, "name", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Only ORDER
        assertParseFailure(parser, "asc", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() throws ParseException {
        // name ascending
        SortCommand expectedCommand = new SortCommand(ReadOnlyPerson.NAMESORTASC);
        assertTrue(parser.parse("name asc") instanceof SortCommand);
        assertParseSuccess(parser, "name asc", expectedCommand);

        // phone descending
        SortCommand expectedCommand2 = new SortCommand(ReadOnlyPerson.PHONESORTDSC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "phone dsc", expectedCommand2);

        // name descending
        SortCommand expectedCommand3 = new SortCommand(ReadOnlyPerson.NAMESORTDSC);
        assertTrue(parser.parse("name dsc") instanceof SortCommand);
        assertParseSuccess(parser, "name dsc", expectedCommand3);

        // email descending
        SortCommand expectedCommand4 = new SortCommand(ReadOnlyPerson.EMAILSORTDSC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "email dsc", expectedCommand4);

        // phone ascending
        SortCommand expectedCommand5 = new SortCommand(ReadOnlyPerson.PHONESORTASC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "phone asc", expectedCommand5);

        // email ascending
        SortCommand expectedCommand6 = new SortCommand(ReadOnlyPerson.EMAILSORTASC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "email asc", expectedCommand6);
    }
}
