package seedu.address.google;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Observable;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;

import seedu.address.commons.core.EventsCenter;
import seedu.address.model.Model;

//@@author derrickchua-reused
/**Singleton class
 *
 * Command-line sample for the Google OAuth2 API described at <a
 * href="http://code.google.com/apis/accounts/docs/OAuth2Login.html">Using OAuth 2.0 for Login
 * (Experimental)</a>.
 *
 * @author Yaniv Inbar
 */
public class OAuth extends Observable {

    private static final OAuth oauth = new OAuth();
    private Model model;

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private final String appName = "W13B3-ABC/1.5RC";

    /** Directory to store user credentials. */
    private final java.io.File dataStoreDir =
            new java.io.File(System.getProperty("user.home"), ".store/addressbook");

    /**
     * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    private FileDataStoreFactory dataStoreFactory;

    /** Global instance of the HTTP transport. */
    private HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    private com.google.api.services.people.v1.PeopleService client;

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /** Authorizes the installed application to access user's protected data. */
    private Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                new InputStreamReader(OAuth.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=people "
                            + "into data/client_secrets.json");
            System.exit(1);
        }

        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets,
                Collections.singleton(PeopleServiceScopes.CONTACTS)).setDataStoreFactory(dataStoreFactory)
                .build();

        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    /** Method to obtain a PeopleService client
     *
     * @return PeopleService client
     * @throws Throwable
     */
    public PeopleService execute() throws Throwable {
        try {
            // initialize the transport
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            // initialize the data store factory
            dataStoreFactory = new FileDataStoreFactory(dataStoreDir);

            // authorization
            Credential credential = authorize();
            // set up global People instance
            client = new PeopleService.Builder(
                    httpTransport, jsonFactory, credential).setApplicationName(appName).build();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return client;


    }

    public void setModel (Model newModel) {
        model = newModel;
    }

    public Model getModel () {
        return model;
    }

    public static OAuth getInstance () {
        return oauth;
    }

}
