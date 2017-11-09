package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT_SID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTH_TOKEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SENDING_NUMBER;

import java.util.logging.Level;
/**
 * Reauthenticates the Twilio Account Details & Sending Phone Number for the User
 *
 */
//@@author justuswah
public class ReauthenticateCommand extends Command {

    public static final String COMMAND_WORD = "reauthenticate";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": reauthenticates the Account SID, Authentication Token, and Sending Number. "
            + "Parameters: "
            + PREFIX_ACCOUNT_SID + "ACCOUNT_SID "
            + PREFIX_AUTH_TOKEN + "AUTHENTICATION_TOKEN "
            + PREFIX_SENDING_NUMBER + "SENDING_NUMBER. "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACCOUNT_SID + "ACed7baf2459e41d773a5f9c2232d4d975 "
            + PREFIX_AUTH_TOKEN + "6a26cc5c91ff355ebf48fe019700920b "
            + PREFIX_SENDING_NUMBER + "+12082157763";
    public static final String REAUTHENTICATE_SUCCESS = "Details Reauthenticated";

    private String accountSid;
    private String authenticationToken;
    private String sendingNumber;

    public ReauthenticateCommand(String accountSid, String authenticationToken, String sendingNumber) {
        this.accountSid = accountSid;
        this.authenticationToken = authenticationToken;
        this.sendingNumber = sendingNumber;
    }

    @Override
    public CommandResult execute() {
        SmsCommand.setAccountParticulars(accountSid, authenticationToken, sendingNumber);
        logger.log(Level.WARNING, REAUTHENTICATE_SUCCESS);
        return new CommandResult(REAUTHENTICATE_SUCCESS);
    }

}
