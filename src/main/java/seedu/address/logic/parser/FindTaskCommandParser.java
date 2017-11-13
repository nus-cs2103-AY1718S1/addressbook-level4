package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Arrays;

import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindTaskCommand object
 */
public class FindTaskCommandParser extends FindCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindTaskCommand
     * and returns an FindTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String argsWithNoTaskPrefix = args.replaceFirst(PREFIX_TASK.getPrefix(), "");
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsWithNoTaskPrefix, PREFIX_PRIORITY, PREFIX_STATE);
        String keywords = argMultimap.getPreamble();
        String trimmedArgs = keywords.trim();
        boolean isPriorityFindRequired = argMultimap.getValue(PREFIX_PRIORITY).isPresent();
        boolean isStateFindRequired = argMultimap.getValue(PREFIX_STATE).isPresent();
        int minPriority = 0;
        boolean isComplete = false;
        if (isPriorityFindRequired) {
            minPriority = parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        }
        if (isStateFindRequired) {
            isComplete = parseState(argMultimap.getValue(PREFIX_STATE).get());
        }
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new TaskContainsKeywordPredicate(Arrays.asList(nameKeywords),
                isStateFindRequired, isPriorityFindRequired, isComplete, minPriority));
    }

    /**
     * Parses the given string, and returns an integer corresponding to its value
     * Guarantees: The specified value is valid as a priority value
     */
    private int parsePriority(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        int priority;
        try {
            priority = Integer.parseInt(args.trim());
        } catch (NumberFormatException nfe) {
            throw new ParseException(FindTaskCommand.MESSAGE_INVALID_PRIORITY);
        }
        if (priority < 1 || priority > 5) {
            throw new ParseException(FindTaskCommand.MESSAGE_INVALID_PRIORITY);
        } else {
            return priority;
        }
    }

    /**
     * Parses the given string, and returns a boolean value corresponding to its value
     */
    private boolean parseState(String args) throws ParseException {
        String trimmed = args.trim();
        if ("true".equals(trimmed) || "false".equals(trimmed)) {
            return Boolean.valueOf(trimmed);
        } else {
            throw new ParseException(FindTaskCommand.MESSAGE_INVALID_COMPLETE_VALUE);
        }
    }
}
