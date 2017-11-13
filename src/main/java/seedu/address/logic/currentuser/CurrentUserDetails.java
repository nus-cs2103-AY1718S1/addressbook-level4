package seedu.address.logic.currentuser;

/**
 * Describe the current user
 */
public class CurrentUserDetails {
    private static String userId = "PUBLIC";
    private static String userIdHex = "PUBLIC";
    private static String saltText = "";
    private static String passwordText = "PUBLIC";

    public static void setCurrentUser(String userId, String userIdHex, String saltText, String passwordText) {
        CurrentUserDetails.userId = userId;
        CurrentUserDetails.userIdHex = userIdHex;
        CurrentUserDetails.saltText = saltText;
        CurrentUserDetails.passwordText = passwordText;
    }

    public static void setPublicUser() {
        userId = "PUBLIC";
        userIdHex = "PUBLIC";
        saltText = "";
        passwordText = "PUBLIC";
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        CurrentUserDetails.userId = userId;
    }

    public static String getUserIdHex() {
        return userIdHex;
    }

    public static String getSaltText() {
        return saltText;
    }

    public static String getPasswordText() {
        return passwordText;
    }
}
