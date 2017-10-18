package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
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
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArrayList<String> keywords = new ArrayList<>();
        Predicate<ReadOnlyPerson> predicate;
        StringTokenizer st = new StringTokenizer(trimmedArgs, " ");
        String commandPrefix = st.nextToken();
        if (st.hasMoreTokens()) {
            while (st.hasMoreTokens()) {
                keywords.add(st.nextToken());
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (commandPrefix.equals(PREFIX_ADDRESS.getPrefix())) {
            predicate = new AddressContainsKeywordsPredicate(keywords);
        } else if (commandPrefix.equals(PREFIX_EMAIL.getPrefix())) {
            predicate = new EmailContainsKeywordsPredicate(keywords);
        } else if (commandPrefix.equals(PREFIX_NAME.getPrefix())) {
            predicate = new NameContainsKeywordsPredicate(keywords);
        } else if (commandPrefix.equals(PREFIX_PHONE.getPrefix())) {
            predicate = new PhoneContainsKeywordsPredicate(keywords);
        } else if (commandPrefix.equals(PREFIX_TAG.getPrefix())) {
            predicate = new TagContainsKeywordsPredicate(keywords);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindCommand(predicate);
    }

}
