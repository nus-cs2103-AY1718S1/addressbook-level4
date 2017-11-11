# Choony93
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
        // associated google map page of a person
        postNow(gmapEventStub);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertTrue(browserPanelHandle.getLoadedUrl().toString(), browserPanelHandle.getLoadedUrl()
                .toString().contains(GOOGLE_GMAP_URL_PREFIX));
```
###### /java/seedu/address/logic/parser/ThemeCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new ThemeCommand(Index.fromOneBased(1)));

        assertParseSuccess(parser, "caspian", new ThemeCommand("caspian"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
```
###### /java/seedu/address/logic/parser/GmapCommandParserTest.java
``` java
    private GmapCommandParser parser = new GmapCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new GmapCommand(INDEX_FIRST_PERSON));

        GmapCommand expectedFindCommand =
            new GmapCommand(new NameConsistsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
    }
```
###### /java/seedu/address/logic/commands/ImportCommandTest.java
``` java
public class ImportCommandTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlUtilTest/");
    private static final String TEST_VALID_BOOK = "validAddressBook.xml";
    private static final String TEST_INVALID_BOOK = "empty.xml";

    private static final String PATH_CURRENT_DIR = (System.getProperty("user.dir"))
        .replaceAll("/", File.separator);
    private static final String PATH_VALID_ABSOLUTE = PATH_CURRENT_DIR
        + TEST_DATA_FOLDER + TEST_VALID_BOOK;
    private static final String PATH_VALID_RELATIVE = TEST_DATA_FOLDER + TEST_VALID_BOOK;
    private static final String PATH_INVALID_MISSING = TEST_DATA_FOLDER;
    private static final String PATH_INVALID_FORMAT = TEST_DATA_FOLDER + TEST_INVALID_BOOK;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Executes the given valid paths, confirms that <br>
     * - the result message verifies path is valid. <br>
     */
    @Test
    public void execute_validAddressbook_import() {

        /*
        Note:
        Absolute path testing will temporarily be disabled due to live Travis pathing restrictions.
        Test passes on appveyor only.
        */
        /*
        String expectedFirstMessage = String.format(MESSAGE_IMPORT_SUCCESS, PATH_VALID_ABSOLUTE);
        ImportCommand importFirstCommand = new ImportCommand(PATH_VALID_ABSOLUTE);
        assertCommandSuccess(importFirstCommand, expectedFirstMessage);
         */

        String expectedSecondMessage = String.format(MESSAGE_IMPORT_SUCCESS, PATH_VALID_RELATIVE);
        ImportCommand importSecondCommand = new ImportCommand(PATH_VALID_RELATIVE);
        assertCommandSuccess(importSecondCommand, expectedSecondMessage);
    }

    @Test
    public void execute_invalidAddressbook_import() {
        String expectedFirstMessage = String.format(MESSAGE_INVALID_IMPORT_FILE_ERROR, PATH_INVALID_MISSING);
        ImportCommand importFirstCommand = new ImportCommand(PATH_INVALID_MISSING);
        assertCommandFailure(importFirstCommand, model, expectedFirstMessage);

        String expectedSecondMessage = String.format(MESSAGE_INVALID_XML_FORMAT_ERROR, PATH_INVALID_FORMAT);
        ImportCommand importSecondCommand = new ImportCommand(PATH_INVALID_FORMAT);
        assertCommandFailure(importSecondCommand, model, expectedSecondMessage);
    }

    @Test
    public void equals() {
        ImportCommand importFirstCommand = new ImportCommand(PATH_VALID_ABSOLUTE);
        ImportCommand importSecondCommand = new ImportCommand(PATH_VALID_RELATIVE);

        // same object -> returns true
        assertTrue(importFirstCommand.equals(importFirstCommand));

        // different types -> returns false
        assertFalse(importFirstCommand.equals(1));

        // null -> returns false
        assertFalse(importFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(importFirstCommand.equals(importSecondCommand));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     */
    public static void assertCommandSuccess(Command command, String expectedMessage) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
```
