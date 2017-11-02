package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.room.logic.commands.HighlightCommand;

public class HighlightCommandParserTest {

    private HighlightCommandParser parser = new HighlightCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String emptyArg = "";
        assertParseFailure(parser, emptyArg,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HighlightCommand.MESSAGE_USAGE));
    }
}
