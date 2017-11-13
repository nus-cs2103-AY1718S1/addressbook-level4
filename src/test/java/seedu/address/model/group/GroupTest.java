package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author tingtx
public class GroupTest {
    @Test
    public void isValidGroup() {

        //valid group
        assertTrue(Group.isValidGroup(" ")); //blank group
        assertTrue(Group.isValidGroup("")); //empty group
        assertTrue(Group.isValidGroup("012345678901234")); // fifteen characters
        assertTrue(Group.isValidGroup("012345678901234567890123456789")); // thirty characters
        assertTrue(Group.isValidGroup("A*&_)><.?/1 ")); //any characters

        //invalid group
        assertFalse(Group.isValidGroup("0123456789012345678901234567890")); //thirty-one characters
        assertFalse(Group.isValidGroup("01234567890123456789012345678901")); //thirty-one characters
    }
}
