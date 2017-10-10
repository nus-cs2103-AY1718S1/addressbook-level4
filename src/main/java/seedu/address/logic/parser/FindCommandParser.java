package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

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

    private static Set<String> prefixSet =
            new HashSet<String>(Arrays.asList(
                    CliSyntax.PREFIX_NAME_STRING,
                    CliSyntax.PREFIX_TAG_STRING,
                    CliSyntax.PREFIX_ADDRESS_STRING,
                    CliSyntax.PREFIX_PHONE_STRING,
                    CliSyntax.PREFIX_EMAIL_STRING));

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

        List<String> keywordsWithPrefix = trimInputIntoKeywordsWithPrefix(trimmedArgs);
        List<Predicate> predicateList = new ArrayList<>();

        for (String keywordWithPrefix : keywordsWithPrefix) {
            predicateList.add(keywordWithPrefixIntoPredicate(keywordWithPrefix));
        }

        return new FindCommand(new PersonContainsFieldsPredicate(predicateList));
    }

    /**
     * Parses {@code keywordWithPrefix}, identifies its prefix and returns
     * appropriate predicate for filtering
     * @throws ParseException if the user input does not conform the expected format
     */
    private Predicate keywordWithPrefixIntoPredicate(String keywordWithPrefix) throws ParseException {

        String prefix = extractPrefixFromKeywordWithPrefix(keywordWithPrefix);
        String keyword = extractKeywordFromKeywordWithPrefix(keywordWithPrefix);

        switch (prefix) {
        case CliSyntax.PREFIX_NAME_STRING:
            return new NameContainsKeywordPredicate(keyword);
        case CliSyntax.PREFIX_PHONE_STRING:
            return new PhoneContainsKeywordPredicate(keyword);
        case CliSyntax.PREFIX_ADDRESS_STRING:
            return new AddressContainsKeywordPredicate(keyword);
        case CliSyntax.PREFIX_EMAIL_STRING:
            return new EmailContainsKeywordPredicate(keyword);
        case CliSyntax.PREFIX_TAG_STRING:
            return new TagsContainKeywordPredicate(keyword);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses {@code userInput}
     * returns list of keywordsWithPrefix
     * @throws ParseException if the {@code input} does not start with a valid prefix
     */
    private List<String> trimInputIntoKeywordsWithPrefix(String userInput) throws ParseException {
        int startIndex = 0;
        int index = 0;
        int endIndex = userInput.length();
        int prefixLength = 2;

        List<String> keywordsWithPrefix = new ArrayList<>();

        //ensure we start with a prefix
        if (endIndex < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        //ensure we start with a valid prefix
        String firstPrefix = userInput.substring(startIndex, startIndex + prefixLength);
        if (!prefixSet.contains(firstPrefix)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        while (index + prefixLength < endIndex) {
            index++;
            String prefix = userInput.substring(index, index + prefixLength);

            if (prefixSet.contains(prefix)) {
                keywordsWithPrefix.add(userInput.substring(startIndex, index).trim());
                startIndex = index;
            }
        }

        //handles the last detail query
        keywordsWithPrefix.add(userInput.substring(startIndex, index + prefixLength).trim());
        return keywordsWithPrefix;
    }

    /**
     * Parses {@code keywordWithPrefix}
     * returns prefix
     * @throws ParseException if the {@code keywordWithPrefix} does not conform the expected format
     */
    private String extractPrefixFromKeywordWithPrefix(String keywordWithPrefix) throws ParseException {
        if (keywordWithPrefix.length() < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return keywordWithPrefix.substring(0, 2);
    }

    /**
     * Parses {@code keywordWithPrefix}
     * returns keyword
     * @throws ParseException if the {@code keywordWithPrefix} does not conform the expected format
     */
    private String extractKeywordFromKeywordWithPrefix(String keywordWithPrefix) throws ParseException {
        if (keywordWithPrefix.length() < 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return keywordWithPrefix.substring(2, keywordWithPrefix.length());
    }
}
