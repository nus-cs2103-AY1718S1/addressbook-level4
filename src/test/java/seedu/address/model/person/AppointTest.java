package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author risashindo7
public class AppointTest {

    @Test
    public void equals() {
        Appoint appoint = new Appoint("20/12/2018 13:30");

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
        Appoint differentAppoint = new Appoint("20/11/2018 13:30");
        assertFalse(appoint.equals(differentAppoint));
    }
}
//@@author
