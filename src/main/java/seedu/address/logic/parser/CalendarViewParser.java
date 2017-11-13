package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CalendarViewCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author Eric

/**
 * Parser for CalendarViewCommand
 */
public class CalendarViewParser implements Parser {


    @Override
    public Command parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        if (userInput.length() != 1 || !isValid(userInput)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarViewCommand.MESSAGE_USAGE));
        }
        return new CalendarViewCommand(userInput.charAt(0));
    }

    /**
     * Util method to check if the parameters is either w,d,y or m.
     */
    private boolean isValid(String str) {

        assert(str.length() == 1);
        switch (str.charAt(0)) {
        case('w'):
        case('d'):
        case('y'):
        case('m'):
            return true;
        default:
            return false;
        }
    }
}
