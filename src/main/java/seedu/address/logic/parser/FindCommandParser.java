package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        if (trimmedArgs.charAt(0) == 'n') {
            ContainsKeywordsPredicate.setPredicateType('n');
        } else if (trimmedArgs.charAt(0) == 'a') {
            ContainsKeywordsPredicate.setPredicateType('a');
        } else if (trimmedArgs.charAt(0) == 'm') {
            ContainsKeywordsPredicate.setPredicateType('m');
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        trimmedArgs = trimmedArgs.substring(2).trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new ContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
