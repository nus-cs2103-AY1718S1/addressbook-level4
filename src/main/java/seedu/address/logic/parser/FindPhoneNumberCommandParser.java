package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPhoneNumberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPhoneNumberCommand object
 */
public class FindPhoneNumberCommandParser implements Parser<FindPhoneNumberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneNumberCommand
     * and returns an FindPhoneNumberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPhoneNumberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneNumberCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPhoneNumberCommand(new NumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
