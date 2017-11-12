package seedu.address.logic.parser;

import seedu.address.logic.commands.SetPathCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jaivigneshvenugopal
/**
 * Parses input arguments and creates a new {@code SetPathCommand} object
 */
public class SetPathCommandParser {

    public SetPathCommand parse(String args) throws ParseException {
        return new SetPathCommand(args.trim());
    }
}
