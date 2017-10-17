package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMultipleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteMultipleCommandParser implements Parser<DeleteMultipleCommand>{
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMultipleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMultipleCommand.MESSAGE_USAGE));
        }

        String[] listOfIndex = trimmedArgs.split("\\s+");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(listOfIndex));
        Collections.reverse(list);
        ArrayList<Index> arrayOfIndex = new ArrayList<Index>();
        
        for(int n = 0; n < list.size(); n++ ) {
            String indexString = list.get(n);
            int foo = Integer.parseInt(indexString) - 1;
            Index index = new Index(foo);
            arrayOfIndex.add(index);
        }

        return new DeleteMultipleCommand(arrayOfIndex);
    }
}
