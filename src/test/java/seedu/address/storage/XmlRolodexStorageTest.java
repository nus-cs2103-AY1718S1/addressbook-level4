package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalRolodex;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.InvalidExtensionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyRolodex;
import seedu.address.model.Rolodex;
import seedu.address.model.person.Person;

public class XmlRolodexStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlRolodexStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readRolodexNullFilePathThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRolodex(null);
    }

    private java.util.Optional<ReadOnlyRolodex> readRolodex(String filePath) throws Exception {
        return new XmlRolodexStorage(filePath).readRolodex(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void readMissingFileEmptyResult() throws Exception {
        assertFalse(readRolodex("NonExistentFile.rldx").isPresent());
    }

    @Test
    public void readNotXmlFormatExceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRolodex("NotRldxFormatRolodex.rldx");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveRolodexAllInOrderSuccess() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempRolodex.rldx";
        Rolodex original = getTypicalRolodex();
        XmlRolodexStorage xmlRolodexStorage = new XmlRolodexStorage(filePath);

        //Save in new file and read back
        xmlRolodexStorage.saveRolodex(original, filePath);
        ReadOnlyRolodex readBack = xmlRolodexStorage.readRolodex(filePath).get();
        assertEquals(original, new Rolodex(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(new Person(HOON));
        original.removePerson(new Person(ALICE));
        xmlRolodexStorage.saveRolodex(original, filePath);
        readBack = xmlRolodexStorage.readRolodex(filePath).get();
        assertEquals(original, new Rolodex(readBack));

        //Save and read without specifying file path
        original.addPerson(new Person(IDA));
        xmlRolodexStorage.saveRolodex(original); //file path not specified
        readBack = xmlRolodexStorage.readRolodex().get(); //file path not specified
        assertEquals(original, new Rolodex(readBack));

    }

    @Test
    public void saveRolodexNullRolodexThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRolodex(null, "SomeFile.rldx");
    }

    @Test
    public void getPersonListModifyListThrowsUnsupportedOperationException() {
        XmlSerializableRolodex rolodex = new XmlSerializableRolodex();
        thrown.expect(UnsupportedOperationException.class);
        rolodex.getPersonList().remove(0);
    }

    @Test
    public void getTagListModifyListThrowsUnsupportedOperationException() {
        XmlSerializableRolodex rolodex = new XmlSerializableRolodex();
        thrown.expect(UnsupportedOperationException.class);
        rolodex.getTagList().remove(0);
    }

    /**
     * Saves {@code rolodex} at the specified {@code filePath}.
     */
    private void saveRolodex(ReadOnlyRolodex rolodex, String filePath) {
        try {
            new XmlRolodexStorage(filePath).saveRolodex(rolodex, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRolodexNullFilePathThrowsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveRolodex(new Rolodex(), null);
    }

    @Test
    public void saveRolodexInvalidStorageExtensionThrowsInvalidExtensionException() throws IOException {
        String invalidExtensionedFilePath = "invalid/extension.megaPoop";
        XmlRolodexStorage rolodexStorage = new XmlRolodexStorage(invalidExtensionedFilePath);
        thrown.expect(InvalidExtensionException.class);
        rolodexStorage.saveRolodex(new Rolodex());
    }

    @Test
    public void assertEqualsSameInstanceReturnsTrue() {
        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.rldx";
        XmlRolodexStorage xmlRolodexStorage = new XmlRolodexStorage(pefsFilePath);
        assertTrue(xmlRolodexStorage.equals(xmlRolodexStorage));
    }

    @Test
    public void assertEqualsNotXmlRolodexStorageInstanceReturnsFalse() {
        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.rldx";
        XmlRolodexStorage xmlRolodexStorage = new XmlRolodexStorage(pefsFilePath);
        assertFalse(xmlRolodexStorage.equals(new Object()));
    }


}
