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
        try {
            ArrayList<Index> indexArrayList = new ArrayList<Index>();
            String[] indexArray = args.split(" ");
            // if the first element in the array is an empty string, do not parse it
            if ("".equals(indexArray[0])) {
                // start looping from i = 1 as the first element in the array is an empty string
                for (int i = 1; i < indexArray.length; i++) {
                    if (!" ".equals(indexArray[i]) && !"".equals(indexArray[i])) {
                        Index index = ParserUtil.parseIndex(indexArray[i]);
                        indexArrayList.add(index);
                    }
                }
            } else {
                // otherwise parse the first element
                for (String s : indexArray) {
                    if (!"".equals(s) && !"".equals(s)) {
                        Index index = ParserUtil.parseIndex(s);
                        indexArrayList.add(index);
                    }
                }
            }
            return new DeleteCommand(indexArrayList);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
