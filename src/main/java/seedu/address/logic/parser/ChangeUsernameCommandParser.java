package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeUsernameCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */
public class ChangeUsernameCommandParser implements Parser<ChangeUsernameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand
     * and returns an ChangePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeUsernameCommand parse(String args) throws ParseException {
        try {
            String[] commandTokenized = args.split(" ");
            // Correct Format: changeuser old_username new_username password
            if (commandTokenized.length != 4) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangeUsernameCommand.MESSAGE_USAGE));
            } else {
                return new ChangeUsernameCommand(commandTokenized[1], commandTokenized[2], commandTokenized[3]);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeUsernameCommand.MESSAGE_USAGE));
        }
    }
}
