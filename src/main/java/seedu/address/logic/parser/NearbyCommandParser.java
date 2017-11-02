package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NearbyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author khooroko
/**
 * Parses input arguments and creates a new NearbyCommand object
 */
public class NearbyCommandParser implements Parser<NearbyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NearbyCommand
     * and returns an NearbyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NearbyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new NearbyCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NearbyCommand.MESSAGE_USAGE));
        }
    }
}
