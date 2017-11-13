//@@author zenghou
package seedu.address.model.user;

import java.util.Objects;

/**
 * Represents a user's credentials
 */
public class UserCreds {
    private String username;
    private int passwordHash;
    private boolean isValidated = false;

    public UserCreds() {
        username = "admin";
        passwordHash = "password".hashCode();
    }

    public UserCreds(String username, String password) {
        this.username = username;
        this.passwordHash = password.hashCode();
    }

    /**
     * Takes in two UserCreds object and verifies if they match
     * @param currentSessionCredentials enter by current user
     * @param savedCredentials in the system
     * @return true if current user is a valid user
     */
    public static boolean isValidUser(UserCreds currentSessionCredentials, UserCreds savedCredentials) {
        return currentSessionCredentials.equals(savedCredentials);
    }

    /**
     * Update isValidated flag to true after current user's credentials matches the saved credentials
     * {@link #isValidUser(UserCreds, UserCreds)}
     * Flag is only valid for this current session, and should be set back to false after current session ends.
     */
    public void validateCurrentSession() {
        isValidated = true;
    }

    /**
     * Checks if current user is validated.
     * @return False if current user is not logged in
     */
    public boolean isValidSession() {
        return isValidated;
    }

    /**
     * Takes in a new String {@code password} and updates the passwordHash
     */
    public void updatePassword(String newPassword) {
        passwordHash = newPassword.hashCode();
    }

    public void updateUsername(String newUsername) {
        username = newUsername;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserCreds // instanceof handles nulls
                && this.username.equals(((UserCreds) other).username)
                && this.passwordHash == ((UserCreds) other).passwordHash);
    }

    @Override
    public String toString() {
        return "User: " + username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passwordHash);
    }

}
