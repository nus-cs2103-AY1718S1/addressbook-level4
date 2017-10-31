package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MapShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author dalessr
/**
 * Parses input arguments and creates a new MapShowCommand object
 */
public class MapShowCommandParser implements Parser<MapShowCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MapShowCommand
     * and returns an MapShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapShowCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MapShowCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapShowCommand.MESSAGE_USAGE));
        }
    }
}
