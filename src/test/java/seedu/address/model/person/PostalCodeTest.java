package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PostalCodeTest {

    //@@author khooroko
    @Test
    public void isValidPostalCode() {
        // invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("91")); // less than 6 numbers
        assertFalse(PostalCode.isValidPostalCode("9999999")); // more than 6 numbers
        assertFalse(PostalCode.isValidPostalCode("postalcode")); // non-numeric
        assertFalse(PostalCode.isValidPostalCode("90p041")); // alphabets within digits
        assertFalse(PostalCode.isValidPostalCode("9312 34")); // spaces within digits

        // valid postal codes
        assertTrue(PostalCode.isValidPostalCode("000000"));
    }
}
