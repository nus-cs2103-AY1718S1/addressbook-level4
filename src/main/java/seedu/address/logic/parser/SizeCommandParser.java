// @@author donjar

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SizeCommand object
 */
public class SizeCommandParser implements Parser<SizeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SizeCommand
     * and returns an SizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SizeCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new SizeCommand();
        }

        try {
            int parsed = Integer.parseInt(args.trim());
            return new SizeCommand(parsed);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SizeCommand.MESSAGE_USAGE));
        }
    }
}
