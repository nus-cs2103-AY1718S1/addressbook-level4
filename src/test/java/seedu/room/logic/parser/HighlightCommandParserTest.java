package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.room.logic.commands.HighlightCommand;

//@@author shitian007
public class HighlightCommandParserTest {

    private HighlightCommandParser parser = new HighlightCommandParser();

    @Test
    public void parse_validTag_success() {
        assertParseSuccess(parser, " RA", new HighlightCommand("RA"));
    }

    @Test
    public void parse_validUnhighlight_success() {
        assertParseSuccess(parser, " -", new HighlightCommand("-"));
    }

    @Test
    public void parse_invalidArgs_failure() {
        String emptyArg = "";
        assertParseFailure(parser, emptyArg,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HighlightCommand.MESSAGE_USAGE));
    }

}
