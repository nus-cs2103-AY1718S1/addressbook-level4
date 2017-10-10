package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WebsiteTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers

        assertFalse(Website.isValidWebsite(" ")); // spaces only
        assertFalse(Website.isValidWebsite("www.yahoo.com")); // no http protocol
        assertFalse(Website.isValidWebsite("http://YAHOO.com")); // non-numeric
        assertFalse(Website.isValidWebsite("http://www.YAHOO.com")); // lower-case
        assertFalse(Website.isValidWebsite("9312 1534")); // no digits

        // valid phone numbers
        assertTrue(Website.isValidWebsite("")); // empty string
        assertTrue(Website.isValidWebsite("http://www.yahoo.com")); // empty string
        assertTrue(Website.isValidWebsite("https://www.yahoo.com")); // exactly 3 numbers
        assertTrue(Website.isValidWebsite("https://ivle.nus.edu.sg")); // multiple domains
        assertTrue(Website.isValidWebsite("https://en.wikipedia.org/wiki/Main_Page")); // with suffix of folders
    }
}
