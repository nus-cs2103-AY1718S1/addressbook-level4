package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author derrickchua

public class IdTest {

    @Test
    public void isValidId() {
        // invalid note
        assertFalse(Id.isValidId(null));

        //// valid note
        assertTrue(Id.isValidId("")); // Any string
        assertTrue(Id.isValidId("123abc!@#")); // Any string
    }
}


