package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author JavynThun
public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        // blank email
        assertFalse(Website.isValidWebsite("")); // empty string
        assertFalse(Website.isValidWebsite(" ")); // spaces only

        // invalid email
        assertFalse(Website.isValidWebsite("https://example@example.com")); // special character in the middle

        // missing parts
        assertFalse(Website.isValidWebsite("https://example")); // missing top-level domain
        assertFalse(Website.isValidWebsite("http://example.com")); // missing 's' after http
        assertFalse(Website.isValidWebsite("https://.com")); // missing domain name
        assertFalse(Website.isValidWebsite("https//example.com")); // missing ':' after https

        // valid email
        assertTrue(Website.isValidWebsite("https://example.com"));
        assertTrue(Website.isValidWebsite("https://example.com.net"));  // multiple top-level domains
        assertTrue(Website.isValidWebsite("pexample.com"));
        assertTrue(Website.isValidWebsite("https://example.org"));
        assertTrue(Website.isValidWebsite("https://www.example.com"));
        assertTrue(Website.isValidWebsite("https://example.com/abcd"));
    }


}
