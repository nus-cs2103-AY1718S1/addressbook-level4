package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.SortUtil.setupArguments;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseArgsException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonDataContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     * @throws ParseArgsException if the user input does not conform the expected format, but is in a suggestible format
     */
    public FindCommand parse(String args) throws ParseException, ParseArgsException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> dataKeywords = new ArrayList<>();
        List<SortArgument> sortArgumentList = new ArrayList<>();

        setupArguments(keywords, dataKeywords, sortArgumentList, FindCommand.MESSAGE_USAGE);

        if (dataKeywords.isEmpty()) {
            throw new ParseArgsException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonDataContainsKeywordsPredicate(dataKeywords), sortArgumentList);
    }

    /**
     * Returns a formatted argument string given unformatted {@code rawArgs}
     * or a {@code null} {@code String} if not formattable.
     */
    public static String parseArguments(String rawArgs) {
        // Check if null and is a non-empty string.
        requireNonNull(rawArgs);
        if (!rawArgs.trim().isEmpty()) {
            return " " + rawArgs.trim();
        }
        return null;
    }

}
