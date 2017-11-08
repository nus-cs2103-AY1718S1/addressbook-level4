//@@author Hoang
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.EmailLoginCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.commands.exceptions.CommandException;

public class EmailLoginCommandTest {
    private EmailLoginCommand command;
    private Email emailManager = new EmailManager();

    //test for wrong email / password
    @Test
    public void wrongCredentials() throws CommandException {
        command = new EmailLoginCommand("phungtuanhoang1996@gmail.com", "thispasswordiswrong");
        command.setData(null, null, null, emailManager);
        CommandResult result = command.execute();
        assertEquals(EmailLoginCommand.MESSAGE_FAILED + EmailManager.MESSAGE_LOGIN_FAILED,
                result.feedbackToUser);
    }

    //test for correct email / password
    @Test
    public void correctCredentials() throws CommandException {
        String email = "cs2103testacc@gmail.com";
        command = new EmailLoginCommand(email, "testpass");
        command.setData(null, null, null, emailManager);
        CommandResult result = command.execute();
        assertEquals(MESSAGE_SUCCESS + email,
                result.feedbackToUser);
    }
}
//@@author Hoang
