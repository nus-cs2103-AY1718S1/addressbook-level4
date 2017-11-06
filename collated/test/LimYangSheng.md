# LimYangSheng
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
