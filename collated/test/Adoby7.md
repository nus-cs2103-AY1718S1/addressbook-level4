# Adoby7
###### \java\seedu\address\logic\commands\AddEventCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() throws Exception {
        Event validEvent = new EventBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        expectedModel.addEvent(validEvent);

        assertCommandSuccess(prepareCommand(validEvent, model), model,
                String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event eventInList = new Event(model.getEventList().getEventList().get(0));
        assertCommandFailure(prepareCommand(eventInList, model), model, AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    /**
     * Generates a new {@code AddEventCommand} which upon execution, adds {@code event} into the {@code model}.
     */
    private AddEventCommand prepareCommand(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddEventCommandTest.java
``` java
public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void executeAcceptedEventSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateEventException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        getAddEventCommandForEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event first = new EventBuilder().withName("First Meeting").build();
        Event second = new EventBuilder().withName("Second Meeting").build();
        AddEventCommand addFirstCommand = new AddEventCommand(first);
        AddEventCommand addSecondCommand = new AddEventCommand(second);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        AddEventCommand addFirstCommandCopy = new AddEventCommand(first);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstCommand == null);

        // different event -> returns false
        assertFalse(addFirstCommand.equals(addSecondCommand));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForEvent(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }



    /**
     * A Model stub that always throw a DuplicateEventException when trying to add a event.
     */
    private class ModelStubThrowingDuplicateEventException extends ModelStub {
        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }

        @Override
        public ReadOnlyEventList getEventList() {
            return new EventList();
        }

        @Override
        public Set<Tag> extractNewTag(ReadOnlyPerson person) {
            return null;
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<Event>();

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            eventsAdded.add(new Event(event));
        }

        @Override
        public ReadOnlyEventList getEventList() {
            return new EventList();
        }

        @Override
        public Set<Tag> extractNewTag(ReadOnlyPerson person) {
            return null;
        }
    }

}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_EVENT_TIME_FIRST = "03/11/2018";
    public static final String VALID_EVENT_TIME_SECOND = "29/02/2020"; //leap year
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String INVALID_EVENT_TIME_FIRST = " " + PREFIX_EVENT_TIME + "03/15/2017";
    public static final String INVALID_EVENT_TIME_SECOND = " " + PREFIX_EVENT_TIME + "31/11/2017"; // 30 days in Nov
    public static final String INVALID_EVENT_TIME_THIRD = " " + PREFIX_EVENT_TIME + "29/02/2017";
    public static final String INVALID_EVENT_TIME_FORTH = " " + PREFIX_EVENT_TIME + "29/02/2100"; //Not a leap year
```
###### \java\seedu\address\logic\commands\DeleteEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {

        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        model = new ModelManager(new AddressBook(), new EventList(), new UserPrefs());

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() > model.getEventList().getEventList().size());

        DeleteEventCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteSecondCommand = new DeleteEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand == null);

        // different event -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteEventCommand} with the parameter {@code index}.
     */
    private DeleteEventCommand prepareCommand(Index index) {
        DeleteEventCommand deleteCommand = new DeleteEventCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoAddCommandTest.java
``` java
public class RedoAddCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddCommand addCommandOne = new AddCommand(HOON);
    private final AddCommand addCommandTwo = new AddCommand(IDA);

    @Before
    public void setUp() {
        addCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        addCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(addCommandTwo, addCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        addPerson(expectedModel, HOON);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        addPerson(expectedModel, IDA);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoAddEventCommandTest.java
``` java
public class RedoAddEventCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddEventCommand addCommandOne = new AddEventCommand(FIFTH);
    private final AddEventCommand addCommandTwo = new AddEventCommand(SIXTH);

    @Before
    public void setUp() {
        addCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        addCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(addCommandTwo, addCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        addEvent(expectedModel, FIFTH);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        addEvent(expectedModel, SIXTH);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoClearCommandTest.java
``` java
public class RedoClearCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final ClearCommand clearCommandOne = new ClearCommand();
    private final ClearCommand clearCommandTwo = new ClearCommand();

    @Before
    public void setUp() {
        clearCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        clearCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(clearCommandTwo, clearCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(new AddressBook(), new EventList(), new UserPrefs());

        // multiple commands in redoStack
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoDeleteCommandTest.java
``` java
public class RedoDeleteCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_SECOND_PERSON);

    @Before
    public void setUp() {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);

        // Need dummy value, because never call deleteCommand.execute()
        deleteCommandOne.assignPerson(ALICE);
        deleteCommandTwo.assignPerson(BENSON);
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoDeleteEventCommandTest.java
``` java
public class RedoDeleteEventCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteEventCommand deleteCommandOne = new DeleteEventCommand(INDEX_FIRST_EVENT);
    private final DeleteEventCommand deleteCommandTwo = new DeleteEventCommand(INDEX_SECOND_EVENT);

    @Before
    public void setUp() {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);

        // Need dummy value, because never call deleteCommand.execute()
        deleteCommandOne.assignEvent(FIRST);
        deleteCommandTwo.assignEvent(SECOND);
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstEvent(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstEvent(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoEditCommandTest.java
``` java
public class RedoEditCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditCommand editCommandOne = new EditCommand(CARL, IDA);
    private final EditCommand editCommandTwo = new EditCommand(DANIEL, HOON);

    @Before
    public void setUp() {
        editCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        editCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(editCommandTwo, editCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        modifyPerson(expectedModel, CARL, IDA);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        modifyPerson(expectedModel, DANIEL, HOON);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoAddCommandTest.java
``` java
public class UndoAddCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddCommand addCommandOne = new AddCommand(HOON);
    private final AddCommand addCommandTwo = new AddCommand(IDA);

    @Before
    public void setUp() {
        addCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        addCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(addCommandOne, addCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        addCommandOne.execute();
        addCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        addPerson(expectedModel, HOON);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoAddEventCommandTest.java
``` java
public class UndoAddEventCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddEventCommand addCommandOne = new AddEventCommand(FIFTH);
    private final AddEventCommand addCommandTwo = new AddEventCommand(SIXTH);

    @Before
    public void setUp() {
        addCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        addCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(addCommandOne, addCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        addCommandOne.execute();
        addCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        addEvent(expectedModel, FIFTH);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoClearCommandTest.java
``` java
public class UndoClearCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final ClearCommand clearCommandOne = new ClearCommand();
    private final ClearCommand clearCommandTwo = new ClearCommand();

    @Before
    public void setUp() {
        clearCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        clearCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(clearCommandOne, clearCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(new AddressBook(), getTypicalEventList(), new UserPrefs());
        clearCommandOne.execute();
        clearCommandTwo.execute();

        // multiple commands in undoStack
        expectedModel.resetData(new AddressBook(), new EventList());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoDeleteCommandTest.java
``` java
public class UndoDeleteCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_SECOND_PERSON);

    @Before
    public void setUp() {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoDeleteEventCommandTest.java
``` java
public class UndoDeleteEventCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteEventCommand deleteCommandOne = new DeleteEventCommand(INDEX_FIRST_EVENT);
    private final DeleteEventCommand deleteCommandTwo = new DeleteEventCommand(INDEX_SECOND_EVENT);

    @Before
    public void setUp() {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        deleteFirstEvent(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoEditCommandTest.java
``` java
public class UndoEditCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditCommand editCommandOne = new EditCommand(CARL, IDA);
    private final EditCommand editCommandTwo = new EditCommand(DANIEL, HOON);

    @Before
    public void setUp() {
        editCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        editCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(editCommandOne, editCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        editCommandOne.execute();
        editCommandTwo.execute();

        // multiple commands in undoStack
        modifyPerson(expectedModel, CARL, IDA);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
```
###### \java\seedu\address\logic\parser\AddEventCommandParserTest.java
``` java
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() {
        Event expectedEvent = new EventBuilder().withName(VALID_EVENT_NAME_FIRST)
                .withDescription(VALID_EVENT_DESC_FIRST).withTime(VALID_EVENT_TIME_FIRST).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_SECOND + EVENT_NAME_FIRST
                        + EVENT_DESC_FIRST + EVENT_TIME_FIRST,
                new AddEventCommand(expectedEvent));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_SECOND
                + EVENT_DESC_FIRST + EVENT_TIME_FIRST,
                new AddEventCommand(expectedEvent));

        // multiple times - last time accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_FIRST
                + EVENT_TIME_SECOND + EVENT_TIME_FIRST,
                new AddEventCommand(expectedEvent));
    }

    @Test
    public void parseCompulsoryFieldMissingFailure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_EVENT_NAME_FIRST + EVENT_DESC_FIRST
                + EVENT_TIME_FIRST, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_DESC_FIRST + VALID_EVENT_DESC_FIRST
                + EVENT_TIME_FIRST, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_DESC_FIRST + EVENT_DESC_FIRST
                + VALID_EVENT_TIME_FIRST, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_EVENT_NAME_FIRST
                + VALID_EVENT_DESC_FIRST + VALID_EVENT_TIME_FIRST, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_EVENT_NAME + EVENT_DESC_FIRST
                + EVENT_TIME_FIRST, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + INVALID_EVENT_DESC
                        + EVENT_TIME_FIRST, EventDescription.MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS);

        // invalid times
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_FIRST
                        + INVALID_EVENT_TIME_FIRST, EventTime.MESSAGE_EVENT_TIME_CONSTRAINTS);

        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_FIRST
                + INVALID_EVENT_TIME_SECOND, EventTime.MESSAGE_EVENT_TIME_CONSTRAINTS);

        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_FIRST
                + INVALID_EVENT_TIME_THIRD, EventTime.MESSAGE_EVENT_TIME_CONSTRAINTS);

        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_FIRST
                + INVALID_EVENT_TIME_FORTH, EventTime.MESSAGE_EVENT_TIME_CONSTRAINTS);
        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_EVENT_NAME + EVENT_DESC_FIRST
                + INVALID_EVENT_TIME_SECOND, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandPortrait() throws Exception {
        PortraitCommand command = (PortraitCommand) parser.parseCommand(PortraitCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PORTRAIT_DESC_FIRST);
        assertEquals(new PortraitCommand(INDEX_FIRST_PERSON, new PortraitPath(VALID_PORTRAIT_PATH_FIRST)), command);
    }
```
###### \java\seedu\address\logic\parser\DisjoinCommandParserTest.java
``` java
/**
 * Test disjoin command parser
 */
public class DisjoinCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisjoinCommand.MESSAGE_USAGE);

    private static final String FIRST_PERSON = " " + PREFIX_PERSON + INDEX_FIRST_PERSON.getOneBased();
    private static final String FIRST_EVENT = " " + PREFIX_EVENT + INDEX_FIRST_EVENT.getOneBased();

    private DisjoinCommandParser parser = new DisjoinCommandParser();

    @Test
    public void testMissingPartsInput() {
        //Miss all parts
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        //Miss person index
        assertParseFailure(parser, FIRST_EVENT, MESSAGE_INVALID_FORMAT);

        //Miss event index
        assertParseFailure(parser, FIRST_PERSON, MESSAGE_INVALID_FORMAT);
    }

    //Miss prefix
    @Test
    public void testMissingPrefixInput() {
        //Miss person prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + FIRST_EVENT,
                MESSAGE_INVALID_FORMAT);

        //Miss event prefix
        assertParseFailure(parser, FIRST_PERSON + " " + INDEX_FIRST_EVENT.getOneBased(),
                MESSAGE_INVALID_FORMAT);

        //Miss all prefixes
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_FIRST_EVENT.getOneBased(),
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void testIllegalInput() {
        // negative index
        assertParseFailure(parser, PREFIX_PERSON + "-5 " + FIRST_EVENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, FIRST_PERSON + " " + PREFIX_EVENT + "0" , MESSAGE_INVALID_FORMAT);

        // Non integer
        assertParseFailure(parser, PREFIX_PERSON + "some random string", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void testSuccess() {
        DisjoinCommand expectedCommand = new DisjoinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertParseSuccess(parser, FIRST_PERSON + " " + FIRST_EVENT, expectedCommand);
    }

}
```
###### \java\seedu\address\logic\parser\PortraitCommandParserTest.java
``` java
/**
 * Test portrait command parser
 */
public class PortraitCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            PortraitCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_INDEX = ParserUtil.MESSAGE_INVALID_INDEX;
    private PortraitCommandParser parser = new PortraitCommandParser();

    @Test
    public void testValidArgs() {
        PortraitCommand expectedCommand = null;
        try {
            expectedCommand = new PortraitCommand(Index.fromOneBased(1),
                    new PortraitPath(VALID_PORTRAIT_PATH_FIRST));
        } catch (IllegalValueException e) {
            fail("Test data cannot throw exception");
        }

        assertParseSuccess(parser, "1" + PORTRAIT_DESC_FIRST, expectedCommand);

        assertParseSuccess(parser, "1" + PORTRAIT_DESC_SECOND + PORTRAIT_DESC_FIRST, expectedCommand);
    }

    @Test
    public void testMissingPartsFailure() {

        assertParseFailure(parser, "" + PORTRAIT_DESC_FIRST, MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "1 " + VALID_PORTRAIT_PATH_FIRST, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void testInvalidValueFailure() {
        // negative index
        assertParseFailure(parser, "-5" + PORTRAIT_DESC_FIRST, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + PORTRAIT_DESC_FIRST, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 not an index", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/" + VALID_PORTRAIT_PATH_FIRST, MESSAGE_INVALID_FORMAT);
    }
}
```
###### \java\seedu\address\model\person\PortraitPathTest.java
``` java

/**
 * Test portrait path class
 */
public class PortraitPathTest {

    @Test
    public void isValidPath() {

        //invalid path

        assertFalse(PortraitPath.isValidPortraitPath(" "));
        assertFalse(PortraitPath.isValidPortraitPath("abcdjpg")); //no suffix
        assertFalse(PortraitPath.isValidPortraitPath(".png")); //only suffix
        assertFalse(PortraitPath.isValidPortraitPath("abcd.jpeg")); //invalid suffix
        assertFalse(PortraitPath.isValidPortraitPath("ab*cd.png")); //illegal char in path name
        assertFalse(PortraitPath.isValidPortraitPath("abcd..jpg")); //redundant '.'
        assertFalse(PortraitPath.isValidPortraitPath("ab.jpgcd.png")); //multiple suffix
        assertFalse(PortraitPath.isValidPortraitPath("valid.j/pg")); //suffix has '/'
        assertFalse(PortraitPath.isValidPortraitPath("C/valid.jpg")); //miss ':'

        //valid path
        assertTrue(PortraitPath.isValidPortraitPath("")); // empty path is allowed
        assertTrue(PortraitPath.isValidPortraitPath("C:/src/Name.jpg"));
        assertTrue(PortraitPath.isValidPortraitPath("D:/name12WithNumber34.png"));
        assertTrue(PortraitPath.isValidPortraitPath("E:/very/very/deep/path/name_with_underscore.jpg"));
        assertTrue(PortraitPath.isValidPortraitPath("F:/name with space.png"));
        assertTrue(PortraitPath.isValidPortraitPath("G:/Name_mixed/ with every\thing 1234.png"));
    }
}
```
