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
    public void parse_MultipleArg_throwsParseException() {
        assertParseFailure(parser, "friends owesMoney", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws IllegalValueException {
        // no leading and trailing whitespaces
        RemoveTagCommand expectedCommand = new RemoveTagCommand (new Tag("friends"));
        assertTrue(parser.parse("friends") instanceof RemoveTagCommand);
        assertParseSuccess(parser, "friends", expectedCommand);

        // no leading and trailing whitespaces but with Index.
        RemoveTagCommand expectedCommand2 = new RemoveTagCommand (Index.fromZeroBased(0), new Tag(
                "enemy"));
        assertTrue(parser.parse("1 enemy") instanceof RemoveTagCommand);
        assertParseSuccess(parser, " 1 enemy", expectedCommand2);
    }

}
