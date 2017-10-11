package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordPredicate;
import seedu.address.model.person.EmailContainsKeywordPredicate;
import seedu.address.model.person.NameContainsKeywordPredicate;
import seedu.address.model.person.PersonContainsFieldsPredicate;
import seedu.address.model.person.PhoneContainsKeywordPredicate;
import seedu.address.model.person.TagsContainKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {



   private static List<Prefix> prefixList = Arrays.asList(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_EMPTY);

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
        return new FindCommand(inputIntoPredicate(args));
    }

    /**
     * Parses {@code userInput}
     * returns {@code PersonContainsFieldsPredicate} that matches the requirements of the input
     */
    private PersonContainsFieldsPredicate inputIntoPredicate(String userInput) {

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        List<Predicate> predicateList = new ArrayList<>();
        for (Prefix prefix : prefixList) {
            for (String value : argumentMultimap.getAllValues(prefix)) {
                predicateList.add(valueAndPrefixIntoPredicate(value, prefix));
            }
        }
        return new PersonContainsFieldsPredicate(predicateList);
    }

    /**
     * Takes in {@code value} and {@code prefix}
     * returns {@code Predicate} that checks for the value in person's field based on prefix
     */
    private Predicate valueAndPrefixIntoPredicate(String value, Prefix prefix) {
        switch (prefix.toString()) {
        case CliSyntax.PREFIX_NAME_STRING:
            return new NameContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_PHONE_STRING:
            return new PhoneContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_ADDRESS_STRING:
            return new AddressContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_EMAIL_STRING:
            return new EmailContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_TAG_STRING:
            return new TagsContainKeywordPredicate(value);
        case CliSyntax.PREFIX_EMPTY_STRING:
            return new NameContainsKeywordPredicate(value);
        default:
            return new NameContainsKeywordPredicate(value);
        }
    }
}

