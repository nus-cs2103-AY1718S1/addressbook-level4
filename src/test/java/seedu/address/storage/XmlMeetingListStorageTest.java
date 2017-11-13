package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingList;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyMeetingList;
import seedu.address.model.UniqueMeetingList;
//@@author liuhang0213

public class XmlMeetingListStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath(
            "./src/test/data/XmlMeetingListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readMeetingList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readMeetingList(null);
    }

    private java.util.Optional<ReadOnlyMeetingList> readMeetingList(String filePath) throws Exception {
        return new XmlMeetingListStorage(filePath).readMeetingList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMeetingList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readMeetingList("NotXmlFormatMeetingList.xml");
    }

    @Test
    public void readAndSaveMeetingList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempMeetingList.xml";
        UniqueMeetingList original = getTypicalMeetingList();
        XmlMeetingListStorage xmlMeetingListStorage = new XmlMeetingListStorage(filePath);

        //Save in new file and read back
        xmlMeetingListStorage.saveMeetingList(original, filePath);
        ReadOnlyMeetingList readBack = xmlMeetingListStorage.readMeetingList(filePath).get();
        assertEquals(original, new UniqueMeetingList(readBack));

        //Save and read without specifying file path
        xmlMeetingListStorage.saveMeetingList(original); //file path not specified
        readBack = xmlMeetingListStorage.readMeetingList().get(); //file path not specified
        assertEquals(original, new UniqueMeetingList(readBack));

    }

    @Test
    public void saveMeetingList_nullMeetingList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveMeetingList(null, "SomeFile.xml");
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableMeetingList meetingList = new XmlSerializableMeetingList();
        thrown.expect(UnsupportedOperationException.class);
        meetingList.getMeetingList().remove(0);
    }

    /**
     * Saves {@code meetingList} at the specified {@code filePath}.
     */
    private void saveMeetingList(ReadOnlyMeetingList meetingList, String filePath) {
        try {
            new XmlMeetingListStorage(filePath).saveMeetingList(meetingList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMeetingList_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveMeetingList(new UniqueMeetingList(), null);
    }


}
