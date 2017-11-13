package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.FacebookAddCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

//@@author alexfoodw
public class FacebookAddCommandParserTest {

    private FacebookAddCommandParser parser = new FacebookAddCommandParser();

    @Test
    public void parse_validArgs_returnsFacebookLinkCommand() {
        assertParseSuccess(parser, FacebookAddCommand.EXAMPLE_NAME,
                new FacebookAddCommand(FacebookAddCommand.EXAMPLE_NAME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacebookAddCommand.MESSAGE_USAGE));
    }
}
//@@author
