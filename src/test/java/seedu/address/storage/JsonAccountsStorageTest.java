package seedu.address.storage;
//@@author yangminxingnus
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.UserPrefs;

public class JsonAccountsStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/JsonAccountsStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAccPrefs_nullFilePath_throwsNullPointerException()
            throws DataConversionException, IOException {
        thrown.expect(NullPointerException.class);
        readAccPrefs(null);
    }

    private Optional<AccountsStorage> readAccPrefs(String userPrefsFileInTestDataFolder)
            throws DataConversionException, IOException {
        String prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonAccountsStorage(prefsFilePath).readAccountsPrefs(prefsFilePath);
    }

    @Test
    public void readAccPrefs_missingFile_emptyResult() throws DataConversionException, IOException {
        assertFalse(readAccPrefs("NonExistentFile.json").isPresent());
    }

    private String addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + userPrefsFileInTestDataFolder
                : null;
    }

    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPrefs(null, "SomeFile.json");
    }

    @Test
    public void saveUserPrefs_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPrefs(new UserPrefs(), null);
    }

    /**
     * Saves {@code userPrefs} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveUserPrefs(UserPrefs userPrefs, String prefsFileInTestDataFolder) {
        try {
            new JsonUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                    .saveUserPrefs(userPrefs);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveUserPrefs_allInOrder_success() throws DataConversionException, IOException {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(1200, 200, 0, 2);

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonUserPrefsStorage.saveUserPrefs(original);
        UserPrefs readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(5, 5, 5, 5);
        jsonUserPrefsStorage.saveUserPrefs(original);
        readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);
    }

    @Test
    public void readUserPrefs_nullFilePath_throwsNullPointerException() throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readUserPrefs(null);
    }

    private Optional<UserPrefs> readUserPrefs(String userPrefsFileInTestDataFolder) throws DataConversionException {
        String prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonUserPrefsStorage(prefsFilePath).readUserPrefs(prefsFilePath);
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readUserPrefs("NonExistentFile.json").isPresent());
    }
}
//@@author
