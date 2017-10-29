package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.TaskContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindReminderCommand object
 */
public class FindReminderCommandParser implements Parser<FindReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindReminderCommand
     * and returns an FindReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindReminderCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindReminderCommand.MESSAGE_USAGE));
        }

        String[] reminderKeywords = trimmedArgs.split("\\s+");

        return new FindReminderCommand(new TaskContainsKeywordsPredicate(Arrays.asList(reminderKeywords)));
    }

}
