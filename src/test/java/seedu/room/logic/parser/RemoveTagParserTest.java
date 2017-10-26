package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.room.logic.commands.RemoveTagCommand;


public class RemoveTagParserTest {

    private static final String MESSAGE_EMPTY_TAG_INPUT = "";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

    private RemoveTagParser parser = new RemoveTagParser();

    @Test
    public void parse_missingParts_failure() {
        // no tagName specified
        assertParseFailure(parser, MESSAGE_EMPTY_TAG_INPUT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validFields_success() {
        String userInput = VALID_TAG_FRIEND;
        RemoveTagCommand expectedCommand = new RemoveTagCommand(userInput);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
