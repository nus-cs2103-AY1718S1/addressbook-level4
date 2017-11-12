package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterAllCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsAllKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterAllCommand object
 */
public class FilterAllCommandParser implements Parser<FilterAllCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterAllCommand
     * and returns an FilterAllCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterAllCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterAllCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FilterAllCommand(new PersonContainsAllKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
