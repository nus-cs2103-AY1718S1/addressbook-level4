package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

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
        args = args.trim();

        String field;
        String trimmedArgs;

        if (args.length() > 2) {
            field = args.substring(0, 2);
            if (!field.substring(1, 2).equals("/")) {
                trimmedArgs = args;
                field = " n/";
            } else {
                field = " " + field;
                trimmedArgs = args.substring(2);
            }
        } else {
            trimmedArgs = args;
            field = " n/";
        }

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        FindCommand returnFindCommand = new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        switch (field) {
        case " n/":
            returnFindCommand = new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
            break;
        case " a/":
            returnFindCommand = new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
            break;
        case " e/":
            returnFindCommand = new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
            break;
        case " t/":
            returnFindCommand = new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));
            break;
        case " p/":
            returnFindCommand = new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
            break;
        default:
            break;
        }

        return returnFindCommand;
    }

}
