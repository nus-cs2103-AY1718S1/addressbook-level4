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
//            Index index = ParserUtil.parseIndex(args);
//            return new DeleteCommand(index);
            System.out.println("para: *" + args + "*");



            ArrayList<Index> indexArrayList = new ArrayList<Index>();
            String[] indexArray = args.split(" ");
            // start looping from i = 1 as the first element in the array is an empty string
            System.out.println("indexArray length: " + indexArray.length);
            for (String s : indexArray) {
                System.out.println("arg: *" + s + "*");
            }

            // if the first element in the array is an empty string, do not parse it
            if (indexArray[0].equals("")) {
                System.out.println("here");
                for (int i = 1; i < indexArray.length; i++) {
                    if (!indexArray[i].equals(" ") && !indexArray[i].equals("")) {
//                        System.out.println("here");
                        Index index = ParserUtil.parseIndex(indexArray[i]);
                        indexArrayList.add(index);
                    }

//                    Index index = ParserUtil.parseIndex(indexArray[i]);
//                    indexArrayList.add(index);
                }
            } else {
                // otherwise parse the first element
                for (String s : indexArray) {
                    if (!s.equals(" ") && !s.equals("")) {
//                        System.out.println("here");
                        Index index = ParserUtil.parseIndex(s);
                        indexArrayList.add(index);
                    }

//                    Index index = ParserUtil.parseIndex(s);
//                    indexArrayList.add(index);
                }
            }
            System.out.println("here");

            for (Index i : indexArrayList) {
                System.out.println("i: " + i.getZeroBased());
            }


//            for (int i = 1; i < indexArray.length; i++) {
//                Index index = ParserUtil.parseIndex(indexArray[i]);
//                indexArrayList.add(index);
//            }
            return new DeleteCommand(indexArrayList);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
