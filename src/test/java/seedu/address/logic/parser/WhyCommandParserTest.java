package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.WhyCommand;

//@@author arnollim
/**
 * WhyCommandParserTest tests the validity of the indices provided to WhyCommand
 */
public class WhyCommandParserTest {

    private WhyCommandParser parser = new WhyCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidIndex = "abc";
        String feedbackToUser = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidIndex, feedbackToUser);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String invalidIndex = "0";
        String feedbackToUser = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidIndex, feedbackToUser);
    }

}
