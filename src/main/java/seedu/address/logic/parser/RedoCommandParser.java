package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.logic.parser.ParserUtil.SPACE_STRING;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author justintkj
/**
* Parses input arguments and creates a new RedoCommand object
*/
public class RedoCommandParser implements Parser<RedoCommand> {

    public static final String NUMBER_ONE = "1";
    public static final int FIRST_PART_MESSAGE = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an RedoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        String[] splitArgs = args.trim().split(SPACE_STRING);

        int numRedo;
        try {
            numRedo = getNumberRedoToBeDone(splitArgs[FIRST_PART_MESSAGE]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE), ive);
        }

        return new RedoCommand(numRedo);
    }
    /**
     * Generates number of redo to be done
     *
     * @param splitArg Message given by user
     * @return Number of redo to be done
     * @throws IllegalValueException invalid number of redo to be done
     */
    private int getNumberRedoToBeDone(String splitArg) throws IllegalValueException {
        int numRedo;
        if (splitArg.trim().equals(EMPTY_STRING)) {
            numRedo = ParserUtil.parseNumber(NUMBER_ONE);
        } else {
            numRedo = ParserUtil.parseNumber(splitArg);
        }
        return numRedo;
    }

}
