//@@author inGall
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindReminderCommand;
import seedu.address.model.reminder.TaskContainsKeywordsPredicate;

public class FindReminderCommandParserTest {

    private FindReminderCommandParser parser = new FindReminderCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindReminderCommand() {
        // no leading and trailing whitespaces
        FindReminderCommand expectedFindReminderCommand =
                new FindReminderCommand(new TaskContainsKeywordsPredicate(Arrays.asList("Submission", "Meeting")));
        assertParseSuccess(parser, "Submission Meeting", expectedFindReminderCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Submission \n \t Meeting  \t", expectedFindReminderCommand);
    }

}
