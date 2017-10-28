package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        //blank website
        assertFalse(Website.isValidWebsite(""));
        assertFalse(Website.isValidWebsite(" "));

        //broken links with missing parts
        assertFalse(Website.isValidWebsite("wwwhelpcom"));
        assertFalse(Website.isValidWebsite("https://wwwworld"));
        assertFalse(Website.isValidWebsite("https://www.facebookcom"));
        assertFalse(Website.isValidWebsite(".com"));
        assertFalse(Website.isValidWebsite("https://.com"));
        assertFalse(Website.isValidWebsite("https//hadougen.com"));
        assertFalse(Website.isValidWebsite("https://hadougen."));
        assertFalse(Website.isValidWebsite("https://lamba.n"));

        //valid links
        assertTrue(Website.isValidWebsite("https://www.google.com"));
        assertTrue(Website.isValidWebsite("https://google.com"));
        assertTrue(Website.isValidWebsite("https://www.facebook.com/alex"));
        assertTrue(Website.isValidWebsite("https://www.facebook.com/search"));
        assertTrue(Website.isValidWebsite("https://www.google.net"));
        assertTrue(Website.isValidWebsite("https://www.linkedin.com"));
        assertTrue(Website.isValidWebsite("https://www.facebook.net"));
    }

    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check name field value
        Website websiteX = new Website("https://www.facebook.com/alex");
        Website websiteY = new Website("https://www.facebook.com/alex");
        assertTrue(websiteX.equals(websiteY) && websiteY.equals(websiteX));
        assertTrue(websiteX.hashCode() == websiteY.hashCode());
    }
}
