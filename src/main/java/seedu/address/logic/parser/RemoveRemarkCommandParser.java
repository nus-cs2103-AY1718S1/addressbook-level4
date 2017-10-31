package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveRemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveRemarkCommand object
 */
public class RemoveRemarkCommandParser implements Parser<RemoveRemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveRemarkCommand
     * and returns an RemoveRemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveRemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        Scanner scan;
        String checkedString;
        String tempString;
        int tempIndex = 0;
        try {
            List<Integer> integerList = new ArrayList<Integer>();
            scan = new Scanner(args);
            if (!scan.hasNextInt()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RemoveRemarkCommand.MESSAGE_USAGE));
            }
            index = Index.fromOneBased(scan.nextInt());
            while (scan.hasNext()) {
                tempString = scan.next();
                //Makes sure index accepted are integers
                if (!tempString.matches(".*\\d+.*")) {
                    throw new ParseException(String.format(MESSAGE_INVALID_REMARK_INDEX_FORMAT,
                            RemoveRemarkCommand.MESSAGE_USAGE));
                }
                tempIndex = Integer.parseInt(tempString);
                if (!integerList.contains(tempIndex)) { //makes sure there are no duplicate index in the list
                    integerList.add(tempIndex);
                }

            }

            ArrayList<Integer> remarkIndexArrayList = ParserUtil.parseIndexes(integerList);
            //Sort is used to make sure index parsed is always in descending order, so that when remarks are removed
            // in RemoveRemarkCommand, there will be no errors
            Collections.sort(remarkIndexArrayList, Collections.reverseOrder());
            return new RemoveRemarkCommand(index, remarkIndexArrayList);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRemarkCommand.MESSAGE_USAGE));
        }
    }
}
