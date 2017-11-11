package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private String filePath;

    public JsonUserPrefsStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     *
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static Optional<UserPrefs> readUserPrefs(String prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    public static void saveUserPrefs(UserPrefs userPrefs, String prefsFilePath) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, prefsFilePath);
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

    //@@author chrisboo
    /**
     * Update the addressBookFilePath and addressBookName fields in preferences.json
     */
    public static void updateUserPrefs(String userPrefsFilePath, String addressBookFilePath, String addressBookName)
        throws DataConversionException, IOException {
        UserPrefs userPrefs = readUserPrefs(userPrefsFilePath).get();
        userPrefs.setAddressBookFilePath(addressBookFilePath);
        userPrefs.setAddressBookName(addressBookName);
        saveUserPrefs(userPrefs, userPrefsFilePath);
    }
    //@@author
}
