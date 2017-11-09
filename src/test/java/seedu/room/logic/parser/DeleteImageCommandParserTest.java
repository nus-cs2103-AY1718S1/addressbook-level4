package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.room.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.room.logic.commands.DeleteImageCommand;

//@@author shitian007
public class DeleteImageCommandParserTest {

    private DeleteImageCommandParser parser = new DeleteImageCommandParser();

    @Test
    public void parse_validIndex_success() {
        String validInput = " " + INDEX_FIRST_PERSON.getOneBased();
        DeleteImageCommand expectedCommand = new DeleteImageCommand(INDEX_FIRST_PERSON);

        assertParseSuccess(parser, validInput, expectedCommand);
    }

    @Test
    public void parse_indexNonInteger_failure() {
        String invalidIndexArgs = "one ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteImageCommand.MESSAGE_USAGE);

        assertParseFailure(parser, invalidIndexArgs, expectedMessage);
    }

}

