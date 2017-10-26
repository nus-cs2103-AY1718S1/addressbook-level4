package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.DeleteByTagCommand;



public class DeleteByTagCommandParserTest {
    private DeleteByTagCommandParser parser = new DeleteByTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByTagCommand() throws IllegalValueException {
        assertParseSuccess(parser, "friends", new DeleteByTagCommand("friends"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "friends forever", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByTagCommand.MESSAGE_USAGE));
    }

}
