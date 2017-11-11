package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;


import org.junit.Test;

import seedu.address.logic.commands.WhyCommand;

/**
 * WhyCommandParserTest tests the validity of the indices provided to WhyCommand
 */
public class WhyCommandParserTest {

    private WhyCommandParser parser = new WhyCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhyCommand.MESSAGE_USAGE));
    }

}
