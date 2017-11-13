package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.BirthdayContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagListContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    //@@author k-l-a
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * @return FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || isSearchablePrefix(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        String toSearch = keywords[0];
        if (startsWithSearchablePrefix(toSearch)) {
            String extractedKeyword = toSearch.substring(2, toSearch.length());
            toSearch = toSearch.substring(0, 2);
            if (!extractedKeyword.isEmpty()) {
                keywords[0] = extractedKeyword;
            } else {
                keywords = Arrays.copyOfRange(keywords, 1, keywords.length);
            }
        }

        if (toSearch.equals(PREFIX_TAG.getPrefix())) {
            return new FindCommand(new TagListContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (toSearch.equals(PREFIX_PHONE.getPrefix())) {
            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (toSearch.equals(PREFIX_EMAIL.getPrefix())) {
            return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (toSearch.equals(PREFIX_ADDRESS.getPrefix())) {
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (toSearch.equals(PREFIX_BIRTHDAY.getPrefix())) {
            return new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else {
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
    }

    /**
     * Checks if the given string prefix is a searchable prefix (n/, t/, p/, e/, a/, or b/).
     */
    public boolean isSearchablePrefix(String prefixString) {
        return prefixString.equals(PREFIX_NAME.getPrefix()) || prefixString.equals(PREFIX_TAG.getPrefix())
                || prefixString.equals(PREFIX_PHONE.getPrefix()) || prefixString.equals(PREFIX_EMAIL.getPrefix())
                || prefixString.equals(PREFIX_ADDRESS.getPrefix()) || prefixString.equals(PREFIX_BIRTHDAY.getPrefix());
    }

    /**
     * Checks if the given string prefix stars with a searchable prefix (n/, t/, p/, e/, a/, or b/).
     */
    public boolean startsWithSearchablePrefix(String prefixString) {
        if (prefixString.length() < 2) {
            return false;
        }
        String prefix = prefixString.substring(0, 2);
        return isSearchablePrefix(prefix);
    }
}
