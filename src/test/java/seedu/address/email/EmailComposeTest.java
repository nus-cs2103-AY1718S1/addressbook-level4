package seedu.address.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.email.message.MessageDraft;

//@@author awarenessxz
public class EmailComposeTest {
    private EmailCompose emailCompose = new EmailCompose();

    @Test
    public void resetData() {
        //Creates standard EmailCompose class
        MessageDraft message = new MessageDraft("message", "subject");
        emailCompose.composeEmail(message);
        EmailCompose standardEmailCompose = new EmailCompose();
        standardEmailCompose.composeEmail(message);

        //both object should be same
        assertTrue(standardEmailCompose.equals(emailCompose));

        //after reset, object should be different
        emailCompose.resetData();
        assertFalse(standardEmailCompose.equals(emailCompose));
    }

    @Test
    public void composeEmail() {
        MessageDraft message;

        //Empty Message Draft
        message = new MessageDraft();
        emailCompose.composeEmail(message);
        assertEquals(message, emailCompose.getMessage());

        //Draft with only message filed
        message = new MessageDraft();
        message.setMessage("HELLO");
        emailCompose.composeEmail(message);
        assertEquals(message, emailCompose.getMessage());

        //Draft with only subject filled
        message = new MessageDraft();
        message.setSubject("SUBJECT");
        emailCompose.composeEmail(message);
        assertEquals(message, emailCompose.getMessage());

        //Draft with both message and email filled
        message = new MessageDraft("message", "subject");
        emailCompose.composeEmail(message);
        assertEquals(message, emailCompose.getMessage());

    }

    @Test
    public void equals() {
        //Set up expected EmailCompose
        EmailCompose standardEmailCompose = new EmailCompose();
        MessageDraft message = new MessageDraft("adam@gmail.com", "password");
        standardEmailCompose.composeEmail(message);

        //same values --> returns true
        EmailCompose emailCompose = new EmailCompose();
        emailCompose.composeEmail(message);
        assertTrue(standardEmailCompose.equals(emailCompose));

        //same object --> returns true
        assertTrue(standardEmailCompose.equals(standardEmailCompose));

        //null --> returns false
        assertFalse(standardEmailCompose.equals(null));

        //different type --> return false
        assertFalse(standardEmailCompose.equals(5));

        //different message --> return false
        emailCompose = new EmailCompose();
        emailCompose.composeEmail(new MessageDraft("sam@gmail.com", "password"));
        assertFalse(standardEmailCompose.equals(emailCompose));

        //different subject --> return false
        emailCompose = new EmailCompose();
        emailCompose.composeEmail(new MessageDraft("adam@gmail.com", "password123"));
        assertFalse(standardEmailCompose.equals(emailCompose));

    }
}
