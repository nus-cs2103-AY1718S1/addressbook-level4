package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Filters the given arguments and returns a FilterGroupCommand object
 */

public class FilterGroupCommandParser implements Parser<FilterGroupCommand> {

    @Override
    public FilterGroupCommand parse (String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterGroupCommand.MESSAGE_USAGE));
        }

        return new FilterGroupCommand(trimmedArgs);
    }
}
