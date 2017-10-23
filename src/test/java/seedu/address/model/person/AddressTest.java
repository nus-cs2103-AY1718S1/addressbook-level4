package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddressTest {

    @Test
    public void hasValidAddressFormat() {
        // invalid address formats
        assertFalse(Address.hasValidAddressFormat("")); // empty string
        assertFalse(Address.hasValidAddressFormat(" ")); // no delimiter present
        assertFalse(Address.hasValidAddressFormat("22,fort road")); // less than 3 tokens
        assertFalse(Address.hasValidAddressFormat("22,fort road,,")); // 2 tokens with consecutive commas
        assertFalse(Address.hasValidAddressFormat(",,,")); // no characters between commas
        assertFalse(Address.hasValidAddressFormat("22,fort road,#08-01,Singapore,439099")); // more than 4 tokens

        // valid address formats
        assertTrue(Address.hasValidAddressFormat("Blk 456, Den Road, Singapore 409999")); // 3 tokens
        assertTrue(Address.hasValidAddressFormat("Blk 456, Den Road, #01-355, Singapore 409999")); // 4 tokens
        assertTrue(Address.hasValidAddressFormat(" , , , ")); // whitespaces between commas
        assertTrue(Address.hasValidAddressFormat("Leng Inc, 1234 Market St, San Francisco CA 2349879"));
    }
}
