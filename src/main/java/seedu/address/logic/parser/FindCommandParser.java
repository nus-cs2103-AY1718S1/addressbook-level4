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

    //@@author Gideonfu
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

        String[] keywords = trimmedArgs.split("\\s+");

        String mainKeyword = keywords[0];
        if (!mainKeyword.equals(FindCommand.KEYWORD_NAME) && !mainKeyword.equals(FindCommand.KEYWORD_ADDRESS)
                && !mainKeyword.equals(FindCommand.KEYWORD_EMAIL) && !mainKeyword.equals(FindCommand.KEYWORD_PHONE)
                && !mainKeyword.equals(FindCommand.KEYWORD_BIRTHDAY) && !mainKeyword.equals(FindCommand.KEYWORD_TAG)
                && !mainKeyword.equals(FindCommand.KEYWORD_ORGANISATION)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (keywords.length == 1) {
            throw new ParseException(FindCommand.MESSAGE_NO_KEYWORD);
        }

        String[] searchKeywords = new String[keywords.length - 1];
        System.arraycopy(keywords, 1, searchKeywords, 0, keywords.length - 1);

        if (mainKeyword.equals(FindCommand.KEYWORD_BIRTHDAY)) {
            for (String keyword : searchKeywords) {
                if (!keyword.matches("(0[1-9]|1[0-2])")) {
                    throw new ParseException(FindCommand.MESSAGE_INVALID_BIRTHDAY_MONTH);
                }
            }
        }

        return new FindCommand(new ContainsKeywordsPredicate(Arrays.asList(searchKeywords), mainKeyword));
    }

}
