//@@author wishingmaid
package seedu.address.commons.util;

/**
 * Helper functions for extensions of files
 */
public class ExtensionCheckerUtil {
    /**
     * @param ext cannot be null
     * @param requiredExt cannot be null
     * @return true if the ext is matches with any of the extensions in the requiredExt String array.
     */
    public static boolean isOfType(String ext, String[] requiredExt) {
        for (String i : requiredExt) {
            if (ext.toLowerCase().equals(i.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String getExtension(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return ext;
    }
}
