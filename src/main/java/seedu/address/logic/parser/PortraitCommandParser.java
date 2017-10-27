package seedu.address.logic.parser;

import seedu.address.logic.commands.PortraitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class PortraitCommandParser {

    public PortraitCommand parse(String args) throws ParseException {
        return new PortraitCommand();
    }
}
