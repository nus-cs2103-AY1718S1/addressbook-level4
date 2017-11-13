# LimYangSheng
###### \java\guitests\guihandles\MeetingCardHandle.java
``` java
/**
 * Provides a handle to a meeting card in the meeting list panel.
 */
public class MeetingCardHandle extends NodeHandle<Node>  {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String MEETING_NAME_FIELD_ID = "#meetingName";
    private static final String MEETING_TIME_FIELD_ID = "#meetingTime";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label meetingNameLabel;
    private final Label meetingTimeLabel;

    public MeetingCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.meetingNameLabel = getChildNode(MEETING_NAME_FIELD_ID);
        this.meetingTimeLabel = getChildNode(MEETING_TIME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getMeetingName() {
        return meetingNameLabel.getText();
    }

    public String getMeetingTime() {
        return meetingTimeLabel.getText();
    }

}
```
###### \java\guitests\guihandles\MeetingListPanelHandle.java
``` java
/**
 * Provides a handle for {@code MeetingListPanel} containing the list of {@code MeetingCard}.
 */
public class MeetingListPanelHandle extends NodeHandle<ListView<MeetingCard>> {
    public static final String MEETING_LIST_VIEW_ID = "#meetingListView";

    public MeetingListPanelHandle(ListView<MeetingCard> meetingListPanelNode) {
        super(meetingListPanelNode);
    }

}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public Predicate<? super Meeting> getMeetingListPredicate() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredMeetingList(Predicate<Meeting> predicate) {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortMeeting() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\BackupCommandTest.java
``` java
public class BackupCommandTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model;
    private UserPrefs userPrefs;
    private StorageManager storageManager;
    private AddressBookStorage addressBookStorage;
    private String filePath;
    private String backupFilePath;
    private BackupCommand backupCommand;

    @Before
    public void setUp() {
        filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        backupFilePath = testFolder.getRoot().getPath() + "TempAddressBook-backup.xml";
        userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(filePath);
        addressBookStorage = new XmlAddressBookStorage(filePath);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        storageManager = new StorageManager(addressBookStorage,
                new JsonUserPrefsStorage("dummy"));

        backupCommand = new BackupCommand();
        backupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_successfulBackup() throws Exception {
        ReadOnlyAddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        backupCommand.execute();
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(backupFilePath).get();
        assertEquals(expectedAddressBook, new AddressBook(retrieved));
    }
}
```
###### \java\seedu\address\logic\commands\RestoreBackupCommandTest.java
``` java
public class RestoreBackupCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private StorageManager storageManager;
    private AddressBookStorage addressBookStorage;
    private String filePath;
    private String backupFilePath;
    private RestoreBackupCommand restoreBackupCommand;

    @Before
    public void setUp() throws Exception {
        filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        backupFilePath = testFolder.getRoot().getPath() + "TempAddressBook-backup.xml";
        addressBookStorage = new XmlAddressBookStorage(filePath);

        model = new ModelManager(new AddressBook(), new UserPrefs());
        storageManager = new StorageManager(addressBookStorage,
                new JsonUserPrefsStorage("dummy"));

        restoreBackupCommand = new RestoreBackupCommand();
        restoreBackupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_restoreBackup_successful() throws Exception {
        storageManager.saveAddressBook(getTypicalAddressBook(), backupFilePath);
        assertTrue(model.getFilteredPersonList().size() == 0);
        ReadOnlyAddressBook expectedAddressBook = getTypicalAddressBook();
        CommandResult result = restoreBackupCommand.execute();
        assertEquals(result.feedbackToUser, restoreBackupCommand.MESSAGE_SUCCESS);
        ReadOnlyAddressBook retrieved = model.getAddressBook();
        assertEquals(expectedAddressBook, retrieved);
    }

    @Test
    public void execute_restoreBackup_withoutBackupFile() throws Exception {
        CommandResult result = restoreBackupCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BackupFilePresentEvent);
        assertEquals(result.feedbackToUser, restoreBackupCommand.MESSAGE_NO_BACKUP_FILE);
    }

}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void successfulBackupOfAddressBook() throws Exception {
        ReadOnlyAddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(getTempFilePath("ab-backup.xml")).get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void backupOfAddressBook_exceptionThrown() throws Exception {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"));
        storage.backupAddressBook(new AddressBook());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

```
###### \java\seedu\address\ui\MeetingCardTest.java
``` java
public class MeetingCardTest extends GuiUnitTest {
    @Test
    public void display() {
        String meetingName = "dummy";
        String meetingTime = "dummy";
        Person person = new PersonBuilder().withMeeting("dinner", "2017-12-30 18:00").build();
        for (Meeting meeting : person.getMeetings()) {
            meetingName = meeting.meetingName;
            meetingTime = meeting.value;
        }
        try {
            Meeting expectedMeeting = new Meeting(person, meetingName, meetingTime);
            MeetingCard meetingCard = new MeetingCard(expectedMeeting, 1);
            uiPartRule.setUiPart(meetingCard);
            assertMeetingDisplay(meetingCard, expectedMeeting, 1);

            // changes made to Meeting reflects on card
            guiRobot.interact(() -> {
                person.setName(ALICE.getName());
                person.setAddress(ALICE.getAddress());
                person.setEmail(ALICE.getEmail());
                person.setPhone(ALICE.getPhone());
                person.setTags(ALICE.getTags());
                person.setNote(ALICE.getNote());
                person.setMeetings(ALICE.getMeetings());
                expectedMeeting.setPerson(person);
            });
            assertMeetingDisplay(meetingCard, expectedMeeting, 1);

        } catch (IllegalValueException e) {
            throw new AssertionError("Time format should be correct");
        }
    }

    @Test
    public void equals() {
        String meetingName = "dummy";
        String meetingTime = "dummy";
        Person person = new PersonBuilder().withMeeting("dinner", "2017-12-30 18:00").build();
        for (Meeting meeting : person.getMeetings()) {
            meetingName = meeting.meetingName;
            meetingTime = meeting.value;
        }
        try {
            Meeting expectedMeeting = new Meeting(person, meetingName, meetingTime);
            MeetingCard meetingCard = new MeetingCard(expectedMeeting, 0);

            // same meeting, same index -> returns true
            MeetingCard copy = new MeetingCard(expectedMeeting, 0);
            assertTrue(meetingCard.equals(copy));

            // same object -> returns true
            assertTrue(meetingCard.equals(meetingCard));

            // null -> returns false
            assertFalse(meetingCard.equals(null));

            // different types -> returns false
            assertFalse(meetingCard.equals(0));

            // different meeting, same index -> returns false
            Person differentPerson = new PersonBuilder().withMeeting("differentName", "2018-01-01 00:00").build();
            for (Meeting meeting : differentPerson.getMeetings()) {
                meetingName = meeting.meetingName;
                meetingTime = meeting.value;
            }
            Meeting differentMeeting = new Meeting(differentPerson, meetingName, meetingTime);
            assertFalse(meetingCard.equals(new MeetingCard(differentMeeting, 0)));

            // same meeting, different index -> returns false
            assertFalse(meetingCard.equals(new MeetingCard(expectedMeeting, 1)));
        } catch (IllegalValueException e) {
            throw new AssertionError("Time format should be correct");
        }
    }

    /**
     * Asserts that {@code meetingCard} displays the details of {@code expectedMeeting} correctly and matches
     * {@code expectedId}.
     */
    private void assertMeetingDisplay(MeetingCard meetingCard, Meeting expectedMeeting, int expectedId) {
        guiRobot.pauseForHuman();

        MeetingCardHandle meetingCardHandle = new MeetingCardHandle(meetingCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", meetingCardHandle.getId());

        // verify meeting details are displayed correctly
        assertCardDisplaysMeeting(expectedMeeting, meetingCardHandle);
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedMeeting}.
     */
    public static void assertCardDisplaysMeeting(Meeting expectedMeeting, MeetingCardHandle actualCard) {
        assertEquals(expectedMeeting.getPerson().getName().fullName, actualCard.getName());
        assertEquals(expectedMeeting.meetingName, actualCard.getMeetingName());
        assertEquals(expectedMeeting.value, actualCard.getMeetingTime());
    }

```
