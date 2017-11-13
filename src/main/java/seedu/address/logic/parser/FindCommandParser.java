//@@author tbhbhbh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.person.Birthday.BIRTHDAY_MONTH_REGEX;

import java.util.Arrays;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsBirthdayPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

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
        // Single keyword as argument and check that it contains an non-zero integer
        if (nameKeywords.length == 1) {
            System.out.println(Character.isLetter(nameKeywords[0].charAt(0)));
            if (Character.isLetter(nameKeywords[0].charAt(0))) {
                return new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
            } else if (StringUtil.isNonZeroUnsignedInteger(nameKeywords[0])
                    && nameKeywords[0].matches(BIRTHDAY_MONTH_REGEX)) {
                return new FindCommand(new PersonContainsBirthdayPredicate(nameKeywords[0]));
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
