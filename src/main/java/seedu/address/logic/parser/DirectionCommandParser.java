package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DirectionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author Chng-Zhi-Xuan
/**
 * Parses input arguments and creates a new DirectionCommand object
 */
public class DirectionCommandParser implements Parser<DirectionCommand> {

    private final int indexFirst = 0;
    private final int indexSecond = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the DirectionCommand
     * and returns an DirectionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DirectionCommand parse(String args) throws ParseException {

        try {
            Index fromIndex = ParserUtil.parseIndexFromPosition(args, indexFirst);
            Index toIndex = ParserUtil.parseIndexFromPosition(args, indexSecond);

            if (fromIndex.getZeroBased() == toIndex.getZeroBased()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirectionCommand.MESSAGE_USAGE)
                );
            }

            return new DirectionCommand(fromIndex, toIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirectionCommand.MESSAGE_USAGE));
        }
    }
}
