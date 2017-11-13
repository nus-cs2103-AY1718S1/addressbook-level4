package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ChangeWindowSizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.windowsize.WindowSize;

//@@author vivekscl
/**
 * Parses input arguments and creates a new ChangeWindowSizeCommand object
 */
public class ChangeWindowSizeCommandParser implements Parser<ChangeWindowSizeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeWindowSizeCommand
     * and returns an ChangeWindowSizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeWindowSizeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!WindowSize.isValidWindowSize(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeWindowSizeCommand.MESSAGE_USAGE));
        }

        return new ChangeWindowSizeCommand(args.trim());
    }
}
