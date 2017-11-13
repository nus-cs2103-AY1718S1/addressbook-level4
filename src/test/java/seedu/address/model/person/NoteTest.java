//@@author sebtsh
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoteTest {
    @Test
    public void isValidNote() {
        // invalid notes
        assertFalse(Note.isValidNote("")); // empty string
        assertFalse(Note.isValidNote(" ")); // spaces only

        // valid notes
        assertTrue(Note.isValidNote("Hates seafood"));
        assertTrue(Note.isValidNote("-")); // one character
        // long note
        assertTrue(Note.isValidNote("Sometimes likes ice cream but only the expensive kind not very sure"));
    }
}
