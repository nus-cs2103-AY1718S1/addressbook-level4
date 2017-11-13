package seedu.address.email.message;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.mail.internet.InternetAddress;

import org.junit.Test;

//@@author awarenessxz
public class MessageDraftTest {

    private MessageDraft message;

    @Test
    public void containsContent() {
        //blank message and subject
        message = new MessageDraft();
        assertFalse(message.containsContent());

        //blank message only
        message.setSubject("new subject");
        assertFalse(message.containsContent());

        //blank subject only
        message = new MessageDraft();
        message.setMessage("new Message");
        assertFalse(message.containsContent());

        //both message and subject are not blank
        message = new MessageDraft();
        message.setSubject("new subject");
        message.setMessage("new Message");
        assertTrue(message.containsContent());
    }

    @Test
    public void equals() {
        final MessageDraft standardMessageDraft = new MessageDraft("Hello", "Subject");
        InternetAddress[] recipientsEmail = new InternetAddress[2];
        try {
            recipientsEmail[0] = new InternetAddress("ben@gmail.com");
            recipientsEmail[1] = new InternetAddress("adam@gmail.com");
            standardMessageDraft.setRecipientsEmail(recipientsEmail);
        } catch (Exception e) {
            assert false : "The internet address should be valid";
        }

        MessageDraft message;

        //no values
        assertFalse(standardMessageDraft.equals(new MessageDraft()));

        //only message same
        message = new MessageDraft("Hello", "");
        assertFalse(standardMessageDraft.equals(message));

        //only subject same
        message = new MessageDraft("", "Subject");
        assertFalse(standardMessageDraft.equals(message));

        //only recipients list same
        message = new MessageDraft();
        message.setRecipientsEmail(recipientsEmail);
        assertFalse(standardMessageDraft.equals(message));

        //only message and subject same
        message = new MessageDraft("Hello", "Subject");
        assertFalse(standardMessageDraft.equals(message));

        //only message and recipients list same
        message = new MessageDraft("Hello", "");
        message.setRecipientsEmail(recipientsEmail);
        assertFalse(standardMessageDraft.equals(message));

        //only subject and recipients list same
        message = new MessageDraft("", "Subject");
        message.setRecipientsEmail(recipientsEmail);
        assertFalse(standardMessageDraft.equals(message));

        //message, subject and recipients list same
        message = new MessageDraft("Hello", "Subject");
        message.setRecipientsEmail(recipientsEmail);
        assertTrue(standardMessageDraft.equals(message));

        //message, subject and recipients list all wrong
        message = new MessageDraft("ELLO", "ubject");
        try {
            recipientsEmail[0] = new InternetAddress("james@gmail.com");
            recipientsEmail[1] = new InternetAddress("alice@gmail.com");
            message.setRecipientsEmail(recipientsEmail);
        } catch (Exception e) {
            assert false : "The internet address should be valid";
        }
        assertFalse(standardMessageDraft.equals(message));
    }
}
