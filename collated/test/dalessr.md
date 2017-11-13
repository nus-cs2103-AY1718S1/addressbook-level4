# dalessr
###### /java/guitests/guihandles/PersonDetailsPanelHandle.java
``` java
/**
 * A handler for the {@code PersonDetailsPanel} of the UI.
 */
public class PersonDetailsPanelHandle extends NodeHandle<Node> {

    public static final String GRIDPANE_ID = "#personDetailsGrid";
    public static final String NAME_ID = "#nameLabel";
    public static final String PHONE_ID = "#phoneLabel";
    public static final String EMAIL_ID = "#emailLabel";
    public static final String ADDRESS_ID = "#addressLabel";

    public final GridPane gridPane;
    public final Label nameLb;
    public final Label phoneLb;
    public final Label emailLb;
    public final Label addressLb;

    public PersonDetailsPanelHandle(Node cardNode) {
        super(cardNode);
        this.gridPane = getChildNode(GRIDPANE_ID);
        this.nameLb = getChildNode(NAME_ID);
        this.phoneLb = getChildNode(PHONE_ID);
        this.emailLb = getChildNode(EMAIL_ID);
        this.addressLb = getChildNode(ADDRESS_ID);
    }

    public String getNameId() {
        return nameLb.getText();
    }

    public String getPhoneId() {
        return phoneLb.getText();
    }

    public String getEmailId() {
        return emailLb.getText();
    }

    public String getAddressId() {
        return addressLb.getText();
    }
}
```
###### /java/guitests/PersonDetailsPanelTest.java
``` java
public class PersonDetailsPanelTest extends AddressBookGuiTest {

    @Test
    public void openTwitterTabView() {

        OpenTwitterWebViewEvent event = new OpenTwitterWebViewEvent();
        postNow(event);
        assertTrue(true);
    }

    @Test
    public void openFacebookTabView() {

        OpenFaceBookWebViewEvent event = new OpenFaceBookWebViewEvent();
        postNow(event);
        assertTrue(true);
    }

    @Test
    public void openGithubTabView() {

        OpenGithubWebViewEvent event = new OpenGithubWebViewEvent();
        postNow(event);
        assertTrue(true);
    }

    @Test
    public void openInstagramTabView() {

        OpenInstagramWebViewEvent event = new OpenInstagramWebViewEvent();
        postNow(event);
        assertTrue(true);
    }

    @Test
    public void openNusmodsTabView() {

        OpenNusModsWebViewEvent event = new OpenNusModsWebViewEvent();
        postNow(event);
        assertTrue(true);
    }
}
```
###### /java/seedu/address/logic/commands/BirthdayAddCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for BirthdayAddCommand.
 */
public class BirthdayAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedCorrectlyUnfilteredList_success() throws Exception {
        Person originalPerson = (Person) model.getAddressBook().getPersonList().get(0);
        Person editedPerson = (Person) model.getAddressBook().getPersonList().get(0);
        Birthday birthday = new Birthday("01/01/2000");
        editedPerson.setBirthday(birthday);

        BirthdayAddCommand birthdayAddCommand = prepareCommand(INDEX_FIRST_PERSON, birthday);
        String expectedMessage = String.format(BirthdayAddCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);

