package seedu.address.externals;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

import com.google.api.services.gmail.Gmail;

import seedu.address.commons.core.Messages;
import seedu.address.external.GmailApi;

//@@author ReneeSeet-unused
/**
 * Contains tests for {@link GmailApi}
 * Note the verification of sending emails are done done via exploratory testing
 * Because it is diffcult for test to authenticate google account and send out an actual email via Gmail's API
 * testCase below is not run but this can help test if  google account has been pre autheticated.
 */

public class GmailApiTest {
    private GmailApi gmailApi;
    private static final String STUB_MESSAGE = "This is a stub message";
    private static final String STUB_EMAIL = "stub@gmail.com";
    private static final String STUB_SUBJECT = "test";

   @Test
    public void execute_Sending_email() throws IOException, MessagingException {
        gmailApi = new GmailApi();
        Gmail service = GmailApi.getGmailService();
        String user = "me";
        MimeMessage email = GmailApi.createEmail(STUB_EMAIL, user, STUB_SUBJECT , STUB_MESSAGE);
        GmailApi.sendMessage(service, user, email);
    }
}
