package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.logic.commands.ImportCommand;
import seedu.room.logic.parser.exceptions.ParseException;

//@@author blackroxs
/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        return new ImportCommand(trimmedArgs);
    }
}
