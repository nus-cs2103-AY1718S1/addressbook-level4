package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindNumberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NumberContainsKeywordsPredicate;

public class FindNumberCommandParser implements Parser<FindNumberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindNumberCommand
     * and returns an FindNumberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNumberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNumberCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindNumberCommand(new NumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
