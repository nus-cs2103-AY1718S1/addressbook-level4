package seedu.room.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.HOON;
import static seedu.room.testutil.TypicalPersons.IDA;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.commons.util.FileUtil;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.ResidentBook;
import seedu.room.model.person.Person;

public class XmlResidentBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlResidentBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readResidentBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readResidentBook(null);
    }

    private java.util.Optional<ReadOnlyResidentBook> readResidentBook(String filePath) throws Exception {
        return new XmlResidentBookStorage(filePath).readResidentBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readResidentBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readResidentBook("NotXmlFormatResidentBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveResidentBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempResidentBook.xml";
        ResidentBook original = getTypicalResidentBook();
        XmlResidentBookStorage xmlResidentBookStorage = new XmlResidentBookStorage(filePath);

        //Save in new file and read back
        xmlResidentBookStorage.saveResidentBook(original, filePath);
        ReadOnlyResidentBook readBack = xmlResidentBookStorage.readResidentBook(filePath).get();
        assertEquals(original, new ResidentBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(new Person(HOON));
        original.removePerson(new Person(ALICE));
        xmlResidentBookStorage.saveResidentBook(original, filePath);
        readBack = xmlResidentBookStorage.readResidentBook(filePath).get();
        assertEquals(original, new ResidentBook(readBack));

        //Save and read without specifying file path
        original.addPerson(new Person(IDA));
        xmlResidentBookStorage.saveResidentBook(original); //file path not specified
        readBack = xmlResidentBookStorage.readResidentBook().get(); //file path not specified
        assertEquals(original, new ResidentBook(readBack));

    }

    @Test
    public void saveResidentBook_nullResidentBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveResidentBook(null, "SomeFile.xml");
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableResidentBook residentBook = new XmlSerializableResidentBook();
        thrown.expect(UnsupportedOperationException.class);
        residentBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableResidentBook residentBook = new XmlSerializableResidentBook();
        thrown.expect(UnsupportedOperationException.class);
        residentBook.getTagList().remove(0);
    }

    /**
     * Saves {@code residentBook} at the specified {@code filePath}.
     */
    private void saveResidentBook(ReadOnlyResidentBook residentBook, String filePath) {
        try {
            new XmlResidentBookStorage(filePath).saveResidentBook(residentBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveResidentBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveResidentBook(new ResidentBook(), null);
    }


}
