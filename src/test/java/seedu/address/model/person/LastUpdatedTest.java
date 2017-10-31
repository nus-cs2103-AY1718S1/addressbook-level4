package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author derrickchua
public class LastUpdatedTest {

    @Test
    public void isValidLastUpdated() {
        // invalid lastUpdated
        assertFalse(LastUpdated.isValidLastUpdated("")); // Any string
        assertFalse(LastUpdated.isValidLastUpdated("123abc!@#")); // Any string
        assertFalse(LastUpdated.isValidLastUpdated("2017-10-31T16:20:34Z"));
        assertFalse(LastUpdated.isValidLastUpdated("2017-10-31T16:20:34.9891211Z"));

        //valid lastUpdated
        assertTrue(LastUpdated.isValidLastUpdated("2017-10-31T16:20:34.989Z"));
        assertTrue(LastUpdated.isValidLastUpdated("2017-10-31T16:20:34.989121Z"));
    }
}


