package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FindCommand.COMMAND_WORD_NAME_PREFIX;
import static seedu.address.logic.commands.FindCommand.COMMAND_WORD_TAG_PREFIX;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.place.NameContainsKeywordsPredicate;
import seedu.address.model.place.TagContainsKeywordsPredicate;

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
        //@@author huyuanrong
        final Pattern prefixFormat = Pattern.compile("(?<prefix>\\w/)(?<arguments>.*)");
        final Matcher prefixMatcher = prefixFormat.matcher(trimmedArgs);

        if (!prefixMatcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        /* Group the prefixes together and the arguments together */
        final String prefix = prefixMatcher.group("prefix");
        final String arguments = prefixMatcher.group("arguments");

        String[] keywords = arguments.split("\\s+");

        switch (prefix) {

        case COMMAND_WORD_NAME_PREFIX:
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        case COMMAND_WORD_TAG_PREFIX:
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        //@@author

    }

}
