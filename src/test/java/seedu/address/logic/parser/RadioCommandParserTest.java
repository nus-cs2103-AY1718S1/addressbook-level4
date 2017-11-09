package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RadioCommand;

//@@author hanselblack
public class RadioCommandParserTest {

    private RadioCommandParser parser = new RadioCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "any argument",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RadioCommand.MESSAGE_USAGE));
    }
}
