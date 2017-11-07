package seedu.address.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


//@@author aungmyin23
public class PostalCodeTest {

    @Test
    public void isValidPostalCode() {
        // invalid postal code
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("91")); // less than 3 numbers
        assertFalse(PostalCode.isValidPostalCode("phone")); // non-numeric
        assertFalse(PostalCode.isValidPostalCode("9011p041")); // alphabets within digits
        assertFalse(PostalCode.isValidPostalCode("9312 1534")); // spaces within digits
        assertFalse(PostalCode.isValidPostalCode("911")); // exactly 3 number

        // valid postal code
        assertTrue(PostalCode.isValidPostalCode("931215"));
    }
}
