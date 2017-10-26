package seedu.room.logic.parser;


import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.logic.commands.RemoveTagCommand;
import seedu.room.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagParser implements Parser<RemoveTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
        return new RemoveTagCommand(trimmedArgs);
    }
}
