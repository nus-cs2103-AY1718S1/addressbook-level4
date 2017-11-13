package seedu.room.storage;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.room.testutil.TypicalEvents.DANCE;
import static seedu.room.testutil.TypicalEvents.IFGTRAINING;
import static seedu.room.testutil.TypicalEvents.getTypicalEventBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.commons.util.FileUtil;
import seedu.room.model.EventBook;
import seedu.room.model.ReadOnlyEventBook;
import seedu.room.model.event.Event;
import seedu.room.model.event.ReadOnlyEvent;

//@@author sushinoya
public class XmlEventBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil
            .getPath("./src/test/data/XmlEventBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEventBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEventBook(null);
    }

    private java.util.Optional<ReadOnlyEventBook> readEventBook(String filePath) throws Exception {
        return new XmlEventBookStorage(filePath).readEventBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEventBook("NotXmlFormatEventBook.xml");
    }

    @Test
    public void readAndSaveEventBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempEventBook.xml";
        EventBook original = getTypicalEventBook();
        XmlEventBookStorage xmlEventBookStorage = new XmlEventBookStorage(filePath);

        //Save in new file and read back
        xmlEventBookStorage.saveEventBook(original, filePath);
        ReadOnlyEventBook readBack = xmlEventBookStorage.readEventBook(filePath).get();
        assertEquals(original.toString(), new EventBook(readBack).toString());

        //Modify data, overwrite exiting file, and read back
        original.addEvent(new Event(DANCE));
        ReadOnlyEvent eventToRemoved = original.getEventList().get(0);
        original.removeEvent(eventToRemoved);
        xmlEventBookStorage.saveEventBook(original, filePath);
        readBack = xmlEventBookStorage.readEventBook(filePath).get();
        assertEquals(original.toString(), new EventBook(readBack).toString());

        //Save and read without specifying file path
        original.addEvent(new Event(IFGTRAINING));
        xmlEventBookStorage.saveEventBook(original); //file path not specified
        readBack = xmlEventBookStorage.readEventBook().get(); //file path not specified
        assertEquals(original.toString(), new EventBook(readBack).toString());

    }

    @Test
    public void saveEventBook_nullResidentBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventBook(null, "SomeFile.xml");
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableEventBook eventBook = new XmlSerializableEventBook();
        thrown.expect(UnsupportedOperationException.class);
        eventBook.getEventList().remove(0);
    }

    /**
     * Saves {@code eventBook} at the specified {@code filePath}.
     */
    private void saveEventBook(ReadOnlyEventBook eventBook, String filePath) {
        try {
            new XmlEventBookStorage(filePath).saveEventBook(eventBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEventBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveEventBook(new EventBook(), null);
    }
}
