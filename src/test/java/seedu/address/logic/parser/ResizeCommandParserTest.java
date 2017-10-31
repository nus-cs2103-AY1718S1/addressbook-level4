package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ResizeCommand;

//@@author newalter
public class ResizeCommandParserTest {

    private ResizeCommandParser parser = new ResizeCommandParser();

    @Test
    public void parse_validArgs_returnsResizeCommand() {
        assertParseSuccess(parser, String.format("%d %d", ResizeCommand.MAX_WIDTH, ResizeCommand.MAX_HEIGHT),
                new ResizeCommand(ResizeCommand.MAX_WIDTH, ResizeCommand.MAX_HEIGHT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid argument
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));

        // invalid number of arguments
        assertParseFailure(parser, "800 600 600",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));

        // invalid arguments that are out of bound
        assertParseFailure(parser, String.format("%d %d", ResizeCommand.MAX_WIDTH + 1, ResizeCommand.MAX_HEIGHT),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));


    }
}
