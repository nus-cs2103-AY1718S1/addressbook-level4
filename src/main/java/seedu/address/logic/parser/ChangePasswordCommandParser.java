package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */
public class ChangePasswordCommandParser implements Parser<ChangePasswordCommand> {

    public ChangePasswordCommand parse(String args) throws ParseException {
        try {
            String[] commandTokenized = args.split(" ");
            // Correct Format: changepw username old_password new_password
            if (commandTokenized.length != 4) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));
            } else {
                return new ChangePasswordCommand(commandTokenized[1], commandTokenized[2], commandTokenized[3]);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));
        }
    }
}
