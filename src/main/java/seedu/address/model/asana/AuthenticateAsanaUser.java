package seedu.address.model.asana;

import com.asana.Client;
import com.asana.OAuthApp;
import seedu.address.storage.AsanaStorage.AsanaCredentials;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

//@@author Sri-vatsa
/**
 * Authenticates Asana user by redirecting user to Asana's webpage
 */
public class AuthenticateAsanaUser {

    public AuthenticateAsanaUser () throws URISyntaxException, IOException {
        AsanaCredentials asanaCredentials = new AsanaCredentials();
        OAuthApp app = new OAuthApp(asanaCredentials.getClientId(), asanaCredentials.getClientSecret(),
                asanaCredentials.getRedirectUri());
        Client client = Client.oauth(app);

        //to prevent CSRF attacks
        String currentState = UUID.randomUUID().toString();
        String url = app.getAuthorizationUrl(currentState);

        //open browser on desktop for authentication purpose --> Asana to show authorisation key
        Desktop.getDesktop().browse(new URI(url));

    }
}
