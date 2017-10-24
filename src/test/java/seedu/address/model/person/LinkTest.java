package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LinkTest {

    @Test
    public void equals() {
        Link link = new Link("facebook.com");

        // same object -> returns true
        assertTrue(link.equals(link));

        // same values -> returns true
        Link linkCopy = new Link(link.value);
        assertTrue(link.equals(linkCopy));

        // different types -> returns false
        assertFalse(link.equals(1));

        // null -> returns false
        assertFalse(link.equals(null));

        // different person -> returns false
        Link differentLink = new Link("facebook.com/gg");
        assertFalse(link.equals(differentLink));
    }
}
