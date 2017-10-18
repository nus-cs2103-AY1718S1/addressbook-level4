package seedu.address.logic.parser;

import seedu.address.logic.commands.EmailLoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new EmailLoginCommand object
 */
public class EmailLoginParser implements  Parser<EmailLoginCommand> {


    @Override
    public EmailLoginCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String[] arguments = userInput.split("\"");

        if (arguments.length != 4) throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));

        String email = arguments[1];
        String password = arguments[3];

        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        }
        catch (AddressException e) {
            throw new ParseException(EmailLoginCommand.MESSAGE_INVALID_EMAIL);
        }

        return new EmailLoginCommand(email, password);
    }
}
