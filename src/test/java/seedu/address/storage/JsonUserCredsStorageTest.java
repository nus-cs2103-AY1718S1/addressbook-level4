//@@author zenghou
package seedu.address.storage;

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
import seedu.address.model.user.UserCreds;

public class JsonUserCredsStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/JsonUserCredsStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserCreds_nullFilePath_throwsNullPointerException() throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readUserCreds(null);
    }

    private Optional<UserCreds> readUserCreds(String userCredsFileInTestDataFolder) throws DataConversionException {
        String credsFilePath = addToTestDataPathIfNotNull(userCredsFileInTestDataFolder);
        return new JsonUserCredsStorage(credsFilePath).readUserCreds(credsFilePath);
    }

    @Test
    public void readUserCreds_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readUserCreds("NonExistentFile.json").isPresent());
    }

    @Test
    public void readUserCreds_notJsonFormat_exceptionThrown() throws DataConversionException {
        thrown.expect(DataConversionException.class);
        readUserCreds("NotJsonFormatUserCreds.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    private String addToTestDataPathIfNotNull(String userCredsFileInTestDataFolder) {
        return userCredsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + userCredsFileInTestDataFolder
                : null;
    }

    @Test
    public void readUserCreds_fileInOrder_successfullyRead() throws DataConversionException {
        UserCreds expected = getTypicalUserCreds();
        UserCreds actual = readUserCreds("TypicalUserCreds.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readUserCreds_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        UserCreds actual = readUserCreds("EmptyUserCreds.json").get();
        assertEquals(new UserCreds(), actual);
    }

    @Test
    public void readUserCreds_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        UserCreds expected = getTypicalUserCreds();
        UserCreds actual = readUserCreds("ExtraValuesUserCreds.json").get();

        assertEquals(expected, actual);
    }

    private UserCreds getTypicalUserCreds() {
        UserCreds userCreds = new UserCreds("admin", "password");
        return userCreds;
    }

    @Test
    public void savePrefs_nullCreds_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserCreds(null, "SomeFile.json");
    }

    @Test
    public void saveUserCreds_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserCreds(new UserCreds(), null);
    }

    /**
     * Saves {@code userPrefs} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveUserCreds(UserCreds userCreds, String credsFileInTestDataFolder) {
        try {
            new JsonUserCredsStorage(addToTestDataPathIfNotNull(credsFileInTestDataFolder))
                    .saveUserCreds(userCreds);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveUserCreds_allInOrder_success() throws DataConversionException, IOException {

        UserCreds original = new UserCreds("admin", "password");

        String credsFilePath = testFolder.getRoot() + File.separator + "TempCreds.json";
        JsonUserCredsStorage jsonUserCredsStorage = new JsonUserCredsStorage(credsFilePath);

        //Try writing when the file doesn't exist
        jsonUserCredsStorage.saveUserCreds(original);
        UserCreds readBack = jsonUserCredsStorage.readUserCreds().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.updatePassword("newPassword");
        original.updateUsername("admin2");
        jsonUserCredsStorage.saveUserCreds(original);
        readBack = jsonUserCredsStorage.readUserCreds().get();
        assertEquals(original, readBack);
    }
}
