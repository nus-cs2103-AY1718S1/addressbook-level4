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
import seedu.address.model.person.*;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static String PHONE_PREDICATE_VALIDATION_REGEX1 = "\\d{4}";
    private static String PHONE_PREDICATE_VALIDATION_REGEX2 = "\\d{8}";
    public Predicate<ReadOnlyPerson> predicate;

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

        if (!areSomePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        }

        String[] keywords = args.substring(args.indexOf('/') + 1).trim().split("\\s+");

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            predicate = new EmailContainsKeywordsPredicate(Arrays.asList(keywords));
        } else if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            predicate = new AddressContainsKeywordsPredicate(Arrays.asList(keywords));
        }
     else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
        if (!validPhoneNumbers(keywords)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String preppedPhone = args.substring(args.indexOf('/') + 1).trim();
        String[] keywordsPhone = preppedPhone.replaceAll("\\s", "").split("(?<=\\G.{4})");
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList(keywordsPhone));
    }



        return new FindCommand(predicate);


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
     */
    private static boolean validPhoneNumbers(String[] keywords) {
        return Stream.of(keywords).allMatch(length -> length.matches(PHONE_PREDICATE_VALIDATION_REGEX2) || length.matches(PHONE_PREDICATE_VALIDATION_REGEX1));
    }

}
