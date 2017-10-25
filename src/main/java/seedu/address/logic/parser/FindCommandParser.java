package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAPPING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.AnyContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
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
        Predicate<ReadOnlyPerson> predicate;
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Prefix> getPrefixInArgs = getPrefixes(trimmedArgs);

        if (getPrefixInArgs.isEmpty()) {
            List<String> arguments = Arrays.asList(trimmedArgs.split(" "));
            List<String> trimmedArguments = prepareArguments(arguments);
            return new FindCommand(new AnyContainsKeywordsPredicate(trimmedArguments));
        }

        if (getPrefixInArgs.size() > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Prefix targetPrefix = getPrefixInArgs.get(0);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, targetPrefix);

        if (!isAPrefixWithValue(argMultimap, targetPrefix)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (targetPrefix.equals(PREFIX_NAME)) {
            List<String> name = argMultimap.getAllValues(PREFIX_NAME);
            List<String> trimmedName = prepareArguments(name);
            predicate = new NameContainsKeywordsPredicate(trimmedName);
        } else if (targetPrefix.equals(PREFIX_PHONE)) {
            List<String> phone = argMultimap.getAllValues(PREFIX_PHONE);
            List<String> trimmedPhone = prepareArguments(phone);
            predicate = new PhoneContainsKeywordsPredicate(trimmedPhone);
        } else if (targetPrefix.equals(PREFIX_EMAIL)) {
            List<String> email = argMultimap.getAllValues(PREFIX_EMAIL);
            List<String> trimmedEmail = prepareArguments(email);
            predicate = new EmailContainsKeywordsPredicate(trimmedEmail);
        } else if (targetPrefix.equals(PREFIX_ADDRESS)) {
            List<String> address = argMultimap.getAllValues(PREFIX_ADDRESS);
            List<String> trimmedAddress = prepareArguments(address);
            predicate = new AddressContainsKeywordsPredicate(trimmedAddress);
        } else {
            List<String> tagList = argMultimap.getAllValues(PREFIX_TAG);
            List<String> trimmedTagList = prepareArguments(tagList);
            predicate = new TagContainsKeywordsPredicate(trimmedTagList);
        }
        assert(PREFIX_MAPPING.containsValue(targetPrefix));

        return new FindCommand(predicate);
    }

    /**
     * Returns a list of prefixes that can be found in the arguments list.
     * @param args
     */
    private static List<Prefix> getPrefixes(String args) {
        List<Prefix> prefixesInList = new ArrayList<>();
        for (Map.Entry<String, Prefix> entry : PREFIX_MAPPING.entrySet()) {
            if (args.contains(entry.getKey())) {
                prefixesInList.add(entry.getValue());
            }
        }
        return prefixesInList;
    }

    /**
     * Prepares the argument list to be searched by ensuring that each argument is a single word without
     * any leading or ending whitespaces.
     * @param potentialArgumentsList
     */
    private static List<String> prepareArguments(List<String> potentialArgumentsList) {
        List<String> preparedArgumentsList = new ArrayList<>();
        for (String arg : potentialArgumentsList) {
            String[] element = arg.split(" ");
            for (String subElement : element) {
                String trimmedSubElement = subElement.trim();
                if (!trimmedSubElement.isEmpty()) {
                    preparedArgumentsList.add(trimmedSubElement);
                }
            }
        }
        return preparedArgumentsList;
    }

    /**
     * Returns true if the target prefix contains at least one non-empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isAPrefixWithValue(ArgumentMultimap argumentMultimap, Prefix prefix) {
        List<String> values = argumentMultimap.getAllValues(prefix);
        boolean hasValue = false;
        for (String v : values) {
            if (!v.isEmpty()) {
                hasValue = true;
            }
        }
        return hasValue;
    }

}
