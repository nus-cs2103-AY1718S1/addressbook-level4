package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ShowBookmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowBookmarkCommand object
 */
public class ShowBookmarkCommandParser implements Parser<ShowBookmarkCommand> {

    private final String emptyString = "";

    /**
     * Parses the given {@code String} of arguments in the context of the ShowBookmarkCommand
     * and returns an ShowBookmarkCommand object for execution.
     * @throws ParseException if the user provided any arguments
     */
    public ShowBookmarkCommand parse(String args) throws ParseException {

        if (!args.trim().equals(emptyString)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowBookmarkCommand.MESSAGE_USAGE));
        } else {
            return new ShowBookmarkCommand();
        }
    }

}
