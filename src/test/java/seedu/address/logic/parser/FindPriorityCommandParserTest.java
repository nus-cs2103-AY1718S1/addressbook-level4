//@@author inGall
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPriorityCommand;
import seedu.address.model.reminder.PriorityContainsKeywordsPredicate;

public class FindPriorityCommandParserTest {

    private FindPriorityCommandParser parser = new FindPriorityCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPriorityCommand() {
        // no leading and trailing whitespaces
        FindPriorityCommand expectedFindPriorityCommand =
                new FindPriorityCommand(new PriorityContainsKeywordsPredicate(Arrays.asList("Low", "High")));
        assertParseSuccess(parser, "Low High", expectedFindPriorityCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Low \n \t High  \t", expectedFindPriorityCommand);
    }

}
