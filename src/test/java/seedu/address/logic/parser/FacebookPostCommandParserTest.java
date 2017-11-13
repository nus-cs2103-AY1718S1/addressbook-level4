package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.FacebookPostCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

//@@author alexfoodw
public class FacebookPostCommandParserTest {
    private FacebookPostCommandParser parser = new FacebookPostCommandParser();

    @Test
    public void parse_validArgs_returnsFacebookPostCommand() {
        assertParseSuccess(parser, FacebookPostCommand.EXAMPLE_POST,
                new FacebookPostCommand(FacebookPostCommand.EXAMPLE_POST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacebookPostCommand.MESSAGE_USAGE));
    }
}
//@@author
