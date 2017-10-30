# Jemereny
###### \java\seedu\address\model\person\PictureTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PictureTest {

    private static final String DEFAULT_PICTURE_LOCATION = "src/main/resources/images/";

    @Test
    public void isValidPicture() {
        // invalid pictures
        assertFalse(Picture.isValidPicture("default_profile"));
        assertFalse(Picture.isValidPicture(DEFAULT_PICTURE_LOCATION + "default_profile.jpg"));

        // valid pictures
        assertTrue(Picture.isValidPicture(null));
        assertTrue(Picture.isValidPicture(DEFAULT_PICTURE_LOCATION + Picture.DEFAULT_PICTURE));
    }
}
```
###### \java\seedu\address\model\person\WebsiteTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        // invalid Websites
        assertFalse(Website.isValidWebsite("")); // empty string
        assertFalse(Website.isValidWebsite(" ")); // spaces only
        assertFalse(Website.isValidWebsite("www.yahoo.com")); // no http protocol
        assertFalse(Website.isValidWebsite("http://YAHOO.com")); // non-numeric
        assertFalse(Website.isValidWebsite("http://www.YAHOO.com")); // lower-case
        assertFalse(Website.isValidWebsite("9312 1534")); // no digits

        // valid Websites
        assertTrue(Website.isValidWebsite(null));
        assertTrue(Website.isValidWebsite("http://www.yahoo.com")); // empty string
        assertTrue(Website.isValidWebsite("https://www.yahoo.com")); // exactly 3 numbers
        assertTrue(Website.isValidWebsite("https://ivle.nus.edu.sg")); // multiple domains
    }
}
```
