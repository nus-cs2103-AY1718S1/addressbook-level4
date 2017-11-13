//@@author PhuaJunJie
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindLettersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsAlphabetsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindLettersCommandParser implements Parser<FindLettersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindLettersCommand
     * and returns an FindLettersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindLettersCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLettersCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindLettersCommand(new NameContainsAlphabetsPredicate(Arrays.asList(nameKeywords)));
    }

}
