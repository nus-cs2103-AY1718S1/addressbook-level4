//@@author PokkaKiyo
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FacebookAddressTest {

    @Test
    public void equals() {
        FacebookAddress facebookAddress = new FacebookAddress("https://www.facebook.com/somefacebookaddress/");

        // same object -> returns true
        assertTrue(facebookAddress.equals(facebookAddress));

        // same values -> returns true
        FacebookAddress facebookAddressCopy = new FacebookAddress(facebookAddress.value);
        assertTrue(facebookAddress.equals(facebookAddressCopy));

        // different types -> returns false
        assertFalse(facebookAddress.equals(1));

        // null -> returns false
        assertFalse(facebookAddress.equals(null));

        // different Facebook address -> returns false
        FacebookAddress differentFacebookAddress =
                new FacebookAddress("https://www.facebook.com/someOTHERfacebookaddress/");
        assertFalse(facebookAddress.equals(differentFacebookAddress));
    }
}
