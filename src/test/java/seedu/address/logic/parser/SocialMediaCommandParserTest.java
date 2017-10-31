package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SocialMediaCommand;

//@@author kenpaxtonlim
public class SocialMediaCommandParserTest {
    private SocialMediaCommandParser parser = new SocialMediaCommandParser();

    @Test
    public void parse_argsSpecified_success() throws Exception {
        String userInputFacebook = " " + SocialMediaCommand.TYPE_FACEBOOK + " " + INDEX_FIRST_PERSON.getOneBased();
        SocialMediaCommand expectedCommandFacebook = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_FACEBOOK);

        assertParseSuccess(parser, userInputFacebook, expectedCommandFacebook);

        String userInputTwitter = " " + SocialMediaCommand.TYPE_TWITTER + " " + INDEX_FIRST_PERSON.getOneBased();
        SocialMediaCommand expectedCommandTwitter = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_TWITTER);

        assertParseSuccess(parser, userInputTwitter, expectedCommandTwitter);

        String userInputInstagram = " " + SocialMediaCommand.TYPE_INSTAGRAM + " " + INDEX_FIRST_PERSON.getOneBased();
        SocialMediaCommand expectedCommandInstagram = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_INSTAGRAM);

        assertParseSuccess(parser, userInputInstagram, expectedCommandInstagram);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE);

        assertParseFailure(parser, SocialMediaCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void parse_noIndexSpecified_failure() throws Exception {
        String userInput = " " + SocialMediaCommand.TYPE_INSTAGRAM;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_noTypeSpecified_failure() throws Exception {
        String userInput = " " + INDEX_FIRST_PERSON.getOneBased();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
