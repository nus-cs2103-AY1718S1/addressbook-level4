package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.SwitchTabCommand;
import seedu.room.logic.parser.exceptions.ParseException;

//@@author sushinoya
/**
 * Parses input arguments and creates a new SwitchTabCommand object
 */
public class SwitchTabCommandParser implements Parser<SwitchTabCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchTabCommand
     * and returns an SwitchTabCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwitchTabCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseIndex(args).getOneBased();

            if (index > 2 || index < 1) {
                throw new IllegalValueException(MESSAGE_INVALID_COMMAND_FORMAT);
            }

            return new SwitchTabCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
        }
    }
}
