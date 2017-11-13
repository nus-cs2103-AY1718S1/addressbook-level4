package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeWindowSizeCommand;
import seedu.address.model.windowsize.WindowSize;

//@@author vivekscl
public class ChangeWindowSizeCommandParserTest {

    private ChangeWindowSizeCommandParser parser = new ChangeWindowSizeCommandParser();

    @Test
    public void parse_validArgs_returnsChangeWindowSizeCommand() {
        assertParseSuccess(parser, "small",
                new ChangeWindowSizeCommand(WindowSize.SMALL_WINDOW_SIZE_INPUT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeWindowSizeCommand.MESSAGE_USAGE));
    }
}
