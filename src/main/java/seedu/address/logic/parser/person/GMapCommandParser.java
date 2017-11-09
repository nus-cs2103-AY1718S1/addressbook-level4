package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.person.GMapCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.util.ParserUtil;

//@@author dennaloh
/**
 * Parses input arguments and creates a new GMapCommand object
 */
public class GMapCommandParser implements Parser<GMapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GMapCommand
     * and returns an GMapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GMapCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new GMapCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GMapCommand.MESSAGE_USAGE));
        }
    }
}
