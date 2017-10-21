package seedu.address.storage.util;

import java.util.regex.Pattern;

/**
 * Helper functions for handling Rolodex storage operations.
 */
public class RolodexStorageUtil {

    public static final Pattern FILEPATH_REGEX = Pattern.compile("^(.+)/([^/]+)$");

    public static final String ROLODEX_FILE_EXTENSION = ".rldx";

    /**
     * Returns true if the full filepath string given has a valid extension
     */
    public static boolean isValidRolodexStorageExtension(String rolodexFilepath) {
        return rolodexFilepath.endsWith(ROLODEX_FILE_EXTENSION);
    }

    /**
     * Returns true if the full filepath string given is a valid filepath
     */
    public static boolean isValidRolodexStorageFilepath(String rolodexFilepath) {
        return FILEPATH_REGEX.matcher(rolodexFilepath).matches();
    }
}
