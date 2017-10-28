package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import java.util.Arrays;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author jelneo
/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split(ONE_OR_MORE_SPACES_REGEX);
        for (String keyword : tagKeywords) {
            if (!Tag.isValidTagName(keyword)) {
                throw new ParseException(MESSAGE_TAG_CONSTRAINTS);
            }
        }

        return new FilterCommand(Arrays.asList(tagKeywords));
    }
}
