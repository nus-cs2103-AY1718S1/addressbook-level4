package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FindCommand.FALSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicate.ContainsTagsPredicate;
import seedu.address.model.person.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PhoneContainsKeywordsPredicate;

//@@author newalter
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Prefix[] searchFields = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG};

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, searchFields);
        if (!isAnyPrefixPresent(argMultimap, searchFields)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArrayList<Predicate<ReadOnlyPerson>> predicates = new ArrayList<>();

        for (Prefix prefix : searchFields) {
            if (argMultimap.getValue(prefix).isPresent()) {
                List<String> keywords = extractKeywords(argMultimap, prefix);
                switch (prefix.getPrefix()) {
                case "n/":
                    predicates.add(new NameContainsKeywordsPredicate(keywords));
                    break;
                case "p/":
                    predicates.add(new PhoneContainsKeywordsPredicate(keywords));
                    break;
                case "e/":
                    predicates.add(new EmailContainsKeywordsPredicate(keywords));
                    break;
                case "a/":
                    predicates.add(new AddressContainsKeywordsPredicate(keywords));
                    break;
                case "t/":
                    predicates.add(new ContainsTagsPredicate(keywords));
                    break;
                default:
                    assert false : "There should not be other prefixes.";
                }
            } else {
                predicates.add(FALSE);
            }
        }

        return new FindCommand(predicates);
    }

    /**
     * Returns false if all of the prefixes contains empty values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isAnyPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * extracts keywords separated by space under the specified{@code prefix}
     * @param argumentMultimap the argumentMultimap that maps prefix to keywords
     * @param prefix the specified prefix
     * @return the list of keywords
     */
    private static List<String> extractKeywords(ArgumentMultimap argumentMultimap, Prefix prefix) {
        List<String> keywords = new ArrayList<>();
        for (String argument : argumentMultimap.getAllValues(prefix)) {
            keywords.addAll(Arrays.asList(argument.split("\\s+")));
        }
        return keywords;
    }
}
