package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameAndTagsContainsKeywordsPredicate;

//@@author chuaweiwen
/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywordsList = new ArrayList<>();
        List<String> tagsKeywordsList = new ArrayList<>();

        String regex = "\\s+";

        // Extracting name
        if (!argMultimap.getAllValues(PREFIX_NAME).isEmpty()) {
            List<String> unprocessedNames = argMultimap.getAllValues(PREFIX_NAME);
            nameKeywordsList = Arrays.asList(getKeywordsFromList(unprocessedNames, regex));
        }

        // Extracting tags
        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            List<String> unprocessedTags = argMultimap.getAllValues(PREFIX_TAG);
            tagsKeywordsList = Arrays.asList(getKeywordsFromList(unprocessedTags, regex));
        }

        return new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywordsList, tagsKeywordsList));
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private String[] getKeywordsFromList(List<String> list, String regex) throws ParseException {
        String keywords = "";
        for (String string : list) {
            // string cannot be empty
            if (string.length() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            keywords = keywords + " " + string;
        }
        return keywords.trim().split(regex);
    }
}
//@@author
