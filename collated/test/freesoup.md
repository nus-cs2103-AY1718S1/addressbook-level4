# freesoup
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
public class ExportCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        String filepath1 = "/exports/test1/";
        String filepath2 = "/exports/test2/";

        ExportCommand test1Export = new ExportCommand(filepath1);
        ExportCommand test2Export = new ExportCommand(filepath2);

        //same Object -> return true
        assertTrue(test1Export.equals(test1Export));

        //same attributes -> return true
        ExportCommand test1ExportCopy = new ExportCommand(filepath1);
        assertTrue(test1Export.equals(test1ExportCopy));

        //Different object -> return false
        assertFalse(test1Export.equals(false));

        //null -> return false
        assertFalse(test1Export.equals(null));

        //Different attributes -> return false
        assertFalse(test1Export.equals(test2Export));
    }

    @Test
    public void export_emptyAddressBook_emptyBookError() throws PersonNotFoundException {
        Model emptyAddressBookModel = new ModelManager();
        assertCommandFailure(prepareCommand(
                "out.xml", emptyAddressBookModel), emptyAddressBookModel, ExportCommand.MESSAGE_EMPTY_BOOK);
    }

    @Test
    public void export_validAddressBook_success() {
        //Valid .xml export.
        assertCommandSuccess(prepareCommand("out.xml", model), model, ExportCommand.MESSAGE_SUCCESS, model);

        //Valid .vcf export
        assertCommandSuccess(prepareCommand("out.vcf", model), model, ExportCommand.MESSAGE_SUCCESS, model);
    }

    @After
    public void cleanUp() {
        //Deletes the files that were created.
        File xmlfile = new File("out.xml");
        File vcffile = new File("out.vcf");

        if (xmlfile.exists()) {
            xmlfile.delete();
        }

        if (vcffile.exists()) {
            vcffile.delete();
        }
    }

    /**
     * Generates a new {@code ExportCommand} which upon execution, exports the contacts in {@code model}.
     */
    private ExportCommand prepareCommand(String filePath, Model model) {
        ExportCommand command = new ExportCommand(filePath);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\commands\RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_emptyArg_throwsParseException() {
        RemoveTagCommandParser parser = new RemoveTagCommandParser();
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_removeTag_success() throws IllegalValueException, PersonNotFoundException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand("friends");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(new Tag("friends"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeSingleTag_success() throws IllegalValueException, PersonNotFoundException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand(5, "owesMoney");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(Index.fromOneBased(5), new Tag("owesMoney"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code RemoveTagCommand} which upon execution, removes all tag
     * matching given Tag in {@code model}.
     */
    private RemoveTagCommand prepareCommand(String tag) throws IllegalValueException {
        RemoveTagCommand command = new RemoveTagCommand(new Tag(tag));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code RemoveTagCommand} which upon execution, removes tag
     * corresponding to given Tag and Index in {@code model}.
     */
    private RemoveTagCommand prepareCommand(int index, String tag) throws IllegalValueException {
        RemoveTagCommand command = new RemoveTagCommand(Index.fromZeroBased(index), new Tag(tag));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
public class SortCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void sort_validComparator_success() {
        //valid sort for phone descending.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(ReadOnlyPerson.PHONESORTDSC);
        SortCommand expectedCommand = prepareCommand(ReadOnlyPerson.PHONESORTDSC, model);
        assertCommandSuccess(expectedCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Generates a new {@code ExportCommand} which upon execution, exports the contacts in {@code model}.
     */
    private SortCommand prepareCommand(Comparator comparator, Model model) {
        SortCommand command = new SortCommand(comparator);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty args
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noFileType_throwsParseException() {
        //only fileName
        assertParseFailure(parser, "output", ExportCommand.MESSAGE_WRONG_FILE_TYPE);

        //file without file extension
        assertParseFailure(parser, "output.", ExportCommand.MESSAGE_WRONG_FILE_TYPE);
    }



    @Test
    public void parse_validArgs_returnExportCommand() throws ParseException {
        //valid xml Export
        ExportCommand exportCommand = new ExportCommand("output.xml");
        assertTrue(parser.parse("output.xml") instanceof ExportCommand);
        assertParseSuccess(parser, "output.xml", exportCommand);

        //valid vcf Export
        ExportCommand exportCommand2 = new ExportCommand("output.vcf");
        assertTrue(parser.parse("output.vcf") instanceof ExportCommand);
        assertParseSuccess(parser, "output.vcf", exportCommand2);
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {
    public static final String TEST_FILE_DIRECTORY = "src/test/data/ImportCommandParserTest/";


    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_wrongFileFormat_throwsParseException() {
        //Wrong file type
        assertParseFailure(parser, "wrong.asd", ImportCommand.MESSAGE_WRONG_FORMAT);

        //No file type
        assertParseFailure(parser, "wrong", ImportCommand.MESSAGE_WRONG_FORMAT);
    }

    @Test
    public void parse_corruptedFile_throwsParseException() {
        //Corrupted xml
        assertParseFailure(parser,
                TEST_FILE_DIRECTORY + "CorruptedXML.xml", ImportCommand.MESSAGE_FILE_CORRUPT);

        //Corrupted vcf
        assertParseFailure(parser,
                TEST_FILE_DIRECTORY + "CorruptedVCF.vcf", ImportCommand.MESSAGE_FILE_CORRUPT);
    }

    @Test
    public void parse_noArgsCancelImport_throwsParseException() {
        //Opens file explorer and choose to not select a file.
        assertParseFailure(parser, " ", ImportCommand.MESSAGE_IMPORT_CANCELLED);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ImportFileChooseEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void parse_validArgs_success() {
        //Valid xml file with data of getTypicalAddressBook.
        assertParseSuccess(parser, TEST_FILE_DIRECTORY + "ValidTypicalAddressBook.xml",
                new ImportCommand(getTypicalAddressBook().getPersonList()));
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParserTest.java
``` java
public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArg_throwsParseException() {
        assertParseFailure(parser, "friends owesMoney", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws IllegalValueException {
        // no leading and trailing whitespaces
        RemoveTagCommand expectedCommand = new RemoveTagCommand (new Tag("friends"));
        assertTrue(parser.parse("friends") instanceof RemoveTagCommand);
        assertParseSuccess(parser, "friends", expectedCommand);

        // no leading and trailing whitespaces but with Index.
        RemoveTagCommand expectedCommand2 = new RemoveTagCommand (Index.fromZeroBased(0), new Tag(
                "enemy"));
        assertTrue(parser.parse("1 enemy") instanceof RemoveTagCommand);
        assertParseSuccess(parser, " 1 enemy", expectedCommand2);
    }

}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty args
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwParseException() {
        //only FIELD
        assertParseFailure(parser, "name", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Only ORDER
        assertParseFailure(parser, "asc", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Only Order correct
        assertParseFailure(parser, "name qwe", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        //Only field correct
        assertParseFailure(parser, "cool dsc", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() throws ParseException {
        // name ascending
        SortCommand expectedCommand = new SortCommand(ReadOnlyPerson.NAMESORTASC);
        assertTrue(parser.parse("name asc") instanceof SortCommand);
        assertParseSuccess(parser, "name asc", expectedCommand);

        // phone descending
        SortCommand expectedCommand2 = new SortCommand(ReadOnlyPerson.PHONESORTDSC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "phone dsc", expectedCommand2);

        // name descending
        SortCommand expectedCommand3 = new SortCommand(ReadOnlyPerson.NAMESORTDSC);
        assertTrue(parser.parse("name dsc") instanceof SortCommand);
        assertParseSuccess(parser, "name dsc", expectedCommand3);

        // email descending
        SortCommand expectedCommand4 = new SortCommand(ReadOnlyPerson.EMAILSORTDSC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "email dsc", expectedCommand4);

        // phone ascending
        SortCommand expectedCommand5 = new SortCommand(ReadOnlyPerson.PHONESORTASC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "phone asc", expectedCommand5);

        // email ascending
        SortCommand expectedCommand6 = new SortCommand(ReadOnlyPerson.EMAILSORTASC);
        assertTrue(parser.parse("phone dsc") instanceof SortCommand);
        assertParseSuccess(parser, "email asc", expectedCommand6);
    }
}
```
