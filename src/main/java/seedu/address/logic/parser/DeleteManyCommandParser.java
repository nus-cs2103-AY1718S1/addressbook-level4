package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteManyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteManyCommandParser implements Parser<DeleteManyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteManyCommand parse(String args) throws ParseException {
        try {
            ArrayList<Index> indexArrayList = new ArrayList<Index>();
            String[] indexArray = args.split(" ");
            System.out.println("args: " + args);
            System.out.println("indexArray.length: " + indexArray.length);
            for (int i = 1; i < indexArray.length; i++) {
                Index index = ParserUtil.parseIndex(indexArray[i]);
                indexArrayList.add(index);
            }
            return new DeleteManyCommand(indexArrayList);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteManyCommand.MESSAGE_USAGE));
        }
    }

}
