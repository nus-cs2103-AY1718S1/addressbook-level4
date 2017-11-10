package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

//@@author freesoup
public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noRange_throwsParseException() {
        //1 tags no range
        assertParseFailure(parser, "friends", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        //2 tags no range
        assertParseFailure(parser, "friends owesMoney", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        //3 tags no range
        assertParseFailure(parser, "friends owesMoney prospective", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));
    }

    @Test
    public void parse_multipleTag_throwsParseException() {
        //all range 2 tags
        assertParseFailure(parser, "all owesMoney prospective", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));

        assertParseFailure(parser, "5 owesMoney prospective", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, ".123\\5", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws IllegalValueException {
        // no leading and trailing whitespaces
        RemoveTagCommand expectedCommand = new RemoveTagCommand (new Tag("friends"));
        assertTrue(parser.parse("all friends") instanceof RemoveTagCommand);
        assertParseSuccess(parser, "all friends", expectedCommand);

        // no leading and trailing whitespaces but with Index.
        RemoveTagCommand expectedCommand2 = new RemoveTagCommand (Index.fromZeroBased(0), new Tag(
                "enemy"));
        assertTrue(parser.parse("1 enemy") instanceof RemoveTagCommand);
        assertParseSuccess(parser, "1 enemy", expectedCommand2);
    }

}
