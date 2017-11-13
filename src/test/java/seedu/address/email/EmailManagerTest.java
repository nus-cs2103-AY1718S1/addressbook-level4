package seedu.address.email;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.email.exceptions.EmailLoginInvalidException;
import seedu.address.email.message.MessageDraft;

//@@author awarenessxz
public class EmailManagerTest {

    private Email email = new EmailManager();

    @Test
    public void getEmailDraft() {
        MessageDraft message = new MessageDraft("message", "subject");
        email.composeEmail(message);

        //getEmailDraft should be equal
        assertTrue(message.equals(email.getEmailDraft()));
    }

    @Test
    public void isUserLogin() {
        //user is not login -> returns false
        assertFalse(email.isUserLogin());

        //user is login -> returns true
        String [] loginDetails = {"adam@gmail.com", "password"};
        try {
            email.loginEmail(loginDetails);
            assertTrue(email.isUserLogin());
        } catch (EmailLoginInvalidException e) {
            assert false : "shouldn't hit this at all";
        }
    }

    @Test
    public void equals() {
        try {
            //Set up expected Email
            Email standardEmail = new EmailManager();
            MessageDraft message = new MessageDraft("Hello", "subject");
            String[] loginDetails = {"adam@gmail.com", "password"};
            standardEmail.loginEmail(loginDetails);
            standardEmail.composeEmail(message);

            //same values --> returns true
            email = new EmailManager();
            email.composeEmail(message);
            email.loginEmail(loginDetails);
            assertTrue(standardEmail.equals(email));

            //same object --> returns true
            assertTrue(standardEmail.equals(standardEmail));

            //null --> returns false
            assertFalse(standardEmail.equals(null));

            //different type --> return false
            assertFalse(standardEmail.equals(5));

            //different message --> return false
            email = new EmailManager();
            email.composeEmail(new MessageDraft());
            email.loginEmail(loginDetails);
            assertFalse(standardEmail.equals(email));

            //different login --> return false
            email.composeEmail(message);
            String[] loginDetails2 = {"bernice@gmail.com", "password"};
            email.loginEmail(loginDetails2);
            assertFalse(standardEmail.equals(email));

        } catch (EmailLoginInvalidException e) {
            assert false : "shouldn't hit this test case";
        }

    }
}
