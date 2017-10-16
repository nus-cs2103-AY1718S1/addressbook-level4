package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

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
    public DeleteCommand parse(String args) throws ParseException {
        String invalidCommandString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        try {
            String trimmedArgs = args.trim();
            String[] indicesInString = trimmedArgs.split("\\s+");

            ArrayList<Index> indices = new ArrayList<>();
            for (int i = 0; i < indicesInString.length; i++) {
                Index index = ParserUtil.parseIndex(indicesInString[i]);

                // Check if there are repeated indices
                if (i >= 1) {
                    for (Index indexInList: indices) {
                        if (indexInList.equals(index)) {
                            throw new ParseException(invalidCommandString);
                        }
                    }
                }
                indices.add(index);
            }

            return new DeleteCommand(indices);
        } catch (IllegalValueException ive) {
            throw new ParseException(invalidCommandString);
        }
    }

}
