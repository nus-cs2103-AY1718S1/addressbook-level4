package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DirCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author Chng-Zhi-Xuan
/**
 * Parses input arguments and creates a new DirCommand object
 */
public class DirCommandParser implements Parser<DirCommand> {

    private final int indexFirst = 0;
    private final int indexSecond = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the DirCommand
     * and returns an DirCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DirCommand parse(String args) throws ParseException {

        try {
            Index fromIndex = ParserUtil.parseIndexFromPosition(args, indexFirst);
            Index toIndex = ParserUtil.parseIndexFromPosition(args, indexSecond);
            return new DirCommand(fromIndex, toIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirCommand.MESSAGE_USAGE));
        }
    }
}
