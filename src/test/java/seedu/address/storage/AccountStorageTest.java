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

public class AccountStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/AccountStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAccPrefs_nullFilePath_throwsNullPointerException() throws
            DataConversionException, IOException {
        thrown.expect(NullPointerException.class);
        readAccountsPrefs(null);
    }

    private Optional<AccountsStorage> readAccountsPrefs(String userPrefsFileInTestDataFolder) throws
            DataConversionException, IOException {
        String prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new AccountsStorage(prefsFilePath).readAccountsPrefs(prefsFilePath);
    }

    @Test
    public void readAccPrefs_missingFile_emptyResult() throws DataConversionException, IOException {
        assertFalse(readAccountsPrefs("NonExistentFile.json").isPresent());
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
    public void saveAccPrefs_allInOrder_success() throws DataConversionException, IOException {

        AccountsStorage original = new AccountsStorage();
        original.getHm().put("test", "test");

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonAccountsStorage jsonAccountsPrefsStorage = new JsonAccountsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonAccountsPrefsStorage.saveAccountsPrefs(original);
        AccountsStorage readBack = jsonAccountsPrefsStorage.readAccountsPrefs().get();
        assertEquals(original, readBack);
    }
}
//@@author
