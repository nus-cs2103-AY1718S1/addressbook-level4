package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AppointTest {

    @Test
    public void equals() {
        Appoint appoint = new Appoint("Hello");

        // same object -> returns true
        assertTrue(appoint.equals(appoint));

        // same values -> returns true
        Appoint appointCopy = new Appoint(appoint.value);
        assertTrue(appoint.equals(appointCopy));

        // different types -> returns false
        assertFalse(appoint.equals(1));

        // null -> returns false
        assertFalse(appoint.equals(null));

        // different person -> returns false
        Appoint differentAppoint = new Appoint("Bye");
        assertFalse(appoint.equals(differentAppoint));
    }
}
