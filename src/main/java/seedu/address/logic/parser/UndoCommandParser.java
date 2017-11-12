package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.logic.parser.ParserUtil.SPACE_STRING;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author justintkj
/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    public static final int FIRST_PART_MESSAGE = 0;
    public static final int DEFAULT_CHOSEN_ONE = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {

        String[] splitArgs = args.trim().split(SPACE_STRING);

        int numUndo;
        try {
            numUndo = getNumberOfUndoToBeDone(splitArgs[FIRST_PART_MESSAGE]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE), ive);
        }

        return new UndoCommand(numUndo);
    }

    /**
     * Generates number of undo to be done
     *
     * @param splitArg Message given by user
     * @return Number of undo to be done
     * @throws IllegalValueException invalid number of undo to be done
     */
    private int getNumberOfUndoToBeDone(String splitArg) throws IllegalValueException {
        int numUndo;
        if (splitArg.trim().equals(EMPTY_STRING)) {
            numUndo = DEFAULT_CHOSEN_ONE;
        } else {
            numUndo = ParserUtil.parseNumber(splitArg);
        }
        return numUndo;
    }

}
