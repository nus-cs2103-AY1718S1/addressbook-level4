package seedu.address.logic.parser;

import seedu.address.logic.commands.EmailLoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static java.util.Objects.requireNonNull;

/**
 * Parses input arguments and creates a new EmailLoginCommand object
 */
public class EmailLoginParser implements  Parser<EmailLoginCommand> {


    @Override
    public EmailLoginCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String[] arguments = userInput.split(" ");

        String email = arguments[1];
        String password = arguments[2];

        try {
            InternetAddress emailAddress = new InternetAddress(email);
        }
        catch (AddressException e) {
            //email exception here
        }

        return new EmailLoginCommand(email, password);
    }
}
