package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.HideCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class HideCommandParser implements Parser<HideCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HideCommand
     * and returns an HideCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HideCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new HideCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HideCommand.MESSAGE_USAGE));
        }
    }

}
