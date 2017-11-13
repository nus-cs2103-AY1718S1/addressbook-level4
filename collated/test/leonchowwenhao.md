# LeonChowWenHao
###### \java\seedu\address\logic\commands\SelectJoinedEventsCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code SelectJoinedEventsCommand}.
 */
public class SelectJoinedEventsCommandTest {
    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinPersonsToEvents(model);
    }

    /**
     * Test for entering single invalid index value. All assertions should be failures.
     **/
    @Test
    public void executeInvalidIndexFailure() {
        // Invalid out of bounds index
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        assertExecutionFailure(Collections.singletonList(outOfBoundsIndex),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Test for entering multiple invalid index value. All assertions should be failures.
     **/
    @Test
    public void executeMultipleInvalidIndexesFailure() {
        // One valid index, one invalid out of bounds index
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        assertExecutionFailure(Arrays.asList(INDEX_FIRST_PERSON, outOfBoundsIndex),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // Multiple invalid out of bounds index
        assertExecutionFailure(Arrays.asList(outOfBoundsIndex, outOfBoundsIndex),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Test for entering single valid index value. All assertions should be successful.
     **/
    @Test
    public void executeValidIndexSuccess() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        // First person index - Alice - Valid - 1 event joined total
        assertExecutionSuccess(Collections.singletonList(INDEX_FIRST_PERSON),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1), ALICE.getName()),
                Collections.singletonList(FIRST));

        // Third person index - Carl - Valid - 2 events joined total
        assertExecutionSuccess(Collections.singletonList(INDEX_THIRD_PERSON),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2), CARL.getName()),
                Arrays.asList(FIRST, SECOND));

        // Last person index - George - Valid - 0 events joined total
        assertExecutionSuccess(Collections.singletonList(lastPersonIndex),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0), GEORGE.getName()),
                Collections.emptyList());
    }

    /**
     * Test for entering multiple valid index values. All assertions should be successful.
     **/
    @Test
    public void executeMultipleValidIndexesSuccess() {
        Index secondLastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        // First & last person index - Alice, George - Valid - 1 event joined total
        assertExecutionSuccess(Arrays.asList(INDEX_FIRST_PERSON, lastPersonIndex),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1), ALICE.getName() + ", "
                        + GEORGE.getName()),
                Collections.singletonList(FIRST));

        // First & second person index - Alice, Benson - Valid - 2 events joined total
        assertExecutionSuccess(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2), ALICE.getName() + ", "
                        + BENSON.getName()),
                Arrays.asList(FIRST, SECOND));

        // Second & third person index - Benson, Carl - Valid - 2 events joined total
        assertExecutionSuccess(Arrays.asList(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2), BENSON.getName() + ", "
                        + CARL.getName()),
                Arrays.asList(FIRST, SECOND));

        // Second last & last person index - Fiona, George - Valid - 0 events joined total
        assertExecutionSuccess(Arrays.asList(secondLastPersonIndex, lastPersonIndex),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0), FIONA.getName() + ", "
                        + GEORGE.getName()),
                Collections.emptyList());
    }

    @Test
    public void equals() {
        SelectJoinedEventsCommand selectJoinedEventsFirstCommand = new SelectJoinedEventsCommand(
                Collections.singletonList(INDEX_FIRST_PERSON));
        SelectJoinedEventsCommand selectJoinedEventsSecondCommand = new SelectJoinedEventsCommand(
                Collections.singletonList(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(selectJoinedEventsFirstCommand.equals(selectJoinedEventsFirstCommand));

        // same values -> returns true
        SelectJoinedEventsCommand selectJoinedEventsFirstCommandCopy = new SelectJoinedEventsCommand(
                Collections.singletonList(INDEX_FIRST_PERSON));
        assertTrue(selectJoinedEventsFirstCommand.equals(selectJoinedEventsFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectJoinedEventsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectJoinedEventsFirstCommand == null);

        // different person index -> returns false
        assertFalse(selectJoinedEventsFirstCommand.equals(selectJoinedEventsSecondCommand));
    }

    /**
     * Executes a {@code SelectJoinedEventsCommand} with the given {@code indexList},
     * and checks that the expected message and filteredEventList are equal.
     */
    private void assertExecutionSuccess(List<Index> indexList, String expectedMessage,
                                        List<ReadOnlyEvent> expectedEventList) {
        SelectJoinedEventsCommand selectJoinedEventsCommand = prepareCommand(indexList);

        try {
            CommandResult commandResult = selectJoinedEventsCommand.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedEventList, model.getFilteredEventList());
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes a {@code SelectJoinedEventsCommand} with the given {@code indexList}, and checks that a
     * {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(List<Index> indexList, String expectedMessage) {
        SelectJoinedEventsCommand selectJoinedEventsCommand = prepareCommand(indexList);

        try {
            selectJoinedEventsCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code SelectJoinedEventsCommand} with parameters {@code indexList}.
     */
    private SelectJoinedEventsCommand prepareCommand(List<Index> indexList) {
        SelectJoinedEventsCommand selectJoinedEventsCommand = new SelectJoinedEventsCommand(indexList);
        selectJoinedEventsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectJoinedEventsCommand;
    }

    /**
     * Sets some persons to join events so the {@code SelectJoinedEventsCommand} can be tested.
     */
    private void joinPersonsToEvents (Model model) throws Exception {
        Person person1 = (Person) model.getFilteredPersonList().get(0);
        Person person2 = (Person) model.getFilteredPersonList().get(1);
        Person person3 = (Person) model.getFilteredPersonList().get(2);

        Event event1 = (Event) model.getFilteredEventList().get(0);
        Event event2 = (Event) model.getFilteredEventList().get(1);

        model.joinEvent(person1, event1);
        model.joinEvent(person2, event2);
        model.joinEvent(person3, event1);
        model.joinEvent(person3, event2);
    }

}
```
###### \java\seedu\address\logic\parser\SelectJoinedEventsCommandParserTest.java
``` java
/**
 * Tests for {@code SelectJoinedEventsCommandParser}.
 */
public class SelectJoinedEventsCommandParserTest {

    private SelectJoinedEventsCommandParser parser = new SelectJoinedEventsCommandParser();

    /**
     * Test for parsing valid Index. Includes single and multiple Index input.
     **/
    @Test
    public void parseValidArgsReturnsSelectJoinedEventsCommand() {
        // Valid argument for SelectJoinedEventsCommand is a List<Index>
        // Valid index specified
        assertParseSuccess(parser, "1", new SelectJoinedEventsCommand(
                Collections.singletonList(INDEX_FIRST_PERSON)));

        // Valid indexes specified
        assertParseSuccess(parser, "1 2 3", new SelectJoinedEventsCommand(getTypicalPersonIndexList()));
    }

    /**
     * Test for parsing invalid Index. Includes single and multiple Index input.
     **/
    @Test
    public void parseInvalidArgsThrowsParseException() {
        // Invalid index specified
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // No index specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // Zero index specified
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // Negative index specified
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // Indexes with character in between specified
        assertParseFailure(parser, "1, 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // One valid and one invalid Index
        assertParseFailure(parser, "1 0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));
    }


}
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("32/01/1991")); // there is no 32th in a month
        assertFalse(Birthday.isValidBirthday("10/13/1992")); // there isn't a 13th month
        assertFalse(Birthday.isValidBirthday("0/1/1994")); // day cannot be 0
        assertFalse(Birthday.isValidBirthday("1/0/1995")); // month cannot be 0
        assertFalse(Birthday.isValidBirthday("31/4/1990")); // april does not have a 31st day
        assertFalse(Birthday.isValidBirthday("30/02/1996")); // february does not have a 30th day
        assertFalse(Birthday.isValidBirthday("29/02/1997")); // 1997 is not a leap year
        assertFalse(Birthday.isValidBirthday("1993/11/21")); // does not follow 'DD/MM/YYYY' format
        assertFalse(Birthday.isValidBirthday("09/1994/30")); // does not follow 'DD/MM/YYYY' format

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("01/01/1990"));
        assertTrue(Birthday.isValidBirthday("13-05-1991"));
        assertTrue(Birthday.isValidBirthday("24.06.1992"));
        assertTrue(Birthday.isValidBirthday("17-02/1993"));
        assertTrue(Birthday.isValidBirthday("09/08.1994"));
        assertTrue(Birthday.isValidBirthday("09.08-1995"));
        assertTrue(Birthday.isValidBirthday("29-02-1996"));
        assertTrue(Birthday.isValidBirthday("28-02-1997"));
        assertTrue(Birthday.isValidBirthday("30-04-1998"));
        assertTrue(Birthday.isValidBirthday("31-05-1999"));
        assertTrue(Birthday.isValidBirthday("1-2-2000"));
        assertTrue(Birthday.isValidBirthday("9-10-2001"));
        assertTrue(Birthday.isValidBirthday("21-6-2002"));
    }
}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java

    @Test
    public void eventStorageReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlEventStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlEventStorageTest} class.
         */
        EventList original = getTypicalEventList();
        storageManager.saveEventStorage(original);
        ReadOnlyEventList retrieved = storageManager.readEventStorage().get();
        assertEquals(original, new EventList(retrieved));
    }

    @Test
    public void getEventStorageFilePath() {
        assertNotNull(storageManager.getEventStorageFilePath());
    }

    @Test
    public void handleEventStorageChangedEventExceptionThrownEventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage("dummy"),
                new JsonUserPrefsStorage("dummy"),
                new XmlEventStorageExceptionThrowingStub("dummy"));
        storage.handleEventStorageChangedEvent(new EventStorageChangedEvent(new EventList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlEventStorageExceptionThrowingStub extends XmlEventStorage {

        private XmlEventStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveEventStorage(ReadOnlyEventList eventList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
```
###### \java\seedu\address\storage\XmlEventStorageTest.java
``` java

public class XmlEventStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlEventStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEventStorageNullFilePathThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEventStorage(null);
    }

    private java.util.Optional<ReadOnlyEventList> readEventStorage(String filePath) throws Exception {
        return new XmlEventStorage(filePath).readEventStorage(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void readMissingFileEmptyResult() throws Exception {
        assertFalse(readEventStorage("NonExistentFile.xml").isPresent());
    }

    @Test
    public void readNotXmlFormatExceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEventStorage("NotXmlFormatEventStorage.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveEventStorageAllInOrderSuccess() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempEventStorage.xml";
        EventList original = getTypicalEventList();
        XmlEventStorage xmlEventStorage = new XmlEventStorage(filePath);

        //Save in new file and read back
        xmlEventStorage.saveEventStorage(original, filePath);
        ReadOnlyEventList readBack = xmlEventStorage.readEventStorage(filePath).get();
        assertEquals(original, new EventList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addEvent(new Event(SEVENTH));
        original.removeEvent(new Event(FIRST));
        xmlEventStorage.saveEventStorage(original, filePath);
        readBack = xmlEventStorage.readEventStorage(filePath).get();
        assertEquals(original, new EventList(readBack));

        //Save and read without specifying file path
        original.addEvent(new Event(EIGHTH));
        xmlEventStorage.saveEventStorage(original); //file path not specified
        readBack = xmlEventStorage.readEventStorage().get(); //file path not specified
        assertEquals(original, new EventList(readBack));

    }

    @Test
    public void saveEventStorageNullEventStorageThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventStorage(null, "SomeFile.xml");
    }

    @Test
    public void getEventListModifyListThrowsUnsupportedOperationException() {
        XmlSerializableEventStorage eventStorage = new XmlSerializableEventStorage();
        thrown.expect(UnsupportedOperationException.class);
        eventStorage.getEventList().remove(0);
    }

    /**
     * Saves {@code eventList} at the specified {@code filePath}.
     */
    private void saveEventStorage(ReadOnlyEventList eventList, String filePath) {
        try {
            new XmlEventStorage(filePath).saveEventStorage(eventList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEventStorageNullFilePathThrowsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveEventStorage(new EventList(), null);
    }
}
```
