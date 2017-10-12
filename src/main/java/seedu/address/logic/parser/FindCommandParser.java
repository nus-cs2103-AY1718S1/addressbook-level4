package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindSpecificCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsSpecifiedKeywordsPredicate;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    public FindSpecificCommand parseSpecific(String args) throws ParseException {

        String trimmedArgs = args.trim();

        /**
         * Used for initial separation of prefix and args.
         */

        final Pattern FINDS_PREFIX_FORMAT = Pattern.compile("(?<prefix>\\w/)(?<arguments>.*)");

        final Matcher matcher = FINDS_PREFIX_FORMAT.matcher(trimmedArgs);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSpecificCommand.MESSAGE_USAGE));
        }

        final String prefix = matcher.group("prefix");
        final String arguments = matcher.group("arguments");

        String[] keyWords = arguments.split("\\s+");

        if (prefix.equals(PREFIX_PHONE)){
            return new FindSpecificCommand(new PersonContainsSpecifiedKeywordsPredicate(Arrays.asList(keyWords)));
        }
        else if (prefix.equals(PREFIX_TAG)){
            return new FindSpecificCommand(new PersonContainsSpecifiedKeywordsPredicate(Arrays.asList(keyWords)));
        }

        return new FindSpecificCommand(new PersonContainsSpecifiedKeywordsPredicate(Arrays.asList(keyWords)));
    }

}
