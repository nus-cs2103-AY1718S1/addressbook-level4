package seedu.address.model.person;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

//@@author HanYaodong
public class FacebookTest {

    @Test
    public void isValidFacebook() {
        // blank facebook
        assertFalse(Facebook.isValidFacebookId(""));
        assertFalse(Facebook.isValidFacebookId("  "));

        // not numerical facebook
        assertFalse(Facebook.isValidFacebookId("zuck"));
        assertFalse(Facebook.isValidFacebookId("some.user.name"));

        // valid facebook
        assertTrue(Facebook.isValidFacebookId("12345"));
    }
}
