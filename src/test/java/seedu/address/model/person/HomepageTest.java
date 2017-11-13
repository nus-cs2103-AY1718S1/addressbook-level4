package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author karrui
public class HomepageTest {

    @Test
    public void isValidHomepage() {
        // invalid homepages
        assertFalse(Homepage.isValidHomepage("")); // empty string
        assertFalse(Homepage.isValidHomepage(" ")); // spaces only
        assertFalse(Homepage.isValidHomepage("www.google.com")); // no "http://" prefix

        // valid homepages
        assertTrue(Homepage.isValidHomepage("http://www.google.com"));
        assertTrue(Homepage.isValidHomepage("http://w")); // one character
    }
}
