# LimeFallacie
###### /java/seedu/address/logic/commands/ListCommandTest.java
``` java
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
###### /java/seedu/address/logic/commands/SortCommandTest.java
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
###### /java/seedu/address/logic/parser/ExportCommandParserTest.java
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
###### /java/seedu/address/logic/parser/ImportCommandParserTest.java
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
###### /java/seedu/address/logic/parser/ListCommandParserTest.java
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
###### /java/seedu/address/logic/parser/SortCommandParserTest.java
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
###### /java/seedu/address/testutil/TypicalSortTypes.java
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
###### /java/seedu/address/testutil/TypicalStorage.java
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
