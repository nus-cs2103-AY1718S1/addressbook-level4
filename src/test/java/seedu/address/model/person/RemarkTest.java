package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    //@@author Gideonfu
    @Test
    public void isValidRemark() {
        // invalid addresses
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(" ")); // spaces only

        // valid addresses
        assertTrue(Remark.isValidRemark("owe $10"));
        assertTrue(Remark.isValidRemark("~")); // one character
        assertTrue(Remark.isValidRemark("Meet for lunch 21/10 6pm Nex")); // long remark
    }
}
