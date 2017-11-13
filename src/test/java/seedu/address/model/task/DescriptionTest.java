package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    //@@author chairz
    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string

        // valid description
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("location")); // alphabets only
        assertTrue(Description.isValidDescription("507020")); // numbers only
        assertTrue(Description.isValidDescription("159W Jalan Loyang Besar")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Prepare a birthday cake! :)")); // with non-alphanumeric characters

    }
}
