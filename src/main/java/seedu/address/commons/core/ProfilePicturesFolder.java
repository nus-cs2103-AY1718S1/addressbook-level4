package seedu.address.commons.core;

public class ProfilePicturesFolder {

    private static String profilePicsFolderPath = "C:/Users/acer/Desktop/SE/profilepic/";

    public static void setPath(String path) {
        profilePicsFolderPath = path;
    }

    public static String getPath() {
        return profilePicsFolderPath;
    }
}
