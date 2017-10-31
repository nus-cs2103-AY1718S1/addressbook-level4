package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_FINDCOMMAND;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.CountryContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.ScheduleContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Used to identify prefix and args.
     */
    private static final Pattern FIND_COMMAND_FORMAT = Pattern.compile("(?<prefix>\\w+/)(?<arguments>.*)");

    private Predicate<ReadOnlyPerson> predicate;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * to allocate the given {@code} of arguments fpr each predicate.
     * @return FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = FIND_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        final String prefix = matcher.group("prefix");
        final String arguments = matcher.group("arguments");

        String[] keywords = arguments.trim().split("\\s+");

        switch (prefix) {
        case ("a/"):
            predicate = new AddressContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("act/"):
            predicate = new ScheduleContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("c/"):
            predicate = new CountryContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("e/"):
            predicate = new EmailContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("n/"):
            predicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("t/"):
            predicate = new TagContainsKeywordsPredicate(Arrays.asList(keywords));
            break;
        case ("p/"):
            if (!validPhoneNumbers(keywords)) {
                throw new ParseException(PhoneContainsKeywordsPredicate.MESSAGE_PHONE_VALIDATION);
            }
            String[] keywordsPhone = arguments.replaceAll("\\s", "").split("(?<=\\G.{4})");
            predicate = new PhoneContainsKeywordsPredicate(Arrays.asList(keywordsPhone));
            break;

        default:
            throw new ParseException(MESSAGE_UNKNOWN_FINDCOMMAND);



        }
        return new FindCommand(predicate);
    }


    public static boolean validPhoneNumbers(String[] phoneNumbers) {
        return Stream.of(phoneNumbers).allMatch(length -> length.matches("\\d{4}") || length.matches("\\d{8}"));
    }
}
