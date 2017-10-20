package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_SORT);

        String trimmedArgsName = "";
        String trimmedArgsTag = "";
        int sortOrder = 0;
        String [] nameKeywords = new String[0];
        String [] tagKeywords = new String[0];

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                trimmedArgsName = ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_NAME)).get().trim();
                nameKeywords = trimmedArgsName.split("\\s+");
                if (trimmedArgsName.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
            }
            if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
                trimmedArgsTag = ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_TAG)).get().trim();
                tagKeywords = trimmedArgsTag.split("\\s+");
                if (trimmedArgsTag.isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
            }

            if (argMultimap.getValue(PREFIX_SORT).isPresent()) {
                sortOrder = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_SORT));
                if (sortOrder < 0) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
            }

            if (trimmedArgsName.isEmpty() && trimmedArgsTag.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                Arrays.asList(tagKeywords)), sortOrder);
    }

}
