package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ClearBookmarkCommand;
//@@author Chng-Zhi-Xuan-reused
public class ClearBookmarkCommandParserTest {

    private ClearBookmarkCommandParser parser = new ClearBookmarkCommandParser();

    public ClearBookmarkCommandParserTest() throws IllegalValueException {
    }

    @Test
    public void parse_validArgs_returnsClearBookmarkCommand() {
        assertParseSuccess(parser,  "", new ClearBookmarkCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                    ClearBookmarkCommand.MESSAGE_USAGE));
    }
}
