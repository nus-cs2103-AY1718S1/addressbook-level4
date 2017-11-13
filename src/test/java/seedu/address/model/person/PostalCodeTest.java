package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// @@author johnweikangong
public class PostalCodeTest {

    @Test
    public void isValidAddress() {
        // Invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("@^&")); // Special characters
        assertFalse(PostalCode.isValidPostalCode("12345678")); // More than 6 digits long
        assertFalse(PostalCode.isValidPostalCode("12345")); // Less than 6 digits long
        assertFalse(PostalCode.isValidPostalCode("915920")); // More than 800000

        // Valid postal code
        assertTrue(Address.isValidAddress("000000")); // Lower bound of postal code range
        assertTrue(Address.isValidAddress("450920"));
        assertTrue(Address.isValidAddress("800000")); // Upper bound of postal code range
    }
}
