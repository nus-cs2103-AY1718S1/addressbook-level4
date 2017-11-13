package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CancelAppointmentCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author
/**
 * Parse input arguments and create a new CancelAppointmentCommand Object
 */
public class CancelAppointmentParser implements Parser {
    @Override
    public Command parse(String userInput) throws ParseException {
        if (!userInput.contains("with")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CancelAppointmentCommand.MESSAGE_USAGE));
        }
        String description = userInput.substring(0, userInput.indexOf("with") - 1);
        String personName = userInput.substring(userInput.indexOf("with") + 5);

        return new CancelAppointmentCommand(personName, description);
    }


}
