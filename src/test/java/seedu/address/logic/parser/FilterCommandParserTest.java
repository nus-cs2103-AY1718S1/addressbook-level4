package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;

//@@author jelneo
public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand = new FilterCommand(Arrays.asList("violent", "friendly"));
        assertParseSuccess(parser, "violent friendly", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n violent \n \t friendly  \t", expectedFilterCommand);

        // alphanumeric arguments
        expectedFilterCommand = new FilterCommand(Arrays.asList("violent12", "friendly435", "2141244"));
        assertParseSuccess(parser, " violent12 friendly435 2141244", expectedFilterCommand);
    }

    @Test
    public void parse_invalidArgs() {
        // no arguments
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);

        // invalid tag names
        assertParseFailure(parser, "???*^%%", MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "???*^%%sfsa !abc", MESSAGE_TAG_CONSTRAINTS);
    }
}
