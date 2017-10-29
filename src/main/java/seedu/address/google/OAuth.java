package seedu.address.google;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

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

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.AuthorizationEvent;
import seedu.address.google.Synchronise;

/**
 * Command-line sample for the Google OAuth2 API described at <a
 * href="http://code.google.com/apis/accounts/docs/OAuth2Login.html">Using OAuth 2.0 for Login
 * (Experimental)</a>.
 *
 * @author Yaniv Inbar
 */
public class OAuth {

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "W13B3-AddressBook/1.2";

    /** Directory to store user credentials. */
    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), ".store/addressbook");

    /**
     * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static com.google.api.services.people.v1.PeopleService client;

    private static Synchronise sync;

    private static SyncTable syncTable;

    public OAuth(SyncTable syncTable) {
        registerAsAnEventHandler(this);
        this.syncTable = syncTable;
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /** Authorizes the installed application to access user's protected data. */
    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(OAuth.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=people "
                            + "into seedu/address/src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(PeopleServiceScopes.CONTACTS)).setDataStoreFactory(dataStoreFactory)
                .build();

        // authorize

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }



    @Subscribe
    private static void handleAuthorizationEvent(AuthorizationEvent event) throws Throwable {
        new Thread (() -> {
            try {
                // initialize the transport
                httpTransport = GoogleNetHttpTransport.newTrustedTransport();

                // initialize the data store factory
                dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

                // authorization
                Credential credential = authorize();
                // set up global People instance
                client = new PeopleService.Builder(
                        httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();

                sync = new Synchronise(client, syncTable);
                sync.execute(event.getPersonList());
//                exportContacts(event.getPersonList());
//                importContacts();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }).start();


    }

}
