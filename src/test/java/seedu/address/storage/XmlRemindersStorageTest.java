package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalReminders.DENTIST_REMINDER;
import static seedu.address.testutil.TypicalReminders.HOMEWORK_REMINDER;
import static seedu.address.testutil.TypicalReminders.MEETING_REMINDER;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.UniqueReminderList;

//@@author justinpoh
public class XmlRemindersStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlRemindersStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readReminders(null);
    }

    private java.util.Optional<ReadOnlyUniqueReminderList> readReminders(String filePath) throws Exception {
        return new XmlRemindersStorage(filePath).readReminders(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readReminders("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readReminders("NotXmlFormatReminders.xml");
    }

    @Test
    public void readAndSaveReminders_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempReminders.xml";
        UniqueReminderList original = getUniqueTypicalReminders();
        XmlRemindersStorage xmlRemindersStorage = new XmlRemindersStorage(filePath);

        //Save in new file and read back
        xmlRemindersStorage.saveReminders(original, filePath);
        ReadOnlyUniqueReminderList readBack = xmlRemindersStorage.readReminders(filePath).get();
        assertEquals(original, new UniqueReminderList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.add(new Reminder(MEETING_REMINDER));
        original.remove(new Reminder(HOMEWORK_REMINDER));
        xmlRemindersStorage.saveReminders(original, filePath);
        readBack = xmlRemindersStorage.readReminders(filePath).get();
        assertEquals(original, new UniqueReminderList(readBack));

        //Save and read without specifying file path
        original.add(new Reminder(DENTIST_REMINDER));
        xmlRemindersStorage.saveReminders(original); //file path not specified
        readBack = xmlRemindersStorage.readReminders().get(); //file path not specified
        assertEquals(original, new UniqueReminderList(readBack));

    }

    @Test
    public void saveReminders_nullReminders_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveReminders(null, "SomeFile.xml");
    }

    @Test
    public void getReminderList_modifyList_throwsUnsupportedOperationException() {
        UniqueReminderList reminderList = new UniqueReminderList();
        thrown.expect(UnsupportedOperationException.class);
        reminderList.asObservableList().remove(0);
    }

    /**
     * Saves {@code reminders} at the specified {@code filePath}.
     */
    private void saveReminders(UniqueReminderList reminders, String filePath) {
        try {
            new XmlRemindersStorage(filePath).saveReminders(reminders, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveReminders_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveReminders(new UniqueReminderList(), null);
    }

}

