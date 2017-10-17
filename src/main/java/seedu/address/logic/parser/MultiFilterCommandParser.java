package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.MultiFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsSubstrPredicate;
import seedu.address.model.person.EmailContainsKeywordsSubstrPredicate;
import seedu.address.model.person.NameContainsKeywordsSubstrPredicate;
import seedu.address.model.person.PhoneContainsKeywordsSubstrPredicate;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class MultiFilterCommandParser implements Parser<MultiFilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MultiFilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        // Parse and get the values of each field
        Optional<String> nameOptional = argMultimap.getValue(PREFIX_NAME).filter(s -> !s.isEmpty());
        Optional<String> phoneOptional = argMultimap.getValue(PREFIX_PHONE).filter(s -> !s.isEmpty());
        Optional<String> emailOptional = argMultimap.getValue(PREFIX_EMAIL).filter(s -> !s.isEmpty());
        Optional<String> addressOptional = argMultimap.getValue(PREFIX_ADDRESS).filter(s -> !s.isEmpty());

        /* Get filter predicates for each field based on user input
         * If a field has been entered by user, then assign a predicate object
         * Otherwise, assign a null object as a predicate to indicate
         * that field has not been entered
         */
        NameContainsKeywordsSubstrPredicate namePredicate = (!nameOptional.isPresent())
                ? null : new NameContainsKeywordsSubstrPredicate(
                        Arrays.asList(nameOptional.get()));

        PhoneContainsKeywordsSubstrPredicate phonePredicate = (!phoneOptional.isPresent())
                ? null : new PhoneContainsKeywordsSubstrPredicate(
                        Arrays.asList(phoneOptional.get()));

        EmailContainsKeywordsSubstrPredicate emailPredicate = (!emailOptional.isPresent())
                ? null : new EmailContainsKeywordsSubstrPredicate(
                        Arrays.asList(emailOptional.get()));

        AddressContainsKeywordsSubstrPredicate addressPredicate = (!addressOptional.isPresent())
                ? null : new AddressContainsKeywordsSubstrPredicate(
                        Arrays.asList(addressOptional.get()));

        return new MultiFilterCommand(namePredicate, phonePredicate, emailPredicate, addressPredicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
