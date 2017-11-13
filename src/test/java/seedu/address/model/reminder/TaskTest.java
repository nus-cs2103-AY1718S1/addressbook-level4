//@@author duyson98

package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskTest {

    @Test
    public void isValidTaskName() {
        // invalid task name
        assertFalse(Task.isValidTaskName("")); // empty string
        assertFalse(Task.isValidTaskName(" ")); // spaces only
        assertFalse(Task.isValidTaskName("^")); // only non-alphanumeric characters
        assertFalse(Task.isValidTaskName("birthday*")); // contains non-alphanumeric characters

        // valid task name
        assertTrue(Task.isValidTaskName("birthday")); // alphabets only
        assertTrue(Task.isValidTaskName("12345")); // numbers only
        assertTrue(Task.isValidTaskName("birthday 2morrow")); // alphanumeric characters
        assertTrue(Task.isValidTaskName("Birthday tomorrow")); // with capital letters
        assertTrue(Task.isValidTaskName("Tomorrow is my birthday")); // long task names
    }
}
