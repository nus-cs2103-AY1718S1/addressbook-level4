package seedu.address.model.task;
//@@author Esilocke
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid descriptions
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription("alphanumerical contents")); // alphanumerical contents
        assertTrue(Description.isValidDescription("-")); // single character
    }
}
