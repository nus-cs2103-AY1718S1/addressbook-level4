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

    @Test
    public void parseCommand_filter() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORDVAR + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORDVAR_1 + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORDVAR_1) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORDVAR_2 + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORDVAR_1) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORDVAR_2 + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORDVAR_1 + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_facebook() throws Exception {
        assertTrue(parser.parseCommand(FacebookCommand.COMMAND_WORDVAR_1) instanceof FacebookCommand);
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
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORDVAR_1) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
        assertTrue(parser.parseCommand("undomult 3") instanceof UndoCommand);
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
