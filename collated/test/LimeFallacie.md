# LimeFallacie
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommand.
 */
public class ImportCommandTest {
    private Model model;
    private ImportCommand importCommand;
    private Storage storage;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        storage = new TypicalStorage().setUp();
    }

    /**
     * test if corrupted file will fail import
     */
    @Ignore
    public void execute_importCorruptedFile_fails() {
        importCommand = new ImportCommand("src\\test\\resources\\data\\testCorruptedFile.xml");
        importCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        assertCommandFailure(importCommand, model, ImportCommand.MESSAGE_WRITE_ERROR);
    }
}

```
###### \java\seedu\address\logic\commands\ListCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;
    private Storage storage;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        storage = new TypicalStorage().setUp();

    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        listCommand = new ListCommand("all");
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        assertCommandSuccess(listCommand, model, String.format(ListCommand.MESSAGE_SUCCESS, "."), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        listCommand = new ListCommand("all");
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, String.format(ListCommand.MESSAGE_SUCCESS, "."), expectedModel);
    }

    @Test
    public void execute_listFriends_success() {
        listCommand = new ListCommand("friends");
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        assertCommandSuccess(listCommand, model,
                String.format(ListCommand.MESSAGE_SUCCESS, " with friends tag."),
                expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {
    private Model model;
    private Storage storage;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        storage = new TypicalStorage().setUp();
    }

    @Test
    public void execute_sortByName_success() throws Exception {
        SortCommand sortCommandName = new SortCommand("name");
        sortCommandName.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sort("name");

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, SORT_TYPE_NAME);
        assertCommandSuccess(sortCommandName, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByPhone_success() throws Exception {
        SortCommand sortCommandPhone = new SortCommand("phone");
        sortCommandPhone.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sort("phone");

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, (SORT_TYPE_PHONE + " number"));
        assertCommandSuccess(sortCommandPhone, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByEmail_success() throws Exception {
        SortCommand sortCommandEmail = new SortCommand("email");
        sortCommandEmail.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sort("email");

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, SORT_TYPE_EMAIL);
        assertCommandSuccess(sortCommandEmail, model, expectedMessage, expectedModel);
    }
}
```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        assertParseSuccess(parser, "data\\circlesExportTest.xml", new ExportCommand("data\\circlesExportTest.xml"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        assertParseSuccess(parser, "data\\circlesImportTest.xml", new ImportCommand("data\\circlesImportTest.xml"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ListCommandParserTest.java
``` java
public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, "all", new ListCommand("all"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "name", new SortCommand(SORT_TYPE_NAME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\testutil\TypicalSortTypes.java
``` java
/**
 * A utility class containing a list of {@code sortType} Strings to be used in tests.
 */
public class TypicalSortTypes {
    public static final String SORT_TYPE_NAME = "name";
    public static final String SORT_TYPE_PHONE = "phone";
    public static final String SORT_TYPE_EMAIL = "email";
}
```
###### \java\seedu\address\testutil\TypicalStorage.java
``` java
/**
 * A utility class containing a constructor for a dummy storage object
 */
public class TypicalStorage {

    public StorageManager setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage("data\\ab");
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage("data\\prefs");
        StorageManager storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
        return storageManager;
    }
}
```
###### \java\systemtests\SortCommandSystemTest.java
``` java
public class SortCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void sort() {
        String command;
        Model expectedModel = getModel();

        /* Case: sort the list by name -> sorted */
        command = SortCommand.COMMAND_WORD + " " + ARGUMENT_NAME;
        try {
            expectedModel.sort(ARGUMENT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_CONTACTS_TO_SORT);
        } catch (DuplicatePersonException dpe) {
            assertCommandFailure(command, MESSAGE_USAGE);
        }
        assertCommandSuccess(command, expectedModel, String.format(MESSAGE_SORT_SUCCESS, ARGUMENT_NAME));

        /* Case: sort the list by email -> sorted */
        command = SortCommand.COMMAND_WORD + " " + ARGUMENT_EMAIL;
        try {
            expectedModel.sort(ARGUMENT_EMAIL);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_CONTACTS_TO_SORT);
        } catch (DuplicatePersonException dpe) {
            assertCommandFailure(command, MESSAGE_USAGE);
        }
        assertCommandSuccess(command, expectedModel, String.format(MESSAGE_SORT_SUCCESS, ARGUMENT_EMAIL));

        /* Case: invalid arguments -> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        /* Case: sort from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assertCommandFailure(SortCommand.COMMAND_WORD + " " + ARGUMENT_NAME,
                MESSAGE_NO_CONTACTS_TO_SORT);

    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
