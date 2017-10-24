package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.room.logic.commands.SortCommand;
import seedu.room.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {

        String sortCriteria = args.trim();

        if (!isValidField(sortCriteria)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        } else {
            return new SortCommand(sortCriteria);
        }
    }

    private static boolean isValidField(String sortCriteria) {
        Set<String> validFields = new HashSet<String>(Arrays.asList(
                new String[] {"name", "phone", "email", "room"}));
        return validFields.contains(sortCriteria);
    }

}
