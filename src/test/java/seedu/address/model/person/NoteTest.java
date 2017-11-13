package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author derrickchua
public class NoteTest {

    @Test
    public void isValidNote() {
        // invalid note
        assertFalse(Note.isValidNote(null));

        //// valid note
        assertTrue(Note.isValidNote("")); // Any string
        assertTrue(Note.isValidNote("123abc!@#")); // Any string
    }
}


