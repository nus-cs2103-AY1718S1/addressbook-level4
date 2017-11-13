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

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    //@@author Procrastinatus
    public DeleteCommand parse(String args) throws ParseException {
        try {
            String[] arguments = args.trim().split(" ");
            if (arguments.length == 1) {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } else {
                Index[] indexes = new Index[arguments.length];
                for (int i = 0; i < indexes.length; i++) {
                    indexes[i] = ParserUtil.parseIndex(arguments[i]);
                }
                return new DeleteCommand(indexes);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
