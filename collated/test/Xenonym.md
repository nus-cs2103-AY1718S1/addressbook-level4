# Xenonym
###### \java\seedu\address\logic\CommandHistoryTest.java
``` java
    @Test
    public void clear() {
        history.add("list");
        history.add("clear");
        history.clear();
        assertEquals(Collections.EMPTY_LIST, history.getHistory());
    }
```
###### \java\seedu\address\logic\commands\BackupCommandTest.java
``` java
public class BackupCommandTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private BackupCommand backupCommand;
    private XmlAddressBookStorage addressBookStorage;

    @Before
    public void setUp() {
        try {
            backupCommand = new BackupCommand();
            addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
            addressBookStorage.saveAddressBook(getTypicalAddressBook());
            JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));

            backupCommand.setData(null, null, null, new StorageManager(addressBookStorage, userPrefsStorage));
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    private String getTempFilePath(String filename) {
        return testFolder.getRoot().getPath() + filename;
    }

    @Test
    public void execute() throws Exception {
        CommandResult result = backupCommand.execute();
        assertEquals(BackupCommand.MESSAGE_SUCCESS, result.feedbackToUser);

        byte[] original = Files.readAllBytes(Paths.get(addressBookStorage.getAddressBookFilePath()));
        byte[] backup = Files.readAllBytes(Paths.get(addressBookStorage.getBackupAddressBookFilePath()));
        assertTrue(Arrays.equals(original, backup));
    }
}
```
###### \java\seedu\address\logic\commands\ClearHistoryCommandTest.java
``` java
public class ClearHistoryCommandTest {
    private ClearHistoryCommand clearHistoryCommand;
    private CommandHistory history;
    private UndoRedoStack undoRedoStack;

    @Before
    public void setUp() {
        history = new CommandHistory();
        undoRedoStack = new UndoRedoStack();
        clearHistoryCommand = new ClearHistoryCommand();
        clearHistoryCommand.setData(new ModelManager(), history, undoRedoStack, new StorageStub());
    }

    @Test
    public void execute() throws Exception {
        history.add("command1");
        history.add("command2");
        undoRedoStack.push(new DummyCommand());
        undoRedoStack.push(new DummyUndoableCommand());
        clearHistoryCommand.execute();
        assertEquals(Collections.emptyList(), history.getHistory());
        assertFalse(undoRedoStack.canUndo());
        assertFalse(undoRedoStack.canRedo());
    }

    class DummyCommand extends Command {
        @Override
        public CommandResult execute() {
            return new CommandResult("");
        }
    }

    class DummyUndoableCommand extends UndoableCommand {
        @Override
        public CommandResult executeUndoableCommand() {
            return new CommandResult("");
        }
```
###### \java\seedu\address\logic\commands\ColourTagCommandTest.java
``` java
public class ColourTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_changeColour_success() throws Exception {
        ColourTagCommand command = new ColourTagCommand(new Tag("test"), "red");
        command.setData(model, null, null, null);

        String expectedMessage = String.format(ColourTagCommand.MESSAGE_COLOUR_TAG_SUCCESS, "test", "red");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Map<Tag, String> colours = new HashMap<>(expectedModel.getUserPrefs().getGuiSettings().getTagColours());
        colours.put(new Tag("test"), "red");
        expectedModel.getUserPrefs().getGuiSettings().setTagColours(colours);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_backup() throws Exception {
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_WORD) instanceof BackupCommand);
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_WORD + " 3") instanceof BackupCommand);
    }
    
    @Test
    public void parseCommand_clearhistory() throws Exception {
        assertTrue(parser.parseCommand(ClearHistoryCommand.COMMAND_WORD) instanceof ClearHistoryCommand);
        assertTrue(parser.parseCommand(ClearHistoryCommand.COMMAND_WORD + " 3") instanceof ClearHistoryCommand);
    }

    @Test
    public void parseCommand_colourtag() throws Exception {
        final Tag tag = new Tag("test");
        final String colour = "red";
        ColourTagCommand command = (ColourTagCommand) parser.parseCommand(ColourTagCommand.COMMAND_WORD
                + " test red");
        assertEquals(new ColourTagCommand(tag, colour), command);
    }
```
###### \java\seedu\address\logic\UndoRedoStackTest.java
``` java
    @Test
    public void clear() {
        undoRedoStack = prepareStack(Collections.singletonList(dummyUndoableCommandOne),
                Arrays.asList(dummyUndoableCommandOne, dummyUndoableCommandTwo));
        undoRedoStack.clear();
        assertFalse(undoRedoStack.canUndo());
        assertFalse(undoRedoStack.canRedo());
    }
```
###### \java\seedu\address\storage\JsonUserPrefsStorageTest.java
``` java
    private static Map<Tag, String> getSampleTagColours() {
        HashMap<Tag, String> sampleTagColours = new HashMap<>();
        try {
            sampleTagColours.put(new Tag("friends"), "red");
            sampleTagColours.put(new Tag("colleagues"), "green");
            sampleTagColours.put(new Tag("family"), "yellow");
            sampleTagColours.put(new Tag("neighbours"), "blue");
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }

        return sampleTagColours;
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void backupAddressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readBackupAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
        //Backup address book and read back
        xmlAddressBookStorage.backupAddressBook(original);
        readBack = xmlAddressBookStorage.readBackupAddressBook().get();
        assertEquals(original, new AddressBook(readBack));
```
###### \java\seedu\address\testutil\StorageStub.java
``` java
/**
 * A default storage stub that has all of the methods failing.
 */
public class StorageStub implements Storage {
    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        fail("This method should not be called.");
    }

    @Override
    public String getAddressBookFilePath() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        fail("This method should not be called.");
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        fail("This method should not be called.");
    }

    @Override
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        fail("This method should not be called.");
    }

    @Override
    public String getBackupAddressBookFilePath() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        fail("This method should not be called.");
    }

    @Override
    public String getUserPrefsFilePath() {
        fail("This method should not be called.");
        return null;
```
###### \java\seedu\address\logic\parser\ColourTagCommandParserTest.java
``` java
public class ColourTagCommandParserTest {

    private ColourTagCommandParser parser = new ColourTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "onlyonearg", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "&&@&C@B invalidtag", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsColourTagCommand() throws Exception {
        assertParseSuccess(parser, "friends red", new ColourTagCommand(new Tag("friends"), "red"));
    }
}
```
