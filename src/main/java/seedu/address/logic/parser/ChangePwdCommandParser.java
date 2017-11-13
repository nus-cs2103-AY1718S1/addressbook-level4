package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangePwdCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author moomeowroar
/**
 * Parses input arguments and creates a new ChangePwdCommand object
 */
public class ChangePwdCommandParser implements Parser<ChangePwdCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an ChangePwdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePwdCommand parse(String args) throws ParseException {
        try {
            String[] array = ParserUtil.parseByDelimiter(args, " ");
            if (array.length == 2) {
                return new ChangePwdCommand("", array[1]);
            } else if (array.length == 3) {
                return new ChangePwdCommand(array[2], array[1]);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePwdCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePwdCommand.MESSAGE_USAGE));
        }
    }

}
