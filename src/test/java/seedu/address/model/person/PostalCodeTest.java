package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author limcel
public class PostalCodeTest {
    @Test
    public void isValidPostalCode() {
        // invalid PostalCodes
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("6239029")); // numbers that are not exactly 6 digits
        assertFalse(PostalCode.isValidPostalCode("!@@#$#@$#")); // contains random symbols
        assertFalse(PostalCode.isValidPostalCode("97jhb9")); // contains alphanumeric numbers
        assertFalse(PostalCode.isValidPostalCode("gfxgfx")); // contains purely alphabets

        // valid PostalCodes
        assertTrue(PostalCode.isValidPostalCode("672943")); // PostalCode number is exactly 6 numbers
    }
}
