package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GroupTest {

    //@@author grantcm
    @Test
    public void isValidGroup () {
        //invalid groupName
        assertFalse(Group.isValidGroup("ab as"));
        assertFalse(Group.isValidGroup(" ab"));
        assertFalse(Group.isValidGroup(" ab ab"));
        assertFalse(Group.isValidGroup("ab ab ab "));
        assertFalse(Group.isValidGroup(" ab  "));

        //valid groupName
        assertTrue(Group.isValidGroup("ab"));
        assertTrue(Group.isValidGroup("Trip"));

    }
    //@@author
}
