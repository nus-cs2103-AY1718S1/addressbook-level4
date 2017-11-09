package seedu.address.model.asana;

//@@author Sri-vatsa
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import com.asana.Client;
import com.asana.OAuthApp;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Authenticate & Store access token for Asana
 */
public class AuthenticateAsanaUser {

    private static final String CLIENT_ID = "474342738710406";
    private static final String CLIENT_SECRET = "a89bbb49213d6b58ebce25cfa0995290";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    private String accessToken  = "0/b62305d262c673af5c042bfad54ef834";

    public AuthenticateAsanaUser() throws URISyntaxException, IOException, IllegalValueException {
        //if accesstoken is null, get an access token from Asana
        if (!isAuthenticated()) {
            OAuthApp app = new OAuthApp(CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
            Client client = Client.oauth(app);

            //to prevent CSRF attacks
            String currentState = UUID.randomUUID().toString();
            String url = app.getAuthorizationUrl(currentState);

            //open browser on desktop for authentication purpose
            Desktop.getDesktop().browse(new URI(url));

            //Get the authentication code from Asana
            //TODO: get from command line on app
            String codeFromAsana = "0/b62305d262c673af5c042bfad54ef834";
            //new LineReader(new InputStreamReader(System.in)).readLine();

            //TODO: Hash and store access token
            accessToken = app.fetchToken(codeFromAsana);

            if (!app.isAuthorized()) {
                throw new IllegalValueException("OurAB failed to sync with Asana");
            }
        }
    }

    /**
     * checks if user is authenticated by Asana
     */
    private boolean isAuthenticated() {
        return !(accessToken == null);
    }

    /**
     * Getter method for Access token
     */
    public String getAccessToken() {
        return accessToken;
    }
}
