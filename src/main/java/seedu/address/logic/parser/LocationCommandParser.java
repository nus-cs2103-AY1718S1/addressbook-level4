package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.LocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
//@@author sidhmads
public class LocationCommandParser implements Parser<LocationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocationCommand
     * and returns an LocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LocationCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
        }
        String[] indexKeywords = trimmedArgs.split("\\s+");
        return new LocationCommand(Arrays.asList(indexKeywords));

    }

}
