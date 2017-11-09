//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        args = args.trim();

        if (args.isEmpty()) {
            return new UndoCommand();
        } else if (args.equals(" all")) {
            return new UndoCommand(Integer.MAX_VALUE);
        } else {
            try {
                int steps = ParserUtil.parseInt(args);
                return new UndoCommand(steps);
            } catch (IllegalValueException ive) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE);
            }
        }
    }
}
//@@author
