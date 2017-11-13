package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectPersonCommand object
 */
public class SelectPersonCommandParser extends SelectCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectPersonCommand
     * and returns an SelectPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectPersonCommand parse(String args) throws ParseException {
        Index index = extractIndex(args);
        return new SelectPersonCommand(index);
    }

    /**
     * Extracts one index from the provided string and returns it
     * @throws ParseException if the string does not contain a valid index
     */
    private Index extractIndex(String args) throws ParseException {
        try {
            return ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectPersonCommand.MESSAGE_USAGE));
        }
    }
}
