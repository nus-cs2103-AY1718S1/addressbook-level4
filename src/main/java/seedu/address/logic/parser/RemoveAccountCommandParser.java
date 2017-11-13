package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.logic.commands.RemoveAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author derickjw
/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */

public class RemoveAccountCommandParser implements Parser<RemoveAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand
     * and returns an ChangePasswordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveAccountCommand parse(String args) throws ParseException {
        try {
            String[] commandTokenized = args.split(" ");
            if (commandTokenized.length != 3) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RemoveAccountCommand.MESSAGE_USAGE));
            } else {
                return new RemoveAccountCommand(commandTokenized[1], commandTokenized[2]);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }
    }
}