        assertCommandSuccess(birthdayAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedCorrectlyFilteredList_success() throws Exception {
        Person originalPerson = (Person) model.getFilteredPersonList().get(0);
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        Birthday birthday = new Birthday("01/01/2000");
        editedPerson.setBirthday(birthday);

        BirthdayAddCommand birthdayAddCommand = prepareCommand(INDEX_FIRST_PERSON, birthday);
        String expectedMessage = String.format(BirthdayAddCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);

        assertCommandSuccess(birthdayAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getAddressBook().getPersonList().size() + 1);
        Birthday birthday = new Birthday("01/01/2000");
        BirthdayAddCommand birthdayAddCommand = prepareCommand(outOfBoundIndex, birthday);

        assertCommandFailure(birthdayAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Birthday birthday = new Birthday("01/01/2000");
        BirthdayAddCommand birthdayAddCommand = prepareCommand(outOfBoundIndex, birthday);

        assertCommandFailure(birthdayAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Birthday birthday = new Birthday("01/01/2000");
        final BirthdayAddCommand standardCommand = new BirthdayAddCommand(INDEX_FIRST_PERSON, birthday);

        // same values -> returns true
        BirthdayAddCommand commandWithSameValues = new BirthdayAddCommand(INDEX_FIRST_PERSON, birthday);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new BirthdayAddCommand(INDEX_SECOND_PERSON, birthday)));

        // different birthday value -> returns false
        Birthday anotherBirthday = new Birthday("10/10/2010");
        assertFalse(standardCommand.equals(new BirthdayAddCommand(INDEX_FIRST_PERSON, anotherBirthday)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code birthday}
     */
    private BirthdayAddCommand prepareCommand(Index index, Birthday birthday) {
        BirthdayAddCommand birthdayAddCommand = new BirthdayAddCommand(index, birthday);
        birthdayAddCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayAddCommand;
    }
}
```
###### /java/seedu/address/logic/commands/BirthdayRemoveCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for BirthdayRemoveCommand.
 */
public class BirthdayRemoveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());


    /**
     * Edit person list where index is smaller than (or equal to) the size of the address book person list
     */
    @Test
    public void execute_allFieldsSpecifiedCorrectlyUnfilteredList_success() throws Exception {
        Person originalPerson = (Person) model.getAddressBook().getPersonList().get(0);
        Person editedPerson = (Person) model.getAddressBook().getPersonList().get(0);
        Birthday birthday = new Birthday();
        editedPerson.setBirthday(birthday);

        BirthdayRemoveCommand birthdayRemoveCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(BirthdayRemoveCommand.MESSAGE_REMOVE_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);

        assertCommandSuccess(birthdayRemoveCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Edit filtered list where index is smaller than (or equal to) the size of filtered list
     */
    @Test
    public void execute_allFieldsSpecifiedCorrectlyFilteredList_success() throws Exception {
        Person originalPerson = (Person) model.getFilteredPersonList().get(0);
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        Birthday birthday = new Birthday();
        editedPerson.setBirthday(birthday);

        BirthdayRemoveCommand birthdayRemoveCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(BirthdayRemoveCommand.MESSAGE_REMOVE_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);

        assertCommandSuccess(birthdayRemoveCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Edit person list where index is larger than size of the address book person list
     */
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getAddressBook().getPersonList().size() + 1);
        Birthday birthday = new Birthday();
        BirthdayRemoveCommand birthdayRemoveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(birthdayRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Birthday birthday = new Birthday();
        BirthdayRemoveCommand birthdayRemoveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(birthdayRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Birthday birthday = new Birthday();
        final BirthdayRemoveCommand standardCommand = new BirthdayRemoveCommand(INDEX_FIRST_PERSON);

        // same values -> returns true
        BirthdayRemoveCommand commandWithSameValues = new BirthdayRemoveCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new BirthdayRemoveCommand(INDEX_SECOND_PERSON)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code birthday}
     */
    private BirthdayRemoveCommand prepareCommand(Index index) {
        BirthdayRemoveCommand birthdayRemoveCommand = new BirthdayRemoveCommand(index);
        birthdayRemoveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayRemoveCommand;
    }
}
```
###### /java/seedu/address/logic/commands/FindCommandTest.java
``` java
    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("n/ Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multiplePhoneKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("p/ 95352563 9482224 9482427");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleEmailKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("e/ heinz@example.com werner@example.com lydia@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleAddressKeywords_singlePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("a/ little tokyo");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA));
    }

    @Test
    public void execute_singleAddressKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("a/ street");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, GEORGE));
    }

    @Test
    public void execute_singleNameSubstring_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("n/ er");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, DANIEL, ELLE));
    }

    @Test
    public void execute_multipleNameSubstrings_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        FindCommand command = prepareCommand("n/ er kun ai");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, DANIEL, ELLE, FIONA));
    }

    @Test
    public void execute_singlePhoneSubstring_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("p/ 24");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_multiplePhoneSubstrings_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("p/ 9535 8765");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, CARL, DANIEL));
    }

    @Test
    public void execute_singleEmailSubstring_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand("e/ rne");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DANIEL, ELLE));
    }

    @Test
    public void execute_multipleEmailSubstrings_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("e/ johnd heinz cornelia");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, CARL, DANIEL));
    }

```
###### /java/seedu/address/logic/commands/MapRouteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code MapRouteCommand}.
 */
public class MapRouteCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private String startLocation = "Clementi Street";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, startLocation);
        assertExecutionSuccess(INDEX_THIRD_PERSON, startLocation);
        assertExecutionSuccess(lastPersonIndex, startLocation);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, startLocation, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON, startLocation);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, startLocation, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MapRouteCommand mapRouteFirstCommand = new MapRouteCommand(INDEX_FIRST_PERSON, startLocation);
        MapRouteCommand mapRouteSecondCommand = new MapRouteCommand(INDEX_SECOND_PERSON, startLocation);

        // same object -> returns true
        assertTrue(mapRouteFirstCommand.equals(mapRouteFirstCommand));

        // same values -> returns true
        MapRouteCommand mapRouteFirstCommandCopy = new MapRouteCommand(INDEX_FIRST_PERSON, startLocation);
        assertTrue(mapRouteFirstCommand.equals(mapRouteFirstCommandCopy));

