package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.MusicCommand;

//@@author hanselblack
public class MusicCommandParserTest {

    private MusicCommandParser parser = new MusicCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "play", new MusicCommand("play"));
        assertParseSuccess(parser, "play", new MusicCommand("play", "pop"));
        assertParseSuccess(parser, "play pop", new MusicCommand("play", "pop"));
        assertParseSuccess(parser, "stop", new MusicCommand("stop"));
        assertParseSuccess(parser, "pause", new MusicCommand("pause"));
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "any argument 23232",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "play pop wdwdwdw",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
    }
}
