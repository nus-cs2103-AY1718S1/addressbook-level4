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
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
public class ImportCommandTest {
    private Model model;
    private List<ReadOnlyPerson> list1;
    private List<ReadOnlyPerson> list2;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        list1 = model.getAddressBook().getPersonList();
        list2 = new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL));
    }

    @Test
    public void equals() {

        ImportCommand test1Import = new ImportCommand(list1);
        ImportCommand test2Import = new ImportCommand(list2);

        //same Object -> return true
        assertTrue(test1Import.equals(test1Import));

        //same attributes -> return true
        ImportCommand test1ImportCopy = new ImportCommand(list1);
        assertTrue(test1Import.equals(test1ImportCopy));

        //Different object -> return false
        assertFalse(test1Import.equals(false));

        //null -> return false
        assertFalse(test1Import.equals(null));

        //Different attributes -> return false
        assertFalse(test1Import.equals(test2Import));
    }

    @Test
    public void import_toEmptyAddressBook_success() throws DuplicatePersonException {

        Model testModel = new ModelManager(new AddressBook(), new UserPrefs());
        AddressBook testAddressBook = new AddressBook();
        testAddressBook.setPersons(list2);
        Model testModelAliceBensonCarl = new ModelManager(testAddressBook, new UserPrefs());

        //Import list of 3 people into empty addressbook
        assertCommandSuccess(prepareCommand(list2, testModel), testModel,
                String.format(ImportCommand.MESSAGE_SUCCESS, 0), testModelAliceBensonCarl);

        //Import list of 7 people into addressbook with 3 people inside.
        assertCommandSuccess(prepareCommand(list1, testModel), testModel,
                String.format(ImportCommand.MESSAGE_SUCCESS, 3), model);

    }

    /**
     * Generates a new {@code ExportCommand} which upon execution, exports the contacts in {@code model}.
     */
    private ImportCommand prepareCommand(List list, Model model) {
        ImportCommand command = new ImportCommand(list);
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
    public void execute_removeTag_success() throws IllegalValueException, NoSuchTagException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand("friends");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(new Tag("friends"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeTagInvalidIndex() throws IllegalValueException {
        //addressbook does not have specified Index.
        assertCommandFailure(prepareCommand(10, "prospective"), model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_removeNonExistantTag_failure() throws IllegalValueException {
        //addressbook does not contain enemy tag.
        assertCommandFailure(prepareCommand("enemy"), model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND);

        //Elle has family tag but no enemy tag
        assertCommandFailure(prepareCommand(4, "enemy"), model,
                String.format(RemoveTagCommand.MESSAGE_TAG_NOT_FOUND_IN, 5));

        //Carl has no tags
        assertCommandFailure(prepareCommand(2, "colleagues"), model,
                String.format(RemoveTagCommand.MESSAGE_TAG_NOT_FOUND_IN, 3));

        //Benson has two tags, owesMoney and friend but no family tag.
        assertCommandFailure(prepareCommand(1, "family"), model,
                String.format(RemoveTagCommand.MESSAGE_TAG_NOT_FOUND_IN, 2));

    }

    @Test
    public void execute_removeSingleTag_success() throws IllegalValueException, NoSuchTagException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand(4, "family");
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.removeTag(Index.fromZeroBased(4), new Tag("family"));
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

    @Test
    public void equals() {
        Comparator namesort = ReadOnlyPerson.NAMESORTASC;
        Comparator phonesort = ReadOnlyPerson.PHONESORTASC;

        SortCommand sortFirstCommand = new SortCommand(namesort);
        SortCommand sortSecondCommand = new SortCommand(phonesort);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(new ClearCommand()));

        // different types -> return false
        assertFalse(sortFirstCommand.equals(true));

        // different comparator -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
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
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_removetag() throws Exception {
        String tag = "foo";
        RemoveTagCommand command = (RemoveTagCommand) parser.parseCommand(
                RemoveTagCommand.COMMAND_WORD + " " + "all" + " " + tag);
        assertEquals(new RemoveTagCommand(new Tag(tag)), command);


        RemoveTagCommand command2 = (RemoveTagCommand) parser.parseCommand(
                RemoveTagCommand.COMMAND_WORD + " " + "5" + " " + tag);
        assertEquals(new RemoveTagCommand(Index.fromOneBased(5), new Tag(tag)), command2);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + "name asc");
        assertEquals(new SortCommand(ReadOnlyPerson.NAMESORTASC), command);
    }

    @Test
    public void parseCommand_export() throws Exception {
        ExportCommand command = (ExportCommand) parser.parseCommand(
                ExportCommand.COMMAND_WORD + " " + "output.vcf");
        assertEquals(new ExportCommand("output.vcf"), command);
    }

    @Test
    public void parseCommand_import() throws Exception {
        ImportCommand command = (ImportCommand) parser.parseCommand(ImportCommand.COMMAND_WORD + " "
                        + ImportCommandParserTest.TEST_FILE_DIRECTORY + "ValidTypicalAddressBook.xml");
        assertEquals(new ImportCommand(getTypicalAddressBook().getPersonList()), command);
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
    public void parse_missingFile_throwsParseException() {
        //Missing .xml
        assertParseFailure(parser, TEST_FILE_DIRECTORY + "missing.xml",
                ImportCommand.MESSAGE_FILE_NOT_FOUND);
        //Missing .vcf
        assertParseFailure(parser, TEST_FILE_DIRECTORY + "missing.vcf",
                ImportCommand.MESSAGE_FILE_NOT_FOUND);
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

        //Valid vcf file with data of getTypicalAddressBook.
        assertParseSuccess(parser, TEST_FILE_DIRECTORY + "ValidTypicalAddressBook.vcf",
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
    public void parse_noRange_throwsParseException() {
        //1 tags no range
        assertParseFailure(parser, "friends", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        //2 tags no range
        assertParseFailure(parser, "friends owesMoney", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        //3 tags no range
        assertParseFailure(parser, "friends owesMoney prospective", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));
    }

    @Test
    public void parse_multipleTag_throwsParseException() {
        //all range 2 tags
        assertParseFailure(parser, "all owesMoney prospective", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));

        assertParseFailure(parser, "5 owesMoney prospective", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, ".123\\5", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws IllegalValueException {
        // no leading and trailing whitespaces
        RemoveTagCommand expectedCommand = new RemoveTagCommand (new Tag("friends"));
        assertTrue(parser.parse("all friends") instanceof RemoveTagCommand);
        assertParseSuccess(parser, "all friends", expectedCommand);

        // no leading and trailing whitespaces but with Index.
        RemoveTagCommand expectedCommand2 = new RemoveTagCommand (Index.fromZeroBased(0), new Tag(
                "enemy"));
        assertTrue(parser.parse("1 enemy") instanceof RemoveTagCommand);
        assertParseSuccess(parser, "1 enemy", expectedCommand2);
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
