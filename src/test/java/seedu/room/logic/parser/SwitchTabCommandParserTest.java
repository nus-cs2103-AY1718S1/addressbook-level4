package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.room.logic.commands.SwitchTabCommand;

//@@author sushinoya
public class SwitchTabCommandParserTest {

    private SwitchTabCommandParser parser = new SwitchTabCommandParser();

    @Test
    public void parseValidArgsReturnsSwitchTabCommand() {
        assertParseSuccess(parser, " 1", new SwitchTabCommand(1));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, " a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidNumArgsThrowsParseException() {
        assertParseFailure(parser, " 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SwitchTabCommand.MESSAGE_USAGE));
    }
}
