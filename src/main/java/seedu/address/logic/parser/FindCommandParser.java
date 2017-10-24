package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
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

    private Predicate<ReadOnlyPerson> predicate;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!areSomePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = args.substring(args.indexOf('/') + 1).trim().split("\\s+");
        keywordsPredicate(args, argMultimap, keywords);

        return new FindCommand(predicate);
    }

    /**
     * Allocate the given {@code} of arguments fpr each predicate
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private void keywordsPredicate(String args, ArgumentMultimap argMultimap, String[] keywords) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            predicate = new EmailContainsKeywordsPredicate(Arrays.asList(keywords));
        } else if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            predicate = new AddressContainsKeywordsPredicate(Arrays.asList(keywords));
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            predicate = new TagContainsKeywordsPredicate(Arrays.asList(keywords));
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            if (!validPhoneNumbers(keywords)) {
                throw new ParseException(String.format(PhoneContainsKeywordsPredicate.MESSAGE_PHONE_VALIDATION));
            }
            String preppedPhone = args.substring(args.indexOf('/') + 1).trim();
            String[] keywordsPhone = preppedPhone.replaceAll("\\s", "").split("(?<=\\G.{4})");
            predicate = new PhoneContainsKeywordsPredicate(Arrays.asList(keywordsPhone));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areSomePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if email parameter has a length of 4 digits or 8 digits exactly
     * @param phoneNumbers
     */

    public static boolean validPhoneNumbers(String [] phoneNumbers) {
        return Stream.of(phoneNumbers).allMatch(length -> length.matches("\\d{4}") || length.matches("\\d{8}"));
    }
}
