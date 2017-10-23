package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PostalCodeTest {

    @Test
    public void isValidPostalCode() {
        // invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("^")); // only non-alphanumeric characters
        assertFalse(PostalCode.isValidPostalCode("St. Garcia")); // contains non-alphanumeric characters

        // valid postal codes
        assertTrue(PostalCode.isValidPostalCode("mexico city")); // alphabets only
        assertTrue(PostalCode.isValidPostalCode("12345")); // numbers only
        assertTrue(PostalCode.isValidPostalCode("Singapore 439099")); // alphanumeric characters
        assertTrue(PostalCode.isValidPostalCode("Singapore")); // with capital letters
        assertTrue(PostalCode.isValidPostalCode("St Vincent and the Grenadines")); // long names
    }

}
