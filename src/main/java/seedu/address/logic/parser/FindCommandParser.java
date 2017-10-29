package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.BirthdayContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NumberContainsKeywordsPredicate;
import seedu.address.model.person.RemarkContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

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

        /**
         * Used for initial separation of command word and args.
         */
        final Pattern commandFormat = Pattern.compile("(?<commandWord>\\w/)(?<arguments>.*)");
        final Matcher matcher = commandFormat.matcher(args.trim());

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        String[] keywords = arguments.split("\\s", 0);

        switch(commandWord) {

        case ("n/"):
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        case ("p/"):
            return new FindCommand(new NumberContainsKeywordsPredicate(Arrays.asList(keywords)));

        case ("e/"):
            return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));

        case ("t/"):
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));

        case("a/"):
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));

        case("r/"):
            return new FindCommand(new RemarkContainsKeywordsPredicate(Arrays.asList(keywords)));

        case("b/"):
            return new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList(keywords)));

        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
