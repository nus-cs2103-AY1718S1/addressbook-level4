package seedu.address.logic.parser;

import seedu.address.logic.commands.EmailSendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;

public class EmailSendParser implements Parser<EmailSendCommand> {

    @Override
    public EmailSendCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String[] arguments = userInput.split("\"");

        if (arguments.length != 6) throw new ParseException(EmailSendCommand.MESSAGE_WRONG_ARGUMENTS);
        else {
            String[] email = arguments[1].split(";");
            return new EmailSendCommand(email, arguments[3], arguments[5]);
        }
    }
}
