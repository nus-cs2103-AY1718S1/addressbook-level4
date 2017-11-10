# liuhang0213
###### /java/seedu/address/logic/commands/NextMeetingCommandTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyMeeting;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.InternalId;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Contains integration tests (interaction with the Model) and unit tests for NextMeetingCommand.
 */
public class NextMeetingCommandTest {

    private Model model;
    private NextMeetingCommand nextMeetingCommand;
    private ReadOnlyMeeting meeting;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingList(), new UserPrefs());
        nextMeetingCommand = new NextMeetingCommand();
        nextMeetingCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        meeting = getTypicalMeetingList().getUpcomingMeeting();
    }

    @Test
    public void execute_nextMeeting_successfully() {
        StringBuilder expected = new StringBuilder();
        expected.append(NextMeetingCommand.MESSAGE_OUTPUT_PREFIX);
        try {
            for (InternalId id : meeting.getListOfPersonsId()) {
                expected.append(model.getAddressBook().getPersonByInternalIndex(id.getId()).getName().fullName);
                expected.append(", ");
            }
            expected.delete(expected.length() - 2, expected.length());
            expected.append('\n');
            expected.append(model.getMeetingList().getUpcomingMeeting().toString());
            assertCommandSuccess(nextMeetingCommand, model, expected.toString(), model);
        } catch (PersonNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```
###### /java/seedu/address/logic/commands/PrefCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code PrefCommand}.
 */
public class PrefCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UniqueMeetingList(), new UserPrefs());

    @Test
    public void equals() {
        String firstPrefKey = "AddressBookName";
        String firstPrefValue = model.getUserPrefs().getAddressBookName();
        String secondPrefKey = "AddressBookFilePath";
        String secondPrefValue = model.getUserPrefs().getAddressBookFilePath();

        PrefCommand prefFirstCommand = new PrefCommand(firstPrefKey, firstPrefValue);
        PrefCommand prefSecondCommand = new PrefCommand(secondPrefKey, secondPrefValue);

        // same object -> returns true
        assertTrue(prefFirstCommand.equals(prefFirstCommand));

        // same values -> returns true
        PrefCommand prefFirstCommandCopy = new PrefCommand(firstPrefKey, firstPrefValue);
        assertTrue(prefFirstCommand.equals(prefFirstCommandCopy));

        // different types -> returns false
        assertFalse(prefFirstCommand.equals(1));

        // null -> returns false
        assertFalse(prefFirstCommand.equals(null));

        // different value -> returns false
        assertFalse(prefFirstCommand.equals(prefSecondCommand));

    }

    @Test
    public void execute_invalidKey_failure() {
        String invalidKey = "This is not preference key.";
        String expectedMessage = String.format(PrefCommand.MESSAGE_PREF_KEY_NOT_FOUND, invalidKey);
        PrefCommand command = prepareCommand(invalidKey, "");
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_validKey_displayValue() {
        String key = "AddressBookName";
        String expectedOutput = model.getUserPrefs().getAddressBookName();
        PrefCommand command = prepareCommand(key, "");
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UniqueMeetingList(),
                new UserPrefs());
        assertCommandSuccess(command, model, expectedOutput, expectedModel);
    }

    @Test
    public void execute_validKey_updateValue() {
        String key = "AddressBookName";
        String newValue = "NewName";
        PrefCommand command = prepareCommand(key, newValue);
        String expectedOutput = String.format(PrefCommand.MESSAGE_EDIT_PREF_SUCCESS, key,
                model.getUserPrefs().getAddressBookName(), newValue);

        UserPrefs prefs = new UserPrefs();
        prefs.setAddressBookName(newValue);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UniqueMeetingList(), prefs);
        assertCommandSuccess(command, model, expectedOutput, expectedModel);
    }

    /**
     * Returns an {@code PrefCommand}
     */
    private PrefCommand prepareCommand(String prefKey, String newPrefValue) {
        PrefCommand prefCommand = new PrefCommand(prefKey, newPrefValue);
        prefCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return prefCommand;
    }

}
```
###### /java/seedu/address/logic/parser/PrefCommandParserTest.java
``` java
public class PrefCommandParserTest {

    private PrefCommandParser parser = new PrefCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrefCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        assertParseFailure(parser, "key value a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrefCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsPrefCommand() {
        String prefKey = "AddressBookName";
        String prefValue = "NewName";
        PrefCommand expectedPrefCommand =
                new PrefCommand(prefKey, prefValue);
        assertParseSuccess(parser, prefKey + " " + prefValue, expectedPrefCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + prefKey + " \n \t " + prefValue + "  \t", expectedPrefCommand);
    }
}
```
###### /java/seedu/address/model/UniqueMeetingListTest.java
``` java
public class UniqueMeetingListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMeetingList meetingList = new UniqueMeetingList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), meetingList.getMeetingList());
    }

    @Test
    public void setMeetings_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        meetingList.setMeetings(null);
    }

    @Test
    public void setMeetings_withDuplicateMeetings_throwsAssertionError() {
        List<ReadOnlyMeeting> newMeetings = Arrays.asList(new Meeting(M1), new Meeting(M1));
        MeetingListStub newData = new MeetingListStub(newMeetings);

        thrown.expect(AssertionError.class);
        meetingList.setMeetings(newData.getMeetingList());
    }

    /**
     * A stub UniqueMeetingList which meetings can violate interface constraints.
     */
    private static class MeetingListStub implements ReadOnlyMeetingList {
        private final ObservableList<ReadOnlyMeeting> meetings = FXCollections.observableArrayList();

        MeetingListStub (Collection<ReadOnlyMeeting> meetings) {
            this.meetings.setAll(meetings);
        }

        @Override
        public ObservableList<ReadOnlyMeeting> getMeetingList() {
            return meetings;
        }

        @Override
        public ReadOnlyMeeting getUpcomingMeeting() {
            return meetings.sorted().get(0);
        }
    }
}
```
###### /java/seedu/address/storage/XmlMeetingListStorageTest.java
``` java

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

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
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

        /*
        //Modify data, overwrite exiting file, and read back
        xmlMeetingListStorage.saveMeetingList(original, filePath);
        readBack = xmlMeetingListStorage.readMeetingList(filePath).get();
        assertEquals(original, new UniqueMeetingList(readBack));
        */

        //Save and read without specifying file path
        //original.add(new Meeting());
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
```
###### /java/seedu/address/testutil/MeetingBuilder.java
``` java
/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_LOCATION = "COM1-02-01";
    public static final String DEFAULT_NOTES = "Testing";

    private Meeting meeting;

    public MeetingBuilder() {
        try {
            LocalDateTime datetime = LocalDateTime.now().plusMonths(1);
            InternalId id = new InternalId(1);
            ArrayList<InternalId> participants = new ArrayList<>();
            participants.add(id);
            this.meeting = new Meeting(datetime, DEFAULT_LOCATION, DEFAULT_NOTES, participants);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default meeting's values are invalid.");
        }
    }

    public Meeting build() {
        return this.meeting;
    }

}
```
###### /java/seedu/address/testutil/TypicalMeetings.java
``` java
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalMeetings {

    public static final Meeting M1 = new MeetingBuilder().build();

    private TypicalMeetings() {} // prevents instantiation


    /**
     * Returns a {@code UniqueMeetingList} with all the typical meetings
     */
    public static UniqueMeetingList getTypicalMeetingList() {
        try {
            UniqueMeetingList meetings = new UniqueMeetingList();
            for (Meeting m : getTypicalMeetings()) {
                meetings.add(m);
            }
            return meetings;
        } catch (DuplicateMeetingException e) {
            throw new AssertionError("sample data cannot contain duplicate meetings", e);
        }
    }

    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(M1));
    }


}
```
