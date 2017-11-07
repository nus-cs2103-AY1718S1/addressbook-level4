# Chng-Zhi-Xuan-reused
###### \java\seedu\address\logic\commands\DirCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code DirCommand}.
 */
public class DirCommandTest {
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
        DirCommand dirFirstToSecondCommand = new DirCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE);
        DirCommand dirSecondToThirdCommand = new DirCommand(INDEX_SECOND_PLACE, INDEX_THIRD_PLACE);

        // same object -> returns true
        assertTrue(dirFirstToSecondCommand.equals(dirFirstToSecondCommand));

        // same values -> returns true
        DirCommand dirFirstToSecondCommandCopy = new DirCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE);
        assertTrue(dirFirstToSecondCommand.equals(dirFirstToSecondCommandCopy));

        // different types -> returns false
        assertFalse(dirFirstToSecondCommand.equals(1));

        // null -> returns false
        assertFalse(dirFirstToSecondCommand.equals(null));

        // different place -> returns false
        assertFalse(dirFirstToSecondCommand.equals(dirSecondToThirdCommand));
    }

    /**
     * Executes a {@code DirCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index fromIndex, Index toIndex) {
        DirCommand dirCommand = prepareCommand(fromIndex, toIndex);

        try {
            CommandResult commandResult = dirCommand.execute();
            assertEquals(DirCommand.MESSAGE_DIR_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        GotoRequestEvent lastEvent = (GotoRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(toIndex, toIndex.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code DirCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index fromIndex, Index toIndex, String expectedMessage) {
        DirCommand dirCommand = prepareCommand(fromIndex, toIndex);

        try {
            dirCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code DirCommand} with parameters {@code index}.
     */
    private DirCommand prepareCommand(Index fromIndex, Index toIndex) {
        DirCommand dirCommand = new DirCommand(fromIndex, toIndex);
        dirCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return dirCommand;
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
###### \java\seedu\address\logic\parser\DirCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class DirCommandParserTest {

    private DirCommandParser parser = new DirCommandParser();

    @Test
    public void parse_validArgs_returnsDirCommand() {
        assertParseSuccess(parser, "1 2", new DirCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirCommand.MESSAGE_USAGE));
    }
}
```
