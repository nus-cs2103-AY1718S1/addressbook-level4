package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ToggleAccessDisplayCommand.TYPE_OFF;
import static seedu.address.logic.commands.ToggleAccessDisplayCommand.TYPE_ON;

import seedu.address.logic.commands.ToggleAccessDisplayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Zzmobie
/**
 * Parses input arguments and creates a new ToggleAccessDisplayCommand object
 */
public class ToggleAccessDisplayCommandParser implements Parser<ToggleAccessDisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ToggleAccessDisplayCommand
     * and returns an ToggleAccessDisplayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ToggleAccessDisplayCommand parse(String args) throws ParseException {
        if (args.trim().equalsIgnoreCase(TYPE_ON)) {
            return new ToggleAccessDisplayCommand(true);
        } else if (args.trim().equalsIgnoreCase(TYPE_OFF)) {
            return new ToggleAccessDisplayCommand(false);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ToggleAccessDisplayCommand.MESSAGE_USAGE));
        }
    }

}
