package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectTaskCommand object
 */
public class SelectTaskCommandParser extends SelectCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectTaskCommand
     * and returns an SelectTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectTaskCommand parse(String args) throws ParseException {
        Index index = extractIndex(args);
        return new SelectTaskCommand(index);
    }

    /**
     * Extracts one index from the provided string and returns it
     * @throws ParseException if the string does not contain a valid index
     */
    private Index extractIndex(String args) throws ParseException {
        try {
            return ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE));
        }
    }
}
