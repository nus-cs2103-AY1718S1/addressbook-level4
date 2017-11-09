# martyn-wong
###### /java/seedu/address/logic/parser/MapCommandParserTest.java
``` java

public class MapCommandParserTest {

    private MapCommandParser parser = new MapCommandParser();

    @Test
    public void parse_validArgs_returnsMapCommand() throws Exception {
        assertParseSuccess(parser, "1", new MapCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "alex", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/logic/commands/SearchCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UniqueMeetingList(), new UserPrefs());


    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("first"));
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchCommand findFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand findSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }


}
```
###### /java/seedu/address/logic/commands/MapCommandTest.java
``` java

/**
 * Contains integration tests (interaction with the Model) for {@code MapCommand}.
 */
public class MapCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UniqueMeetingList(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        MapCommand mapFirstCommand = new MapCommand(INDEX_FIRST_PERSON);
        MapCommand mapSecondCommand = new MapCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(mapFirstCommand.equals(mapFirstCommand));

        // same values -> returns true
        MapCommand mapFirstCommandCopy = new MapCommand(INDEX_FIRST_PERSON);
        assertTrue(mapFirstCommand.equals(mapFirstCommandCopy));

        // different types -> returns false
        assertFalse(mapFirstCommand.equals(1));

        // null -> returns false
        assertFalse(mapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(mapFirstCommand.equals(mapSecondCommand));
    }

    /**
     * Executes a {@code MapCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        MapCommand mapCommand = prepareCommand(index);

        try {
            mapCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code MapCommand} with parameters {@code index}.
     */
    private MapCommand prepareCommand(Index index) {
        MapCommand mapCommand = new MapCommand(index);
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapCommand;
    }
}
```
