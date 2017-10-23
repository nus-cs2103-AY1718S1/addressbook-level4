package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StreetTest {

    @Test
    public void isValidStreet() {
        // invalid streets
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("^")); // only non-alphanumeric characters
        assertFalse(PostalCode.isValidPostalCode("St. Patrick's Road")); // contains non-alphanumeric characters

        // valid street
        assertTrue(PostalCode.isValidPostalCode("watermelon lane")); // alphabets only
        assertTrue(PostalCode.isValidPostalCode("12345")); // numbers only
        assertTrue(PostalCode.isValidPostalCode("23rd st")); // alphanumeric characters
        assertTrue(PostalCode.isValidPostalCode("Farquhar Lane")); // with capital letters
        assertTrue(PostalCode.isValidPostalCode("Riverside Village of Church Creek")); // long names
    }
}
