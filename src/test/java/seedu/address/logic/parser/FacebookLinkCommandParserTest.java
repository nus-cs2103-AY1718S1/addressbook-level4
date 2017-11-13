package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_URL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FacebookLinkCommand;

//@@author alexfoodw
public class FacebookLinkCommandParserTest {

    private FacebookLinkCommandParser parser = new FacebookLinkCommandParser();

    @Test
    public void parse_validArgs_returnsFacebookLinkCommand() {
        assertParseSuccess(parser, FacebookLinkCommand.EXAMPLE_LINK,
                new FacebookLinkCommand(FacebookLinkCommand.EXAMPLE_LINK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "www.google.com",
                String.format(MESSAGE_INVALID_URL, "Example: " + FacebookLinkCommand.EXAMPLE_LINK));
    }
}
//@@author
