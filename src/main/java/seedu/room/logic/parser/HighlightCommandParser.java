package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.logic.commands.HighlightCommand;
import seedu.room.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class HighlightCommandParser implements Parser<HighlightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HighlightCommand parse(String args) throws ParseException {
        String highlightTag = args.trim();
        if (validTag(highlightTag)) {
            System.out.println("Highlight tag: " + highlightTag);
            return new HighlightCommand(highlightTag);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HighlightCommand.MESSAGE_USAGE));
        }
    }

    private boolean validTag(String highlightTag) {
        return !highlightTag.isEmpty();
    }
}

