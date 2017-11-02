package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.VisualizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author YuchenHe98
/**
 * Parses input arguments and creates a new VisualizeCommand object
 */
public class VisualizeCommandParser implements Parser<VisualizeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the VisualizeCommand
     * and returns an VisualizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VisualizeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new VisualizeCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VisualizeCommand.MESSAGE_USAGE));
        }
    }
}
