package seedu.address.model.asana;

import seedu.address.model.exceptions.AsanaAuthenticationException;
import seedu.address.storage.AsanaStorage.AsanaCredentials;

//@@author Sri-vatsa
/**
 * Authenticate & Store access token for Asana
 */
public class CheckAuthenticateAsanaUser {

    private static String currentAccessToken;

    public CheckAuthenticateAsanaUser() throws AsanaAuthenticationException {
        if (!isAuthenticated()) {
           throw new AsanaAuthenticationException("Please make sure you have allowed OurAB to access your Asana account");
        }
    }

    /**
     * checks if user is authenticated by Asana
     */
    private boolean isAuthenticated() {
        currentAccessToken = new AsanaCredentials().getAccessToken();
        return !(currentAccessToken == null);
    }

}
