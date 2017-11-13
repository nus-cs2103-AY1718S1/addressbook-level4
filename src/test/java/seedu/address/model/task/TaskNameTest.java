package seedu.address.model.task;
//@@author Esilocke
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskNameTest {

    @Test
    public void isValidName() {
        // invalid names
        assertFalse(TaskName.isValidName(" ")); // spaces only
        assertFalse(TaskName.isValidName("")); // empty string

        // valid names
        assertTrue(TaskName.isValidName(".")); // single character
        assertTrue(TaskName.isValidName("buy pencil")); // alphanumerical with spaces
        assertTrue(TaskName.isValidName("buy pencil!")); // special symbols
    }
}
