package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIL_RECEPIENT;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Mails a person in Contags.
 */
public class MailCommand extends Command {

    public static final String COMMAND_WORD = "mail";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mails a contact in Contags.\n"
            + "Recepient mail address cannot be blank.\n"
            + "Parameters: " + PREFIX_MAIL_RECEPIENT + " INDEX"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MAIL_RECEPIENT + "1";

    public static final String MESSAGE_SUCCESS = "Redirect to Mail application success.";
    public static final String MESSAGE_FAILURE = "Could not redirect to Mail application. "
            + "Please enter a valid index.";

    private final Index targetIndex;

    public MailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Opens up Desktop Mail application.
     */

    public void sendMail(String sendMailTo) throws ParseException, IOException, URISyntaxException {

        String host = "localhost";

        Properties properties = System.getProperties();
        properties.setProperty("mail.sftp.host", host);

        Desktop desktop = Desktop.getDesktop();
        URI mailTo;
        String url = "";

        try {
            url = "mailTo:" + sendMailTo;
            mailTo = new URI(url);
            desktop.mail(mailTo);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String sendMailTo = lastShownList.get(targetIndex.getZeroBased()).getEmail().toString();

        try {
            sendMail(sendMailTo);
        } catch (ParseException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_FAILURE);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MailCommand // instanceof handles nulls
                && this.targetIndex.equals(((MailCommand) other).targetIndex)); // state check
    }

}
