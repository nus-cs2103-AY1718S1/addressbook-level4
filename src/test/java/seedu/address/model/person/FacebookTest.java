package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FacebookTest {
    @Test
    public void isEqualFacebook() {

        // equal Facebook objects
        assertTrue(new Facebook("zuck").equals(new Facebook("zuck")));
        assertTrue(new Facebook("obama").equals(new Facebook("obama")));

        //unequal Facebook objects
        assertFalse(new Facebook("zuck").equals(new Facebook("galois")));
        assertFalse(new Facebook("galaois").equals(new Facebook("euler")));
    }
}
