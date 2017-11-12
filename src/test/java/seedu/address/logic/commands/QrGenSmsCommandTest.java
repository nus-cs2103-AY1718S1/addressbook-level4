package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

//@@author danielweide
/**
 * Test For QrCallCommand for Equal Cases
 */
public class QrGenSmsCommandTest {
    private String phoneNumberOne;
    private String phoneNumberTwo;
    private String nameOne;
    private String nameTwo;
    @Before
    public void prepareCommand() {
        phoneNumberOne = "91234567";
        phoneNumberTwo = "81234567";
        nameOne = "Daniel";
        nameTwo = "John";
    }
    @Test
    public void linkCheck() {
        QrGenSmsCommand genSmsCommand = new QrGenSmsCommand();
        String firstQrLink = genSmsCommand.qrSms(phoneNumberOne, nameOne);
        String secondQrLink = genSmsCommand.qrSms(phoneNumberTwo, nameTwo);

        // same object -> returns true
        assertTrue(firstQrLink.equals(firstQrLink));

        // same values -> returns true
        String firstQrLinkCopy = genSmsCommand.qrSms(phoneNumberOne, nameOne);
        assertTrue(firstQrLink.equals(firstQrLinkCopy));

        // different types -> returns false
        assertFalse(firstQrLink.equals(1));

        // null -> returns false
        assertFalse(firstQrLink.equals(null));

        // different link -> returns false
        assertFalse(firstQrLink.equals(secondQrLink));
    }
}
