package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalUserPerson.getTypicalUserPerson;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.UserPerson;

//@@author bladerail
public class XmlUserProfileStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlUserProfileStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserProfile_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readUserProfile(null);
    }

    private java.util.Optional<UserPerson> readUserProfile(String filePath) throws Exception {
        return new XmlUserProfileStorage(filePath).readUserProfile(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUserProfile("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readUserProfile("NotXmlFormatUserProfile.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveUserProfile_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempUserProfile.xml";
        UserPerson original = getTypicalUserPerson();
        XmlUserProfileStorage xmlUserProfileStorage = new XmlUserProfileStorage(filePath);

        //Save in new file and read back
        xmlUserProfileStorage.saveUserPerson(original, filePath);
        UserPerson readBack = xmlUserProfileStorage.readUserProfile(filePath).get();
        assertEquals(original, new UserPerson(readBack));

        //Modify data, overwrite exiting file, and read back
        original.update(new Person(HOON));
        xmlUserProfileStorage.saveUserPerson(original, filePath);
        readBack = xmlUserProfileStorage.readUserProfile(filePath).get();
        assertEquals(original, new UserPerson(readBack));

        //Save and read without specifying file path
        original.update(new Person(IDA));
        xmlUserProfileStorage.saveUserPerson(original); //file path not specified
        readBack = xmlUserProfileStorage.readUserProfile().get(); //file path not specified
        assertEquals(original, new UserPerson(readBack));

    }

    @Test
    public void saveUserProfile_nullUserProfile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserPerson(null, "SomeFile.xml");
    }

    /**
     * Saves {@code UserProfile} at the specified {@code filePath}.
     */
    private void saveUserPerson(UserPerson userPerson, String filePath) {
        try {
            new XmlUserProfileStorage(filePath).saveUserPerson(userPerson, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveUserPerson_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveUserPerson(new UserPerson(), null);
    }


}
