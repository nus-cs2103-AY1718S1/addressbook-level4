//@@author inGall
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.PriorityContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPriorityCommand object
 */
public class FindPriorityCommandParser implements Parser<FindPriorityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPriorityCommand
     * and returns an FindPriorityCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPriorityCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPriorityCommand.MESSAGE_USAGE));
        }

        String[] priorityKeywords = trimmedArgs.split("\\s+");

        return new FindPriorityCommand(new PriorityContainsKeywordsPredicate(Arrays.asList(priorityKeywords)));
    }

}
