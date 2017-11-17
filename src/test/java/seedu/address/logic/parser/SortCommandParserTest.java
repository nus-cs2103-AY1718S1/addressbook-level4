//@@author siri99
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    final String invalidSortFilter = "people";
    final String sortFilterBirthday = "birthday";
    final String sortFilterBirthdayAlias = "b";
    final String sortFilterDefault = "";
    final String sortFilterName = "name";
    final String sortFilterScore = "score";

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() throws Exception {

        String userInput;
        SortCommand expectedCommand;

        // No sortFilter argument: default sort in alphabhetical order of names
        userInput = SortCommand.COMMAND_WORD + sortFilterDefault;
        expectedCommand = new SortCommand(sortFilterDefault);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in alphabhetical order of names
        userInput = SortCommand.COMMAND_WORD + " " + sortFilterName;
        expectedCommand = new SortCommand(sortFilterName);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in ascending order of birthdays
        userInput = SortCommand.COMMAND_WORD + " " + sortFilterBirthday;
        expectedCommand = new SortCommand(sortFilterBirthday);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in ascending order of birthdays using alias 'b' for birthdays
        userInput = SortCommand.COMMAND_WORD + " " + sortFilterBirthdayAlias;
        expectedCommand = new SortCommand(sortFilterBirthdayAlias);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in descending order of scores (9 to 0 : best to worst score)
        userInput = SortCommand.COMMAND_WORD + " " + sortFilterScore;
        expectedCommand = new SortCommand(sortFilterScore);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in alphabhetical order of names with extra spaces
        userInput = SortCommand.COMMAND_WORD + "     " + sortFilterName;
        expectedCommand = new SortCommand(sortFilterName);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_invalid_arguments() throws Exception {
        String userInput = SortCommand.COMMAND_WORD + " " + invalidSortFilter;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
//@@author siri99
