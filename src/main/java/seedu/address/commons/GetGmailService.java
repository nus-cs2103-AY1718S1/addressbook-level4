//@author srishag
package seedu.address.commons;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.gmail.Gmail;

import seedu.address.logic.commands.exceptions.GoogleAuthException;

/**
 * Returns Gmail service using the GoogleAuthenticator class
 */

public class GetGmailService {

    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    public Gmail getGmailService() throws GoogleAuthException, IOException {
        String token = googleAuthenticator.getToken();
        GoogleCredential credential = googleAuthenticator.getCredential(token);
        return new Gmail.Builder(googleAuthenticator.getTransport(), googleAuthenticator.getJsonFactory(), credential)
                .build();
    }

}
