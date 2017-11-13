package seedu.address.email;

import org.junit.Test;

import seedu.address.email.exceptions.EmailLoginInvalidException;
import seedu.address.email.exceptions.EmailMessageEmptyException;
import seedu.address.email.message.MessageDraft;

//@@author awarenessxz
public class EmailSendTest {
    private EmailSend emailSend = new EmailSend();

    @Test
    public void sendEmail() {
        EmailCompose emailCompose = new EmailCompose();
        EmailLogin emailLogin = new EmailLogin();

        //email draft does not have message and subject
        try {
            MessageDraft messageDraft = new MessageDraft();
            emailCompose.composeEmail(messageDraft);
            emailSend.sendEmail(emailCompose, emailLogin);
        } catch (EmailMessageEmptyException e) {
            assert true : "error was hit as expected";
        } catch (Exception e) {
            assert false : "error was hit unexpectedly";
        }

        //user is not logged in
        try {
            String [] loginDetails = {"adam@yahoo.com", "password"};
            emailLogin.loginEmail(loginDetails);
            emailSend.sendEmail(emailCompose, emailLogin);
        } catch (EmailLoginInvalidException e) {
            assert true : "error was hit as expected";
        } catch (Exception e) {
            assert false : "error was hit unexpectedly";
        }
    }
}
