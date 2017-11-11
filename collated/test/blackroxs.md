# blackroxs
###### \java\seedu\room\logic\commands\CommandTestUtil.java
``` java
    public static final String INVALID_TAG = "testing"; // 'testing' does not exist inside the room book
    public static final String INVALID_FILE = "testing.xml"; // 'testing.xml' does not exist inside the temp folder

```
###### \java\seedu\room\logic\commands\ImportCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommandTest.
 */
public class ImportCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlResidentBookStorage residentBookStorage = new XmlResidentBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        XmlEventBookStorage eventBookStorage = new XmlEventBookStorage(getTempFilePath("bc"));
        storageManager = new StorageManager(residentBookStorage, eventBookStorage, userPrefsStorage);

        try {
            storageManager.backupResidentBook(getTypicalImportFile());
        } catch (IOException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void fileNotFound() {
        ImportCommand command = prepareCommand(INVALID_FILE);
        assertCommandFailure(command, model, ImportCommand.MESSAGE_ERROR);
    }

    @Test
    public void validImport() {
        ClassLoader classLoader = getClass().getClassLoader();
        File backup = new File(classLoader.getResource("backup.xml").getFile());
        assertNotNull(backup);

        ImportCommand command = prepareCommand(backup.getAbsolutePath());
        ResidentBook correctVersion = TypicalImportFile.getCombinedResult();

        ModelManager expectedModel = new ModelManager(correctVersion, new UserPrefs());

        assertCommandSuccess(command, model, TYPICAL_IMPORT_SUCCESS_MESSAGE, expectedModel);
    }

    /**
     * Returns an {@code ImportCommand} with parameters {@code filePath}
     */
    private ImportCommand prepareCommand(String filePath) {
        ImportCommand command = new ImportCommand(filePath);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\room\logic\commands\RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_tagNameValid_success() throws Exception {
        RemoveTagCommand removeTagCommand = prepareCommand(VALID_TAG_FRIEND);
        String expectedMessage = RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.removeTag(new Tag(VALID_TAG_FRIEND));

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNameInvalid() throws Exception {
        RemoveTagCommand removeTagCommand = prepareCommand(INVALID_TAG);
        String expectedMessage = RemoveTagCommand.MESSAGE_REMOVE_TAG_NOT_EXIST;

        assertCommandFailure(removeTagCommand, model, expectedMessage);
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code tagName}
     */
    private RemoveTagCommand prepareCommand(String tagName) {
        RemoveTagCommand command = new RemoveTagCommand(tagName);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\room\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\room\logic\parser\RemoveTagParserTest.java
``` java
public class RemoveTagParserTest {

    private static final String MESSAGE_EMPTY_TAG_INPUT = "";
    private static final String MESSAGE_EMPTY_STRING_INPUT = "     ";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

    private RemoveTagParser parser = new RemoveTagParser();

    @Test
    public void parse_missingParts_failure() {
        // no tagName specified
        assertParseFailure(parser, MESSAGE_EMPTY_TAG_INPUT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyString_failure() {
        // only empty string
        assertParseFailure(parser, MESSAGE_EMPTY_STRING_INPUT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validFields_success() {
        String userInput = VALID_TAG_FRIEND;
        RemoveTagCommand expectedCommand = new RemoveTagCommand(userInput);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\room\storage\StorageManagerTest.java
``` java
    private void setupTempPictureFile() throws IOException {
        File source = new File(storageManager.getDirAbsolutePath()
                + File.separator + Picture.FOLDER_NAME);
        File dest = new File(storageManager.getDirAbsolutePath()
                + File.separator + Picture.FOLDER_NAME + "_backup");

        source.mkdirs();
        dest.mkdirs();
        source.deleteOnExit();
        dest.deleteOnExit();
    }


```
###### \java\seedu\room\storage\StorageManagerTest.java
``` java
    @Test
    public void eventsBookReadSave() throws Exception {
        EventBook original = getTypicalEventBook();
        storageManager.saveEventBook(original);
        ReadOnlyEventBook retrieved = storageManager.readEventBook().get();
        assertEquals(original, new EventBook(retrieved));
    }

    @Test
    public void handleBackupResidentBook() throws Exception {
        ResidentBook original = getTypicalResidentBook();
        storageManager.saveResidentBook(original);
        ReadOnlyResidentBook retrieved = storageManager.readResidentBook().get();

        storageManager.backupResidentBook(retrieved);
        ReadOnlyResidentBook backup = storageManager.readBackupResidentBook().get();

        assertEquals(new ResidentBook(retrieved), new ResidentBook(backup));
    }

    @Test
    public void handleBackupEventBook() throws Exception {
        EventBook original = getTypicalEventBook();
        storageManager.saveEventBook(original);
        ReadOnlyEventBook retrieved = storageManager.readEventBook().get();

        storageManager.backupEventBook(retrieved);
        ReadOnlyEventBook backup = storageManager.readBackupEventBook().get();

        assertEquals(new EventBook(retrieved), new EventBook(backup));
    }

    @Test
    public void handleNoBackupImages() throws Exception {
        storageManager.backupImages();

        assertTrue(!new File(storageManager.getDirAbsolutePath()
                + File.separator + Picture.FOLDER_NAME).exists());

        assertTrue(!new File(storageManager.getDirAbsolutePath()
                + File.separator + Picture.FOLDER_NAME + "_backup").exists());

    }

    @Test
    public void handleBackupImagesValid() throws Exception {
        setupTempPictureFile();

        assertTrue(new File(storageManager.getDirAbsolutePath()
                + File.separator + Picture.FOLDER_NAME).exists());
        assertTrue(new File(storageManager.getDirAbsolutePath()
                + File.separator + Picture.FOLDER_NAME + "_backup").exists());
    }


}
```
###### \java\seedu\room\testutil\TypicalImportFile.java
``` java

/**
 * A utility class containing a list of {@code Person} objects to be used in tests for import.
 */
public class TypicalImportFile {
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName("Amy Parker")
            .withRoom("08-115").withEmail("amy_parker123@example.com")
            .withPhone("84455255")
            .withTags("level8").build();
    public static final ReadOnlyPerson BERNARD = new PersonBuilder().withName("Bernard Maddison")
            .withRoom("02-203")
            .withEmail("bmad@example.com").withPhone("98885432")
            .withTags("owesMoney", "professor").build();
    public static final ReadOnlyPerson CARLO = new PersonBuilder().withName("Carlo Henn").withPhone("95222563")
            .withEmail("carlo.h@example.com").withRoom("04-300C").build();
    public static final ReadOnlyPerson DANIELLE = new PersonBuilder().withName("Danielle Chua").withPhone("82252533")
            .withEmail("chua_jj_danielle@example.com").withRoom("06-120").build();

    public static final String TYPICAL_IMPORT_SUCCESS_MESSAGE = ImportCommand.MESSAGE_SUCCESS + " Added: Amy Parker, Bernard Maddison, Carlo Henn, Danielle Chua";
    private TypicalImportFile() {
    } // prevents instantiation

    /**
     * @return an {@code ResidentBook} with typical person not found in original list.
     */
    public static ResidentBook getTypicalImportFile() {
        ResidentBook importFile = new ResidentBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                importFile.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return importFile;
    }

    public static ResidentBook getCombinedResult() {
        ResidentBook importFile = new ResidentBook();
        for (ReadOnlyPerson person : getCombinedList()) {
            try {
                importFile.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return importFile;
    }

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(AMY, BERNARD, CARLO, DANIELLE));
    }

    public static List<ReadOnlyPerson> getCombinedList() {
        return new ArrayList<>(Arrays.asList(AMY, BERNARD, CARLO, DANIELLE, ALICE, BENSON,
                CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

}
```
