package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.email.exceptions.LoginFailedException;
import seedu.address.logic.commands.exceptions.CommandException;

import static org.junit.Assert.*;

public class EmailSendCommandTest {
    private EmailSendCommand command;
    private Email emailManager = new EmailManager();

    //Test case of not logged in
    @Test
    public void notLoggedIn() throws CommandException {
        command = new EmailSendCommand(new String[] {"cs2103testacc@gmail.com"}, "Title", "Body");
        command.setData(null, null, null, emailManager);

        CommandResult result = command.execute();
        assertEquals("No email logged in", result.feedbackToUser);
    }

    //Test case of invalid recipients address
    @Test
    public void notValidRecipients() throws LoginFailedException, CommandException {
        //logged in email
        emailManager.login("cs2103testacc@gmail.com", "testpass");

        command = new EmailSendCommand(new String[] {"cs2103testacc;adffd"}, "Title", "Body");
        command.setData(null, null, null, emailManager);

        CommandResult result = command.execute();
        assertEquals("One or more of the given emails is not valid", result.feedbackToUser);
    }
}
