package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

//@@author danielweide
/**
 * Test For QrCallCommand for Equal Cases
 */
public class QrGenCallCommandTest {
    private String phoneNumberOne;
    private String phoneNumberTwo;
    @Before
    public void prepareCommand() {
        phoneNumberOne = "91234567";
        phoneNumberTwo = "81234567";
    }
    @Test
    public void linkCheck() {
        QrGenCallCommand genCallCommand = new QrGenCallCommand();
        String firstQrLink = genCallCommand.qrCall(phoneNumberOne);
        String secondQrLink = genCallCommand.qrCall(phoneNumberTwo);

        // same object -> returns true
        assertTrue(firstQrLink.equals(firstQrLink));

        // same values -> returns true
        String firstQrLinkCopy = genCallCommand.qrCall(phoneNumberOne);
        assertTrue(firstQrLink.equals(firstQrLinkCopy));

        // different types -> returns false
        assertFalse(firstQrLink.equals(1));

        // null -> returns false
        assertFalse(firstQrLink.equals(null));

        // different link -> returns false
        assertFalse(firstQrLink.equals(secondQrLink));
    }
}
