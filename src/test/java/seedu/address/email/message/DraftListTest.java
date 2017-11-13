package seedu.address.email.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author awarenessxz
public class DraftListTest {
    private DraftList draftList = new DraftList();

    @Test
    public void composeEmail() {
        MessageDraft message;

        //Empty Message Draft
        message = new MessageDraft();
        draftList.composeEmail(message);
        assertEquals(message, draftList.getMessage(0));

        //Draft with only message filed
        message = new MessageDraft();
        message.setMessage("HELLO");
        draftList.composeEmail(message);
        assertEquals(message, draftList.getMessage(0));

        //Draft with only subject filled
        message = new MessageDraft();
        message.setSubject("SUBJECT");
        draftList.composeEmail(message);
        assertEquals(message, draftList.getMessage(0));

        //Draft with both message and email filled
        message = new MessageDraft("message", "subject");
        draftList.composeEmail(message);
        assertEquals(message, draftList.getMessage(0));

    }

    @Test
    public void equals() {
        //Set up expected EmailCompose
        DraftList standardDraftList = new DraftList();
        MessageDraft message = new MessageDraft("adam@gmail.com", "password");
        standardDraftList.composeEmail(message);

        //same values --> returns true
        DraftList draftList = new DraftList();
        draftList.composeEmail(message);
        assertTrue(standardDraftList.equals(draftList));

        //same object --> returns true
        assertTrue(standardDraftList.equals(standardDraftList));

        //null --> returns false
        assertFalse(standardDraftList.equals(null));

        //different type --> return false
        assertFalse(standardDraftList.equals(5));

        //different message --> return false
        draftList = new DraftList();
        draftList.composeEmail(new MessageDraft("sam@gmail.com", "password"));
        assertFalse(standardDraftList.equals(draftList));

        //different subject --> return false
        draftList = new DraftList();
        draftList.composeEmail(new MessageDraft("adam@gmail.com", "password123"));
        assertFalse(standardDraftList.equals(draftList));
    }
}
