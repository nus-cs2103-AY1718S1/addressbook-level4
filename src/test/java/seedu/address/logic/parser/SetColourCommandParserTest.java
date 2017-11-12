package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SetColourCommand;

//@@author eldonng
/**
 * Parses SetColourCommand arguments and creates a SetColourCommand object
 */
public class SetColourCommandParserTest {

    private SetColourCommandParser parser = new SetColourCommandParser();

    @Test
    public void parse_validArgs_returnsSetColourCommand() {
        assertParseSuccess(parser, "friends red", new SetColourCommand("friends", "red"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetColourCommand.MESSAGE_USAGE));
    }
}
