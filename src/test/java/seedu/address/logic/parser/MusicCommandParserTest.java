package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.MusicCommand;

//@@author hanselblack
public class MusicCommandParserTest {

    private MusicCommandParser parser = new MusicCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "any argument",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
    }
}
