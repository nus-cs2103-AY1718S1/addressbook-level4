//@@author majunting
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.ParserUtil.parseIndex;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LocateCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new LocateCommand object
 */
public class LocateCommandParser implements Parser<LocateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocateCommand
     * and returns an LocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LocateCommand parse(String args) throws ParseException {
        try {
            String[] argArray = args.split(PREFIX_ADDRESS.getPrefix());
            if (argArray.length <= 1) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE);
            }
            Index index = parseIndex(argArray[0].trim());
            String address = argArray[1].trim();
            return new LocateCommand(index, address);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE);
        }
    }
}
