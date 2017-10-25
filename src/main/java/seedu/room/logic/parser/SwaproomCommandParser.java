package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.commons.core.index.Index;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.SwaproomCommand;
import seedu.room.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class SwaproomCommandParser implements Parser<SwaproomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwaproomCommand parse(String args) throws ParseException {
        try {
            String[] indexes = args.split("\\s+");

            if (indexes.length != 3) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwaproomCommand.MESSAGE_USAGE));
            }

            Index index1 = ParserUtil.parseIndex(indexes[1]);
            Index index2 = ParserUtil.parseIndex(indexes[2]);
            return new SwaproomCommand(index1, index2);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwaproomCommand.MESSAGE_USAGE));
        }
    }

}
