package seedu.address.logic.parser;

import org.junit.Test;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import seedu.address.logic.commands.RemoveTagCommand;


public class RemoveTagParserTest {

    private static String EMPTY_TAG_INPUT = "";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

    private RemoveTagParser parser = new RemoveTagParser();

    @Test
    public void parse_missingParts_failure() {
        // no tagName specified
        assertParseFailure(parser, EMPTY_TAG_INPUT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validFields_success() {
        String userInput = VALID_TAG_FRIEND;
        RemoveTagCommand expectedCommand = new RemoveTagCommand(userInput);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
