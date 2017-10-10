package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PostalCodeTest {

    @Test
    public void isValidPostalCode() {
        // invalid name
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("AD")); // only numeric characters
        assertFalse(PostalCode.isValidPostalCode("00000")); // shorter than 6 digits
        assertFalse(PostalCode.isValidPostalCode("0000000")); // longer than 6 digits

        // valid name
        assertTrue(PostalCode.isValidPostalCode("000000")); // alphabets only
        assertTrue(PostalCode.isValidPostalCode("123456")); // numbers only
        assertTrue(PostalCode.isValidPostalCode("000845")); // alphanumeric characters
    }
}