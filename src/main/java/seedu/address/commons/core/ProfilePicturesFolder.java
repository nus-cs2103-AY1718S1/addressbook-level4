package seedu.address.commons.core;

//@@author jaivigneshvenugopal
/**
 * Contains path to user's folder that contains profile pictures of all clients.
 */
public class ProfilePicturesFolder {

    private static String profilePicsFolderPath = "/images/profilePics/";

    public static void setPath(String path) {
        profilePicsFolderPath = path;
    }

    public static String getPath() {
        return profilePicsFolderPath;
    }
}
