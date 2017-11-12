package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

//@@author danielweide
/**
 * Test For QrCallCommand for Equal Cases
 */
public class QrGenSaveContactCommandTest {
    private String phoneNumberOne;
    private String phoneNumberTwo;
    private String nameOne;
    private String nameTwo;
    private String emailOne;
    private String emailTwo;
    @Before
    public void prepareCommand() {
        phoneNumberOne = "91234567";
        phoneNumberTwo = "81234567";
        nameOne = "Daniel";
        nameTwo = "John";
        emailOne = "Daniel@gmail.com";
        emailTwo = "John@gmail.com";
    }
    @Test
    public void linkCheck() {
        QrGenSaveContactCommand genSaveContactCommand = new QrGenSaveContactCommand();
        String firstQrLink = genSaveContactCommand.qrSaveContact(phoneNumberOne, nameOne, emailOne);
        String secondQrLink = genSaveContactCommand.qrSaveContact(phoneNumberTwo, nameTwo, emailTwo);

        // same object -> returns true
        assertTrue(firstQrLink.equals(firstQrLink));

        // same values -> returns true
        String firstQrLinkCopy = genSaveContactCommand.qrSaveContact(phoneNumberOne, nameOne, emailOne);
        assertTrue(firstQrLink.equals(firstQrLinkCopy));

        // different types -> returns false
        assertFalse(firstQrLink.equals(1));

        // null -> returns false
        assertFalse(firstQrLink.equals(null));

        // different link -> returns false
        assertFalse(firstQrLink.equals(secondQrLink));
    }
}
