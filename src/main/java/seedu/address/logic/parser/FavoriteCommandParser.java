//@@author A0143832J
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavoriteCommand object
 */
public class FavoriteCommandParser implements Parser<FavoriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavoriteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavoriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavoriteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE);
        }
    }

}
//@@author
