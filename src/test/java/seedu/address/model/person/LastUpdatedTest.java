package seedu.address.model.person;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LastUpdatedTest {

    @Test
    public void isValidLastUpdated() {
        // invalid lastUpdated
        assertFalse(LastUpdated.isValidLastUpdated(null));

        //// valid lastUpdated
        assertTrue(LastUpdated.isValidLastUpdated("")); // Any string
        assertTrue(LastUpdated.isValidLastUpdated("123abc!@#")); // Any string
    }
}


