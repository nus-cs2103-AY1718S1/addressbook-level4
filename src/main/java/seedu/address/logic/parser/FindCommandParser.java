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

//@@author awarenessxz
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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_SORT);

        String trimmedArgsName = "";
        String trimmedArgsTag = "";
        int sortOrder = 0;
        String [] nameKeywords = new String[0];
        String [] tagKeywords = new String[0];

        try {
            trimmedArgsName = getArgumentName(argMultimap);
            trimmedArgsTag = getArgumentTag(argMultimap);
            sortOrder = getArgumentSortOrder(argMultimap);

            if (trimmedArgsName.isEmpty() && trimmedArgsTag.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            } else {
                if (!trimmedArgsName.isEmpty()) {
                    nameKeywords = trimmedArgsName.split("\\s+");
                }
                if (!trimmedArgsTag.isEmpty()) {
                    tagKeywords = trimmedArgsTag.split("\\s+");
                }
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                Arrays.asList(tagKeywords)), sortOrder);
    }

    /**
     * Returns argument name values if available
     *
     * @param argMultimap
     * @return argument name values
     * @throws IllegalValueException if value is empty
     */
    private String getArgumentName(ArgumentMultimap argMultimap) throws IllegalValueException {
        String trimmedArgsName = "";

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            trimmedArgsName = ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_NAME)).get().trim();
            if (trimmedArgsName.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        return trimmedArgsName;
    }

    /**
     * Returns argument tag values if available
     *
     * @param argMultimap
     * @return argument tag values
     * @throws IllegalValueException if value is empty
     */
    private String getArgumentTag(ArgumentMultimap argMultimap) throws IllegalValueException {
        String trimmedArgsTag = "";

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            trimmedArgsTag = ParserUtil.parseKeywords(argMultimap.getValue(PREFIX_TAG)).get().trim();
            if (trimmedArgsTag.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        return trimmedArgsTag;
    }

    /**
     * Returns sort option if available
     *
     * @param argMultimap
     * @return int sort option
     * @throws IllegalValueException if sort < 0
     */
    private int getArgumentSortOrder(ArgumentMultimap argMultimap) throws IllegalValueException {
        int sortOrder = 0;

        if (argMultimap.getValue(PREFIX_SORT).isPresent()) {
            sortOrder = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_SORT));
            if (sortOrder < 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        return sortOrder;
    }

}
