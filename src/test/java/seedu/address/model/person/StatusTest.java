//@@author sebtsh
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {
    @Test
    public void isValidStatus() {
        // invalid statuses
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only

        // valid statuses
        assertTrue(Status.isValidStatus("Requires follow up"));
        assertTrue(Status.isValidStatus("-")); // one character
        // long status
        assertTrue(Status.isValidStatus("Was wondering if he wanted follow up or not but have not decided"));
    }
}
