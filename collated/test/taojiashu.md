# taojiashu
###### \java\seedu\address\logic\commands\ExitCommandTest.java
``` java
    private ExitCommand exitCommand;
    private CommandHistory history;

```
###### \java\seedu\address\logic\commands\ExitCommandTest.java
``` java
    @Before
    public void setUp() {
        Model model = new ModelManager();
        history = new CommandHistory();
        exitCommand = new ExitCommand();
        exitCommand.setData(model, history, new UndoRedoStack());
    }

```
###### \java\seedu\address\logic\commands\ExitCommandTest.java
``` java
    @Test
    public void execute_exit_stalled() {
        assertCommandResult(exitCommand, MESSAGE_CONFIRMATION);

        String otherCommand = "clear";
        history.add(otherCommand);
        assertCommandResult(exitCommand, MESSAGE_CONFIRMATION);
    }


    @Test
    public void execute_exit_success() {
```
###### \java\seedu\address\logic\commands\ExitCommandTest.java
``` java
        history.add("exit");

        assertCommandResult(exitCommand, MESSAGE_EXIT_ACKNOWLEDGEMENT);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

```
###### \java\seedu\address\logic\commands\FavouriteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for FavouriteCommand.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markFavourite_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withFavourite("True").build();
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final FavouriteCommand standardCommand = new FavouriteCommand(INDEX_FIRST_PERSON);
        final FavouriteCommand anotherCommand = new FavouriteCommand(INDEX_SECOND_PERSON);

        // same values -> returns true
        FavouriteCommand commandWithSameValues = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same objects -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different person -> returns false
        assertFalse(standardCommand.equals(anotherCommand));
    }

    /**
     * Returns an {@code FavouriteCommand} with parameter {@code index}
     */
    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}
```
###### \java\seedu\address\logic\commands\LocateCommandTest.java
``` java
/**
 * Contains integration tests
 */
public class LocateCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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
        LocateCommand locateFirstCommand = new LocateCommand(INDEX_FIRST_PERSON);
        LocateCommand locateSecondCommand = new LocateCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(locateFirstCommand.equals(locateFirstCommand));

        // same values -> returns true
        LocateCommand locateFirstCommandCopy = new LocateCommand(INDEX_FIRST_PERSON);
        assertTrue(locateFirstCommand.equals(locateFirstCommandCopy));

        // different types -> returns false
        assertFalse(locateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(locateFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(locateFirstCommand.equals(locateSecondCommand));
    }

    /**
     * Executes a {@code LocateCommand} with the given {@code index}, and checks that {@code ShowLocationRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        LocateCommand locateCommand = prepareCommand(index);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
        String address = person.getAddress().toString();

        try {
            CommandResult commandResult = locateCommand.execute();
            assertEquals(String.format(locateCommand.MESSAGE_LOCATE_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShowLocationRequestEvent lastEvent = (ShowLocationRequestEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(address, lastEvent.getAddress());
    }

    /**
     * Executes a {@code LocateCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        LocateCommand locateCommand = prepareCommand(index);

        try {
            locateCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code LocateCommand} with parameters {@code index}.
     */
    private LocateCommand prepareCommand(Index index) {
        LocateCommand locateCommand = new LocateCommand(index);
        locateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locateCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ShowFavouriteCommandTest.java
``` java
/**
 * JUnit tests of ShowFavouriteCommand
 */
public class ShowFavouriteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_listIsNotFiltered_showFavourite() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ShowFavouriteCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA));
    }

    @Test
    public void execute_listIsFiltered_showFavourite() {
        showFirstPersonOnly(model);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ShowFavouriteCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA));
    }

    private ShowFavouriteCommand prepareCommand() {
        ShowFavouriteCommand command = new ShowFavouriteCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_favourite() throws Exception {
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(FavouriteCommand.COMMAND_WORD_1 + " "
                + INDEX_FIRST_PERSON.getOneBased());
        FavouriteCommand abbreviatedCommand =
                (FavouriteCommand) parser.parseCommand(FavouriteCommand.COMMAND_WORD_2 + " "
                + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new FavouriteCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_showFavourite() throws Exception {
        assertTrue(parser.parseCommand(ShowFavouriteCommand.COMMAND_WORD_1) instanceof ShowFavouriteCommand);
        assertTrue(parser.parseCommand(ShowFavouriteCommand.COMMAND_WORD_2 + " 3") instanceof ShowFavouriteCommand);
    }

    @Test
    public void parseCommand_locateCommand() throws Exception {
        assertTrue(parser.parseCommand(LocateCommand.COMMAND_WORDVAR + " 1") instanceof LocateCommand);
    }
```
###### \java\seedu\address\logic\parser\FavouriteCommandParserTest.java
``` java
public class FavouriteCommandParserTest {
    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        assertParseSuccess(parser, "1", new FavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\LocateCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class LocateCommandParserTest {

    private LocateCommandParser parser = new LocateCommandParser();

    @Test
    public void parse_validArgs_returnsLocateCommand() {
        assertParseSuccess(parser, "1", new LocateCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\FavouriteTest.java
``` java
public class FavouriteTest {

    @Test
    public void equals() {
        Favourite favourite = new Favourite();

        // same object -> returns true
        assertTrue(favourite.equals(favourite));

        // same values -> returns true
        Favourite favouriteCopy = new Favourite();
        assertTrue(favourite.equals(favouriteCopy));

        // different types -> returns false
        assertFalse(favourite.equals(1));

        // null -> returns false
        assertFalse(favourite.equals(null));

        // different values -> returns false
        Favourite differentFavourite = new Favourite();
        differentFavourite.toggleFavourite();
        assertFalse(favourite.equals(differentFavourite));
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Favourite} of the {@code Person} that we are building
     */
    public PersonBuilder withFavourite(String favourite) {
        if (favourite.equals("True")) {
            this.person.setFavourite(new Favourite(true));
        } else if (favourite.equals("False")) {
            this.person.setFavourite(new Favourite());
        }
        return this;
    }

```
