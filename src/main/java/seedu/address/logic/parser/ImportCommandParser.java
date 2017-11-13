//@@author danielbrzn
package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();

        // unexpected input
        if (trimmedInput.contains(" ") || trimmedInput.length() == 0 || !trimmedInput.contains("google")) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ImportCommand.MESSAGE_USAGE));
        } else {
            return new ImportCommand(trimmedInput);
        }
    }
}
