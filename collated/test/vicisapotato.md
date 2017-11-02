# vicisapotato
###### \java\seedu\address\logic\commands\TabCommandTest.java
``` java
public class TabCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexTabList_success() {
        assertExecutionSuccess(INDEX_FIRST_TAB);
        assertExecutionSuccess(INDEX_SECOND_TAB);
    }

    @Test
    public void execute_invalidIndexTabList_failure() {
        Index outOfBoundsIndex = Index.fromZeroBased(3);

        assertExecutionFailure(outOfBoundsIndex, TabCommand.MESSAGE_INVALID_TAB_INDEX);
    }

    @Test
    public void equals() {
        TabCommand tabFirstCommand = new TabCommand(INDEX_FIRST_TAB);
        TabCommand tabSecondCommand = new TabCommand(INDEX_SECOND_TAB);

        // same object -> returns true
        assertTrue(tabFirstCommand.equals(tabFirstCommand));

        // same values -> returns true
        TabCommand tabFirstCommandCopy = new TabCommand(INDEX_FIRST_TAB);
        assertTrue(tabFirstCommand.equals(tabFirstCommandCopy));

        // different types -> returns false
        assertFalse(tabFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tabFirstCommand.equals(null));

        // different parcel -> returns false
        assertFalse(tabFirstCommand.equals(tabSecondCommand));
    }

    /**
     * Executes a {@code TabCommand} with the given {@code index}, and checks that {@code JumpToTabRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        TabCommand tabCommand = prepareCommand(index);

        try {
            CommandResult commandResult = tabCommand.execute();
            assertEquals(String.format(TabCommand.MESSAGE_SELECT_TAB_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToTabRequestEvent lastEvent = (JumpToTabRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        TabCommand tabCommand = prepareCommand(index);

        try {
            tabCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private TabCommand prepareCommand(Index index) {
        TabCommand tabCommand = new TabCommand(index);
        tabCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tabCommand;
    }
}
```
###### \java\seedu\address\logic\parser\TabCommandParserTest.java
``` java
public class TabCommandParserTest {

    private TabCommandParser parser = new TabCommandParser();

    @Test
    public void parse_validArgs_returnsTabCommand() {
        assertParseSuccess(parser, "1", new TabCommand(INDEX_FIRST_TAB));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\TabCommandParserTest.java
``` java

```
###### \java\systemtests\TabCommandSystemTest.java
``` java
public class TabCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void tab() {
        /* Case: select the first tab in the parcel list, command with leading spaces and trailing spaces
         * -> tab selected
         */
        String command = "   " + TabCommand.COMMAND_WORD + " " + INDEX_FIRST_TAB.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_TAB);

        /* Case: invalid index (number of tabs + 1) -> rejected */
        int invalidIndex = TabCommand.NUM_TAB + 1;
        assertCommandFailure(TabCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_TAB_INDEX);

        /* Case: select the current selected tab -> tab selected */
        assertCommandSuccess(command, INDEX_FIRST_TAB);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(TabCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(TabCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(TabCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(TabCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("tAB 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty address book -> tab selected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getParcelList().size() == 0;
        assertCommandSuccess(command, INDEX_FIRST_TAB);
    }
```
