package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BinrestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Pengyuz
/**
 * Parses input arguments and creates a new BinrestoreCommand object
 */
public class BinrestoreCommandParser implements Parser<BinrestoreCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BinrestoreCommand
     * and returns an BinrestoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BinrestoreCommand parse(String args) throws ParseException {
        ArrayList<Index> index;
        String arguments = args.trim();
        if (arguments.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinrestoreCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndexes(arguments);
            return new BinrestoreCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinrestoreCommand.MESSAGE_USAGE));
        }
    }

}
