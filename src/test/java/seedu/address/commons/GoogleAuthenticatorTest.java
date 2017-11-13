//@@author PhuaJunJie
package seedu.address.commons;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.GoogleAuthException;

public class GoogleAuthenticatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GoogleAuthenticator authenticator = new GoogleAuthenticator();

    /**
     * Checks if Login url generated is in the valid format
     */
    @Test
    public void execute_obtain_loginUrl() {
        assertTrue(authenticator.getAuthorizationUrl().contains(
                "https://accounts.google.com/o/oauth2/auth?client_id"));
    }

    /**
     * Checks if user is authenticated. In this case no token is generated and null exception is thrown.
     */
    @Test
    public void execute_get_invalidToken() throws Exception {
        thrown.expect(GoogleAuthException.class);
        authenticator.getToken();
    }

    /**
     * Checks if user is authenticated. In this case no google contacts list is generated and null exception is thrown.
     */
    @Test
    public void execute_get_invalidGooglePersonList() throws Exception {
        thrown.expect(NullPointerException.class);
        authenticator.getConnections(null);
    }
}
