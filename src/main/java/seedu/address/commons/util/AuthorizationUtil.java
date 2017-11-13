//@@author danielbrzn
package seedu.address.commons.util;

import java.io.IOException;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.util.Preconditions;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowUrlEvent;

/**
 * A utility class for OAuth authorization with Google
 */

public class AuthorizationUtil extends AuthorizationCodeInstalledApp {

    private final AuthorizationCodeFlow flow;
    private final VerificationCodeReceiver receiver;

    public AuthorizationUtil(AuthorizationCodeFlow flow, VerificationCodeReceiver receiver) {
        super(flow, receiver);
        this.flow = flow;
        this.receiver = receiver;
    }

    /**
     * Authorizes HitMeUp to access user's protected data.
     *
     * @param userId user ID or {@code null} if not using a persisted credential store
     * @return credential
     */
    public Credential authorize(String userId) throws IOException {
        try {
            // open in Browser Panel
            String redirectUri = receiver.getRedirectUri();
            AuthorizationCodeRequestUrl authorizationUrl =
                    flow.newAuthorizationUrl().setRedirectUri(redirectUri);
            browse(authorizationUrl.build());
            // receive authorization code and exchange it for an access token
            String code = receiver.waitForCode();
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
            // credential is not stored, but returned to caller
            return flow.createAndStoreCredential(response, userId);
        } finally {
            receiver.stop();
        }
    }

    /**
     * Open a browser at the given URL using {@link seedu.address.ui.BrowserPanel}
     * @param url URL to browse
     */
    public static void browse(String url) {
        Preconditions.checkNotNull(url);

        EventsCenter.getInstance().post(new ShowUrlEvent(url));
    }
}
