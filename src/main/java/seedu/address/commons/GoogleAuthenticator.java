package seedu.address.commons;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.GetRedirectUrlEvent;
import seedu.address.logic.commands.exceptions.GoogleAuthException;

//@@author PhuaJunJie
/**
 * This class contains methods of the google Auth Api. For authentication after login.
 */
public class GoogleAuthenticator {

    private HttpTransport transport = new NetHttpTransport();
    private JacksonFactory jsonFactory = new JacksonFactory();

    private String clientId = "650819214900-b3m4dv6igjlf9q3nq9eqsbmspask57kp.apps.googleusercontent.com";
    private String clientSecret = "ttunyBEmZMrK_a9MH_qc1kus";
    private String redirectUrl = "https://contacts.google.com";

    private String scope1 = "https://www.googleapis.com/auth/contacts.readonly";
    private String scope2 = "https://www.googleapis.com/auth/plus.login";
    private String scope3 = "https://www.googleapis.com/auth/user.phonenumbers.read";
    private String scope4 = "https://www.googleapis.com/auth/contacts";
    private String scope5 = "https://mail.google.com/";


    private String authorizationUrl;


    //Constructor
    public GoogleAuthenticator() {
        this.authorizationUrl = new GoogleBrowserClientRequestUrl(clientId, redirectUrl,
                Arrays.asList(scope1, scope2, scope3, scope4, scope5)).build();
    }


    //Getter for Authorization URL for user login
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }


    /**
     * This method obtains the token from the redirect URL after successful login
     */
    public String getToken() throws GoogleAuthException {
        String token;
        boolean indexToken;
        try {
            GetRedirectUrlEvent event = new GetRedirectUrlEvent();
            EventsCenter.getInstance().post(event);
            String url = event.getReDirectUrl();
            indexToken = checkValidTokenIndex(url.indexOf("token="));
            if (indexToken) {
                token = url.substring(url.indexOf("token=") + 6, url.indexOf("&"));
            } else {
                throw new StringIndexOutOfBoundsException();
            }
        } catch (StringIndexOutOfBoundsException | NullPointerException e) {
            throw new GoogleAuthException("Authentication Failed. Please login again.");
        }
        return token;
    }

    /**
     * Checks if google login is valid
     * @returns true if valid (i.e. index is not -1) or false if not valid(i.e. index is -1)
     */
    public boolean checkValidTokenIndex(int index) {
        if (index == -1) {
            return false;
        }
        return true;
    }

    /**
     * Obtain google credentials from token
     */
    public GoogleCredential getCredential(String token) throws IOException {

        GoogleTokenResponse googleToken = new GoogleTokenResponse();
        googleToken.setAccessToken(token);

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(transport)
                .setJsonFactory(jsonFactory)
                .setClientSecrets(clientId, clientSecret)
                .build()
                .setFromTokenResponse(googleToken);
        return credential;
    }

    /**
     * Build PeopleService using google credentials
     */
    public PeopleService buildPeopleService(GoogleCredential credential) {
        PeopleService peopleService =
                new PeopleService.Builder(transport, jsonFactory, credential).build();
        return peopleService;
    }

    /**
     * Obtain the list of Contacts from google
     */
    public List<Person> getConnections(PeopleService peopleService)  throws IOException {
        ListConnectionsResponse response;
        response = peopleService.people().connections().list("people/me")
                .setPersonFields("names,emailAddresses,phoneNumbers,addresses")
                .execute();
        List<Person> connections = response.getConnections();

        return connections;
    }
    //@author srishag
    /**
     * Obtain transport from google
     * @return transport
     */
    public HttpTransport getTransport() {
        return transport;
    }

    /**
     * Obtain JsonFactory from google
     * @return JsonFactory
     */
    public JacksonFactory getJsonFactory() {
        return jsonFactory;
    }
}
