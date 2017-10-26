package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.EmailSendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EmailSendParser object
 */
public class EmailSendParser implements Parser<EmailSendCommand> {

    @Override
    public EmailSendCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String[] arguments = userInput.split("\"");

        if (arguments.length != 6) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailSendCommand.MESSAGE_USAGE));
        } else {
            String[] email = arguments[1].split(";");
            return new EmailSendCommand(email, arguments[3], arguments[5]);
        }
    }
}
