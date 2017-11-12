package seedu.address.storage.AsanaStorage;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

//@@author Sri-vatsa
/***
 * Stores all the relevant data required for Asana
 */
public class AsanaCredentials {
    private static final String CLIENT_ID = "474342738710406";
    private static final String CLIENT_SECRET = "a89bbb49213d6b58ebce25cfa0995290";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static boolean isAsanaConfigured = false;
    private static String hashedToken;

    public AsanaCredentials() {
        hashedToken = null;
    }

    /**
     * Getter method for Access token
     */
    public String getAccessToken() {
        byte[] decoded = Base64.getDecoder().decode(hashedToken);
        String accessToken = new String(decoded, StandardCharsets.UTF_8);
        return accessToken;
    }

    /**
     * Getter method for CLIENT_ID
     */
    public String getClientId() {
        return CLIENT_ID;
    }

    /**
     * Getter method for Access token
     */
    public String getClientSecret() {
        return CLIENT_SECRET;
    }

    /**
     * Getter method for Access token
     */
    public String getRedirectUri() {
        return REDIRECT_URI;
    }

    /**
     * Setter method for access token
     */
    public void setAccessToken(String accessToken) {
        byte[] message = accessToken.getBytes(StandardCharsets.UTF_8);
        String encoded = Base64.getEncoder().encodeToString(message);
        hashedToken = encoded;
    }

    /**
     * Setter method for isAsanaConfigured
     */
    public void setIsAsanaConfigured(boolean value) {
        isAsanaConfigured = value;
    }

    /**
     * Getter method for isAsanaConfigured
     */
    public boolean getIsAsanaConfigured() {
        return isAsanaConfigured;
    }

}
