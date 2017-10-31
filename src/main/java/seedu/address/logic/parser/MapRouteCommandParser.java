package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MapRouteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author dalessr
/**
 * Parses input arguments and creates a new MapRouteCommand object
 */
public class MapRouteCommandParser implements Parser<MapRouteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MapRouteCommand
     * and returns an MapRouteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapRouteCommand parse(String args) throws ParseException {
        try {
            if (!args.contains(PREFIX_ADDRESS.getPrefix())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
            }
            String[] argsArray = args.trim().split(PREFIX_ADDRESS.getPrefix());
            if (argsArray.length <= 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
            }
            Index index = ParserUtil.parseIndex(argsArray[0].trim());
            return new MapRouteCommand(index, argsArray[1].trim());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
        }
    }
}
