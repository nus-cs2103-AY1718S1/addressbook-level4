package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.tasks.FindTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

//@@author tby1994
/**
 * Parses input arguments and creates a new FindTaskCommand object
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] taskKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList(taskKeywords)));
    }

}
