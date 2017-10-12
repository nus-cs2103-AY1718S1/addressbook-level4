package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_valid_arguments() throws Exception {

        // FilterType: Name
        final String validFilterTypeName = "name";
        String userInput = SortCommand.COMMAND_WORD + " " + validFilterTypeName;
        SortCommand expectedCommand = new SortCommand(validFilterTypeName);
        assertParseSuccess(parser, userInput, expectedCommand);

        // FilterType: Default.
        // TODO: Implement more sorting methods
        final String validFilterTypeDefault = "default";
        userInput = SortCommand.COMMAND_WORD + " " + validFilterTypeDefault;
        expectedCommand = new SortCommand(validFilterTypeDefault);
        assertParseSuccess(parser, userInput, expectedCommand);

        // No filterType argument: Should set to default
        userInput = SortCommand.COMMAND_WORD;
        expectedCommand = new SortCommand(validFilterTypeDefault);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalid_arguments() throws Exception {
        final String invalidFilterType = "abc";
        String userInput = SortCommand.COMMAND_WORD + " " + invalidFilterType;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        // FilterType is not name or default
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
