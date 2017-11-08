package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.ChangeModeCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class ChangeModeCommandParserTest {

    private ChangeModeCommandParser parser = new ChangeModeCommandParser();

    @Test
    public void parseValidArgsReturnsChangeModeCommand() {
        assertParseSuccess(parser, "ab", new ChangeModeCommand("ab"));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeModeCommand
            .MESSAGE_USAGE));
    }
}
