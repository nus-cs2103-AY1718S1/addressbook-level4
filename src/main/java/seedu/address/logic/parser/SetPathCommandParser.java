package seedu.address.logic.parser;

import seedu.address.logic.commands.SetPathCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SetPathCommandParser {

    public SetPathCommand parse(String args) throws ParseException {
        return new SetPathCommand(args.trim());
    }
}
