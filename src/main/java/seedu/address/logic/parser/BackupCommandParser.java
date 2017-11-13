package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for backup command and returns a BackupCommand instance. Arguments should consist of only one
 * filepath.
 */
//@@author charlesgoh
public class BackupCommandParser implements Parser<BackupCommand> {

    public static final int SIZE_OF_ARG_ARRAY = 1;

    /**
     * Parses the given user input and backs up data into a separate file name.
     * @param userInput
     * @throws ParseException
     */
    public BackupCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
        }

        // Converts arg arrays to lower case to account for caps entries
        String[] argKeywords = trimmedArgs.split("\\s");

        if (argKeywords.length != SIZE_OF_ARG_ARRAY) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
        }

        return new BackupCommand(trimmedArgs);
    }
}
