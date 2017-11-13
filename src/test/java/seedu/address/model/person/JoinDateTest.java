package seedu.address.model.person;

//@@ author ReneeSeet

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JoinDateTest {

    @Test
    public void isValidJoinDate() {

        // invalid Join Date
        assertFalse(JoinDate.isValidDate("")); // empty string
        assertFalse(JoinDate.isValidDate(" ")); // spaces only
        assertFalse(JoinDate.isValidDate("41/10/2010")); //invalid day
        assertFalse(JoinDate.isValidDate("27/10/20100")); //invalid year
        assertFalse(JoinDate.isValidDate("27/13/2010")); //invalid month

        // valid Join Date
        assertTrue(JoinDate.isValidDate("27/10/2010"));
        assertTrue(JoinDate.isValidDate("27/10/1997"));
    }
}
