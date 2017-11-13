package seedu.address.google;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

// @@author derrickchua
public class OAuthTest {

    @Test
    public void ensureSingleton() {
        OAuth instance1 = prepareOAuth();
        OAuth instance2 = prepareOAuth();

        assertTrue(instance1 == instance2);
    }


    public OAuth prepareOAuth() {
        return OAuth.getInstance();
    }
}
