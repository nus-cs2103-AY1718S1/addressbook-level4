# nahtanojmil
###### /java/guitests/guihandles/GraphPanelHandle.java
``` java
/**
 * Provides a handle to the graph of a person in the person list panel.
 */
public class GraphPanelHandle extends NodeHandle<Node> {

    public static final String GRAPH_DISPLAY_ID = "#graphPanelPlaceholder";
    private static final String TAB_PANEL_ID = "#tabPaneGraphs";

    private final TabPane tabPane;

    public GraphPanelHandle(Node graphPanelNode) {
        super(graphPanelNode);
        this.tabPane = getChildNode(TAB_PANEL_ID);
    }

    public TabPane getTabPanel() {
        return tabPane;
    }



}
```
###### /java/guitests/guihandles/HomePanelHandle.java
``` java
/**
 * Provides a handle to the homePanel.
 */
public class HomePanelHandle extends NodeHandle<Node> {

    public static final String HOMEPANEL_DISPLAY_ID = "#homePanelPlaceholder";

    public HomePanelHandle(Node homePanelNode) {
        super(homePanelNode);
    }

}
```
###### /java/seedu/address/logic/commands/HomeCommandTest.java
``` java
public class HomeCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() throws CommandException {
        CommandResult result = new HomeCommand().execute();
        assertEquals(MESSAGE_SELECT_HOME_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof HomeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}



```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
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
###### /java/seedu/address/logic/commands/TabCommandTest.java
``` java
public class TabCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();


    @Test
    public void execute_help_success() throws CommandException {
        CommandResult result = new TabCommand(INDEX_ONE).execute();
        String expectedMessage = "Selected Tab: " + INDEX_ONE.getOneBased();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof JumpToTabRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remarkCommandWord_returnsRemarkCommand() throws Exception {
        final Remark remarks = new Remark("I'm so done.");
        RemarkCommand testCommand = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remarks.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remarks), testCommand);
    }


    @Test
    public void parseCommand_remarkCommandAlias_returnsRemarkCommand() throws Exception {
        final Remark remarks = new Remark("I'm so done.");
        RemarkCommand testCommand = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remarks.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remarks), testCommand);
    }

    @Test
    public void parseCommand_tabCommandWord_returnsTabCommand() throws Exception {
        assertTrue(parser.parseCommand(TabCommand.COMMAND_WORD + " 1") instanceof TabCommand);
    }

    @Test
    public void parseCommand_homeCommandWord_returnsHomeCommand() throws Exception {
        assertTrue(parser.parseCommand("home") instanceof HomeCommand);
    }
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
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
###### /java/seedu/address/logic/parser/RemarkCommandParserTest.java
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
###### /java/seedu/address/logic/parser/TabCommandParserTest.java
``` java
public class TabCommandParserTest {

    private TabCommandParser parser = new TabCommandParser();

    @Test
    public void parse_validArgs_returnsTabCommand() {
        assertParseSuccess(parser, "1", new TabCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {

        this.person.setRemark(new Remark(remark));
        return this;
    }
```
###### /java/seedu/address/ui/GraphPanelTest.java
``` java
public class GraphPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private GraphPanel graphPanel;
    private GraphPanelHandle graphPanelHandle;


    @Before
    public void setUp() {
        try {
            Model model = new ModelManager();
            Logic logic = new LogicManager(model);
            guiRobot.interact(() -> graphPanel = new GraphPanel(logic));
            uiPartRule.setUiPart(graphPanel);
            graphPanelHandle = new GraphPanelHandle(graphPanel.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Test
    public void display() throws Exception {
        // select ALICE
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        XYChart.Series<String, Double> testSeries = new XYChart.Series<>();
        for (ReadOnlyPerson people : TYPICAL_PERSONS) {
            if (ALICE.getFormClass().equals(people.getFormClass())) {
                testSeries.getData().add(new XYChart.Data<>(people.getName().toString(),
                        Double.parseDouble(people.getGrades().toString())));
            }
        }
        assertEquals(testSeries.getData().get(0).getXValue(), TYPICAL_PERSONS.get(0).getName().toString());

        //select BOB
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertFalse(BOB.getFormClass().equals(TYPICAL_PERSONS.get(0).getFormClass()));
    }

    @Test
    public void changeTab() throws Exception {
        postNow(new JumpToTabRequestEvent(INDEX_ONE));
        assertTrue(graphPanelHandle.getTabPanel().getSelectionModel().isSelected(0));
    }
}
```
