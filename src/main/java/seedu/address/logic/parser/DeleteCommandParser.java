package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private static final String SPACE = " ";

    //@@author liliwei25
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index[] index = getIndexs(args);
            return new DeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses Indexs from the given user input
     *
     * @param input User input
     * @return Index array of parsed Indexs
     * @throws IllegalValueException If any of the inputs are not positive integers
     */
    private Index[] getIndexs(String input) throws IllegalValueException {
        String[] arguments = input.trim().split(SPACE);
        Index[] index = new Index[arguments.length];
        int count = 0;
        for (String e: arguments) {
            index[count++] = ParserUtil.parseIndex(e);
        }
        return index;
    }
    //@@author
}
