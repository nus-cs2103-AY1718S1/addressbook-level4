# Chng-Zhi-Xuan-reused
###### \java\seedu\address\logic\commands\DirectionCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code DirectionCommand}.
 */
public class DirectionCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /* Carrying out the test will lead to FXML.Load() failure.
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPlaceIndex = Index.fromOneBased(model.getFilteredPlaceList().size());

        assertExecutionSuccess(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE);
        assertExecutionSuccess(INDEX_THIRD_PLACE, INDEX_FIRST_PLACE);
        assertExecutionSuccess(lastPlaceIndex, INDEX_NINETH_PLACE);
    }
    */

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);

        assertExecutionFailure(INDEX_FIRST_PLACE, outOfBoundsIndex, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPlaceOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PLACE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPlaceList().size());

        assertExecutionFailure(INDEX_FIRST_PLACE, outOfBoundsIndex, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void equals() {
        DirectionCommand dirFirstToSecondCommand = new DirectionCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE);
        DirectionCommand dirSecondToThirdCommand = new DirectionCommand(INDEX_SECOND_PLACE, INDEX_THIRD_PLACE);

        // same object -> returns true
        assertTrue(dirFirstToSecondCommand.equals(dirFirstToSecondCommand));

        // same values -> returns true
        DirectionCommand dirFirstToSecondCommandCopy = new DirectionCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE);
        assertTrue(dirFirstToSecondCommand.equals(dirFirstToSecondCommandCopy));

        // different types -> returns false
        assertFalse(dirFirstToSecondCommand.equals(1));

        // null -> returns false
        assertFalse(dirFirstToSecondCommand.equals(null));

        // different place -> returns false
        assertFalse(dirFirstToSecondCommand.equals(dirSecondToThirdCommand));
    }

    /**
     * Executes a {@code DirectionCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index fromIndex, Index toIndex) {
        DirectionCommand directionCommand = prepareCommand(fromIndex, toIndex);

        try {
            CommandResult commandResult = directionCommand.execute();
            assertEquals(DirectionCommand.MESSAGE_DIR_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        GotoRequestEvent lastEvent = (GotoRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(toIndex, toIndex.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code DirectionCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index fromIndex, Index toIndex, String expectedMessage) {
        DirectionCommand directionCommand = prepareCommand(fromIndex, toIndex);

        try {
            directionCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code DirectionCommand} with parameters {@code index}.
     */
    private DirectionCommand prepareCommand(Index fromIndex, Index toIndex) {
        DirectionCommand directionCommand = new DirectionCommand(fromIndex, toIndex);
        directionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return directionCommand;
    }
}
```
###### \java\seedu\address\logic\parser\BookmarkCommandParserTest.java
``` java
public class BookmarkCommandParserTest {

    private BookmarkCommandParser parser = new BookmarkCommandParser();

    @Test
    public void parse_validArgs_returnsBookmarkCommand() {
        assertParseSuccess(parser, "1", new BookmarkCommand(INDEX_FIRST_PLACE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookmarkCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ClearBookmarkCommandParserTest.java
``` java
public class ClearBookmarkCommandParserTest {

    private ClearBookmarkCommandParser parser = new ClearBookmarkCommandParser();

    public ClearBookmarkCommandParserTest() throws IllegalValueException {
    }

    @Test
    public void parse_validArgs_returnsClearBookmarkCommand() {
        assertParseSuccess(parser,  "", new ClearBookmarkCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                    ClearBookmarkCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\DirectionCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class DirectionCommandParserTest {

    private DirectionCommandParser parser = new DirectionCommandParser();

    @Test
    public void parse_validArgs_returnsDirCommand() {
        assertParseSuccess(parser, "1 2", new DirectionCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirectionCommand.MESSAGE_USAGE));
    }
}
```