        // different types -> returns false
        assertFalse(mapRouteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(mapRouteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(mapRouteFirstCommand.equals(mapRouteSecondCommand));
    }

    /**
     * Executes a {@code MapRouteCommand} with the given {@code index, location}, and checks that
     * {@code BrowserPanelFindRouteEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String location) {
        MapRouteCommand mapRouteCommand = prepareCommand(index, location);

        try {
            CommandResult commandResult = mapRouteCommand.execute();
            assertEquals(String.format(mapRouteCommand.MESSAGE_FIND_ROUTE_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
        BrowserPanelFindRouteEvent lastEvent = (BrowserPanelFindRouteEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.getSelectedPerson());
        assertEquals(location, lastEvent.getAddress());
    }

    /**
     * Executes a {@code MapRouteCommand} with the given {@code index, location}, and checks that a
     * {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String location, String expectedMessage) {
        MapRouteCommand mapRouteCommand = prepareCommand(index, location);

        try {
            mapRouteCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code MapRouteCommand} with parameters {@code index, location}.
     */
    private MapRouteCommand prepareCommand(Index index, String location) {
        MapRouteCommand mapRouteCommand = new MapRouteCommand(index, location);
        mapRouteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapRouteCommand;
    }
}
```
###### /java/seedu/address/logic/commands/MapShowCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code MapShowCommand}.
 */
public class MapShowCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MapShowCommand mapShowFirstCommand = new MapShowCommand(INDEX_FIRST_PERSON);
        MapShowCommand mapShowSecondCommand = new MapShowCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(mapShowFirstCommand.equals(mapShowFirstCommand));

        // same values -> returns true
        MapShowCommand mapShowFirstCommandCopy = new MapShowCommand(INDEX_FIRST_PERSON);
        assertTrue(mapShowFirstCommand.equals(mapShowFirstCommandCopy));

        // different types -> returns false
        assertFalse(mapShowFirstCommand.equals(1));

        // null -> returns false
        assertFalse(mapShowFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(mapShowFirstCommand.equals(mapShowSecondCommand));
    }

    /**
     * Executes a {@code MapShowCommand} with the given {@code index}, and checks that
     * {@code BrowserPanelShowLocationEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        MapShowCommand mapShowCommand = prepareCommand(index);

        try {
            CommandResult commandResult = mapShowCommand.execute();
            assertEquals(String.format(MapShowCommand.MESSAGE_LOCATE_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        BrowserPanelShowLocationEvent lastEvent = (BrowserPanelShowLocationEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.getNewSelection());
    }

    /**
     * Executes a {@code MapShowCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        MapShowCommand mapShowCommand = prepareCommand(index);

        try {
            mapShowCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code MapShowCommand} with parameters {@code index}.
     */
    private MapShowCommand prepareCommand(Index index) {
        MapShowCommand mapShowCommand = new MapShowCommand(index);
        mapShowCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapShowCommand;
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("n/", "foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_birthdayAdd() throws Exception {
        List<String> keywords = Arrays.asList("1", "01/01/2000");
        BirthdayAddCommand command = (BirthdayAddCommand) parser.parseCommand(
                BirthdayAddCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        Index firstIndex = new Index(0);
        Birthday birthday = new Birthday("01/01/2000");
        assertEquals(new BirthdayAddCommand(firstIndex, birthday), command);
    }

    @Test
    public void parseCommand_birthdayRemove() throws Exception {
        List<String> keywords = Arrays.asList("1");
        BirthdayRemoveCommand command = (BirthdayRemoveCommand) parser.parseCommand(
                BirthdayRemoveCommand.COMMAND_WORD + " " + keywords.get(0) + "");
        Index firstIndex = new Index(0);
        assertEquals(new BirthdayRemoveCommand(firstIndex), command);
    }

```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_map_show() throws Exception {
        MapShowCommand command = (MapShowCommand) parser.parseCommand(
                MapShowCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new MapShowCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_map_route() throws Exception {
        String startLocation = "Clementi Street";
        MapRouteCommand command = (MapRouteCommand) parser.parseCommand(
                MapRouteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_ADDRESS + startLocation);
        assertEquals(new MapRouteCommand(INDEX_FIRST_PERSON, startLocation), command);
    }
```
###### /java/seedu/address/logic/parser/BirthdayAddCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class BirthdayAddCommandParserTest {

    private BirthdayAddCommandParser parser = new BirthdayAddCommandParser();
    private Birthday birthday = new Birthday("01/01/2000");

    public BirthdayAddCommandParserTest() throws IllegalValueException {
    }

    @Test
    public void parse_validArgs_returnsBirthdayAddCommand() {
        assertParseSuccess(parser, "1 01/01/2000", new BirthdayAddCommand(INDEX_FIRST_PERSON, birthday));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "aaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01012000000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01012000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 0101/2000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 32/01/2000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01/13/2000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayAddCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/BirthdayRemoveCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class BirthdayRemoveCommandParserTest {

    private BirthdayRemoveCommandParser parser = new BirthdayRemoveCommandParser();

    public BirthdayRemoveCommandParserTest() {}

    @Test
    public void parse_validArgs_returnsBirthdayRemoveCommand() {
        assertParseSuccess(parser, "1", new BirthdayRemoveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2.2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "aaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 aaa", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 01/13/2000", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdayRemoveCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/FindCommandParserTest.java
``` java
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefix_throwsParseException() {
        assertParseFailure(parser, "alice bob eve",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgAfterPrefix_throwsParseException() {
        assertParseFailure(parser, "n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/ p/ e/ a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/alice bob p/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/ p/12345678 e/alice@gmail.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_dummyValueBeforeFirstPrefix_throwsParseException() {
        assertParseFailure(parser, "alicen/alice bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "alice p/12345678",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommandOne =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("n/", "Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommandOne);
        FindCommand expectedFindCommandTwo =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("n/", "Alice", "p/", "12345678")));
        assertParseSuccess(parser, "n/Alice p/12345678", expectedFindCommandTwo);
        FindCommand expectedFindCommandThree =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("e/", "alice@com", "bob@com")));
        assertParseSuccess(parser, "e/ alice@com bob@com", expectedFindCommandThree);
        FindCommand expectedFindCommandFour =
                new FindCommand(new NameContainsKeywordsPredicate(
                        Arrays.asList("a/", "blk", "30", "Jurong", "East", "Street", "25,", "#21-31")));
        assertParseSuccess(parser, "a/blk 30 Jurong East Street 25, #21-31", expectedFindCommandFour);
    }

}
```
###### /java/seedu/address/logic/parser/MapRouteCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class MapRouteCommandParserTest {

    private MapRouteCommandParser parser = new MapRouteCommandParser();
    private String startLocation = "Clementi Street";

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1 a/Clementi Street", new MapRouteCommand(INDEX_FIRST_PERSON, startLocation));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MapRouteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 a/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MapRouteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 a/    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MapRouteCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/MapShowCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class MapShowCommandParserTest {

    private MapShowCommandParser parser = new MapShowCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new MapShowCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapShowCommand.MESSAGE_USAGE));
    }
}
```
###### /java/systemtests/FindCommandSystemTest.java
``` java
    @Test
    public void find() {

        Index firstIndex = new Index(0);

        /* Case: find multiple persons by name in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " n/ " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/ " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by name where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find multiple persons by name in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find multiple persons by name in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find multiple persons by name in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find multiple persons by name in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons by name in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " I/1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " n/ " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by name in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " n/ " + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by name in address book, keyword is substring of name -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Mei";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by name in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person by name not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " n/ " + " Mark";
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple persons by phone in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + " 98765432 87652533";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by phone in address book, keyword is substring of phone number -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + " 8765";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by phone in address book, phone number is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + " 853552555";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person by phone not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " p/ " + " 66666666";
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple persons by email in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + " johnd@example.com  cornelia@example.com";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by email in address book, keyword is same as email but of different case
         * ->2 person found
         */
        command = FindCommand.COMMAND_WORD + " e/ " + " JoHnD@EXAMPLE.com  corneLIA@example.COM";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by email in address book, email keyword is substring of the email -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + " rne";
        ModelHelper.setFilteredList(expectedModel, DANIEL, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by email in address book, email address is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + " sheinz@example.com";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person by email not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " e/ " + " nuscomputing@example.com";
        assertCommandSuccess(command, expectedModel);

        /* Case: find single person by address in address book, 5 keywords -> 1 person found */
        command = FindCommand.COMMAND_WORD + " a/ " + " 311, Clementi Ave 2, #02-25";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by substring of address in address book, 1 keywords -> 3 person found */
        command = FindCommand.COMMAND_WORD + " a/ " + " street";
        ModelHelper.setFilteredList(expectedModel, CARL, DANIEL, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by address in address book, keyword is same as address but of different case
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " a/ " + " 10TH StrEEt";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardChanged(firstIndex);

        /* Case: find person by address in address book, address name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " a/ " + " 110th street";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person by address not in address book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " a/ " + " 311, Clementi Ave 2, #02-31";
        assertCommandSuccess(command, expectedModel);

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_WORD + " n/ " + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " n/ " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

```
