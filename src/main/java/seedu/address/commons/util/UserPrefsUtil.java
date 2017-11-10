package seedu.address.commons.util;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserPrefs;

//@@author chrisboo
/**
 * A class for accessing User's Preferences
 */
public class UserPrefsUtil {
    public static Optional<UserPrefs> readUserPrefs(String userPrefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(userPrefsFilePath, UserPrefs.class);
    }

    public static void saveUserPrefs(UserPrefs userPrefs, String userPrefsFilePath) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, userPrefsFilePath);
    }

    /**
     * Update the addressBookFilePath and addressBookName fields in preferences.json
     */
    public static void updateUserPrefs(String addressBookFilePath, String addressBookName) throws DataConversionException, IOException {
        UserPrefs userPrefs = readUserPrefs("preferences.json").get();
        userPrefs.setAddressBookFilePath(addressBookFilePath);
        userPrefs.setAddressBookName(addressBookName);
        saveUserPrefs(userPrefs, "preferences.json");
    }
}
//@@author
