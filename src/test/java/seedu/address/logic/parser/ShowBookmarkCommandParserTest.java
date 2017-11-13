package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ShowBookmarkCommand;

public class ShowBookmarkCommandParserTest {

    private ShowBookmarkCommandParser parser = new ShowBookmarkCommandParser();

    public ShowBookmarkCommandParserTest() throws IllegalValueException {
    }

    @Test
    public void parse_validArgs_returnsShowBookmarkCommand() {
        assertParseSuccess(parser,  "", new ShowBookmarkCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowBookmarkCommand.MESSAGE_USAGE));
    }
}
