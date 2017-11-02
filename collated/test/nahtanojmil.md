# nahtanojmil
###### \java\guitests\guihandles\GraphPanelHandle.java
``` java
/**
 * Provides a handle to the graph of a person in the person list panel.
 */
public class GraphPanelHandle extends NodeHandle<Node> {

    public static final String GRAPH_DISPLAY_ID = "#lineChart";
    private ReadOnlyPerson person;

    public GraphPanelHandle(Node graphPanelNode) {
        super(graphPanelNode);
    }

    /**
     * Returns the graph in the graph panel display.
     */
    public void getGraph() {
    // TODO: get the graph of the person
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("a lot of remarks").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(remarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(remarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand , model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        ReadOnlyPerson filteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(filteredList).withRemark("a lot of remarks").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(remarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBounds = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBounds, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBounds = INDEX_SECOND_PERSON;
        //shows that outOfBounds is still within bounds of address book list
        assertTrue(outOfBounds.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBounds, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand stdCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand toCompareCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(stdCommand.equals(toCompareCommand));

        //same object -> returns true
        assertTrue(stdCommand.equals(stdCommand));

        //null -> return false
        assertFalse(stdCommand.equals(null));

        //different types -> return false
        assertFalse(stdCommand.equals(new HelpCommand()));

        //different index -> return false
        assertFalse(stdCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        //different remarks -> return false
        assertFalse(stdCommand.equals((new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB)))));
    }

    /**
     *
     * @return an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Remark string) {
        RemarkCommand remarkCommand = new RemarkCommand(index, string);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remarkCommandWord_returnsRemarkCommand() throws Exception {
        final Remark remarks = new Remark("I'm so done.");
        RemarkCommand testCommand = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remarks.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remarks), testCommand);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remarkCommandAlias_returnsRemarkCommand() throws Exception {
        final Remark remarks = new Remark("I'm so done.");
        RemarkCommand testCommand = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remarks.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remarks), testCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseRemark_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRemark(Optional.of(INVALID_REMARK));
    }

    @Test
    public void parseRemark_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemark_validValue_returnsEmail() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        Optional<Remark> actualRemark = ParserUtil.parseRemark(Optional.of(VALID_REMARK));

        assertEquals(expectedRemark, actualRemark.get());
    }
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_success() throws Exception {
        final Remark remarkString = new Remark("I'm so good");
        Index index;
        String userInput;

        //have remarks
        index = INDEX_FIRST_PERSON;
        userInput = index.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remarkString;
        RemarkCommand testCommand = new RemarkCommand(index, remarkString);
        assertParseSuccess(parser, userInput, testCommand);

        //no remarks
        userInput = index.getOneBased() + " " + PREFIX_REMARK.toString();
        RemarkCommand nextTestCommand = new RemarkCommand(index, new Remark(""));
        assertParseSuccess(parser, userInput, nextTestCommand);
    }

    @Test
    public void parse_noSpecifiedField_failure() throws Exception {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, errorMessage);
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {

        this.person.setRemark(new Remark(remark));
        return this;
    }
```
###### \java\seedu\address\ui\GraphPanelTest.java
``` java
public class GraphPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private GraphPanel graphPanel;
    private GraphPanelHandle graphPanelHandle;

    @Before
    public void setUp() {
        try {
            guiRobot.interact(() -> graphPanel = new GraphPanel(TYPICAL_PERSONS));
            uiPartRule.setUiPart(graphPanel);
            graphPanelHandle = new GraphPanelHandle(graphPanel.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Test
    public void display() throws Exception {
        // select ALICE
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));;
        // select BOB
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
    }
}
```
