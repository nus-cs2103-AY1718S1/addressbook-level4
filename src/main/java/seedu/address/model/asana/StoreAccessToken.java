package seedu.address.model.asana;

import com.asana.Client;
import com.asana.OAuthApp;
import seedu.address.storage.AsanaStorage.AsanaCredentials;

import java.io.IOException;

//@@author Sri-vatsa
/**
 * Stores AccessToken from user input
 */
public class StoreAccessToken {

    private final AsanaCredentials asanaCredentials = new AsanaCredentials();

    public StoreAccessToken(String accessCode) throws IOException {
        String accessToken = retrieveToken(accessCode);
        asanaCredentials.setAccessToken(accessToken);
    }

    /***
     * Retrieve access token using userAccessCode if it is valid
     */
    private String retrieveToken(String accessCode) throws IOException {
        OAuthApp app = new OAuthApp(asanaCredentials.getClientId(), asanaCredentials.getClientSecret(),
                asanaCredentials.getRedirectUri());
        Client.oauth(app);
        String accessToken = app.fetchToken(accessCode);

        //check if user input is valid by testing if accesscode given by user successfully authorises the application
        if (!(app.isAuthorized())) {
            throw new IllegalArgumentException();
        }

        return accessToken;
    }

}
