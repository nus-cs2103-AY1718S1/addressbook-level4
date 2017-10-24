package seedu.room.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.HOON;
import static seedu.room.testutil.TypicalPersons.IDA;
import static seedu.room.testutil.TypicalPersons.getTypicalRoomBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.commons.util.FileUtil;
import seedu.room.model.RoomBook;
import seedu.room.model.ReadOnlyRoomBook;
import seedu.room.model.person.Person;

public class XmlRoomBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlRoomBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readRoomBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRoomBook(null);
    }

    private java.util.Optional<ReadOnlyRoomBook> readRoomBook(String filePath) throws Exception {
        return new XmlRoomBookStorage(filePath).readRoomBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRoomBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRoomBook("NotXmlFormatRoomBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveRoomBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempRoomBook.xml";
        RoomBook original = getTypicalRoomBook();
        XmlRoomBookStorage xmlRoomBookStorage = new XmlRoomBookStorage(filePath);

        //Save in new file and read back
        xmlRoomBookStorage.saveRoomBook(original, filePath);
        ReadOnlyRoomBook readBack = xmlRoomBookStorage.readRoomBook(filePath).get();
        assertEquals(original, new RoomBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(new Person(HOON));
        original.removePerson(new Person(ALICE));
        xmlRoomBookStorage.saveRoomBook(original, filePath);
        readBack = xmlRoomBookStorage.readRoomBook(filePath).get();
        assertEquals(original, new RoomBook(readBack));

        //Save and read without specifying file path
        original.addPerson(new Person(IDA));
        xmlRoomBookStorage.saveRoomBook(original); //file path not specified
        readBack = xmlRoomBookStorage.readRoomBook().get(); //file path not specified
        assertEquals(original, new RoomBook(readBack));

    }

    @Test
    public void saveRoomBook_nullRoomBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRoomBook(null, "SomeFile.xml");
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableRoomBook roomBook = new XmlSerializableRoomBook();
        thrown.expect(UnsupportedOperationException.class);
        roomBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableRoomBook roomBook = new XmlSerializableRoomBook();
        thrown.expect(UnsupportedOperationException.class);
        roomBook.getTagList().remove(0);
    }

    /**
     * Saves {@code roomBook} at the specified {@code filePath}.
     */
    private void saveRoomBook(ReadOnlyRoomBook roomBook, String filePath) {
        try {
            new XmlRoomBookStorage(filePath).saveRoomBook(roomBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRoomBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveRoomBook(new RoomBook(), null);
    }


}
