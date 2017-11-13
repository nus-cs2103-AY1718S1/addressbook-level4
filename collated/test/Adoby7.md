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
###### \java\seedu\address\logic\commands\DisjoinCommandTest.java
``` java
/**
 * Contains integration tests for disjoin command
 */
public class DisjoinCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinEvents(model);
    }

    @Test
    public void testDisjoinInvalidIndexFail() {
        final Index validIndex = INDEX_FIRST_EVENT;
        Index invalidLargeIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisjoinCommand invalidPersonIndexCommand = prepareCommand(invalidLargeIndex, validIndex, model);

        invalidLargeIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DisjoinCommand invalidEventIndexCommand = prepareCommand(validIndex, invalidLargeIndex, model);

        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(invalidEventIndexCommand, model, expectedMessage);
        assertCommandFailure(invalidPersonIndexCommand, model, expectedMessage);
    }

    @Test
    public void testNotParticipantFail() {
        String expectedMessage = DisjoinCommand.MESSAGE_PERSON_NOT_PARTICIPATE;
        final Index validIndex = INDEX_FIRST_EVENT;
        // last person does not join any event
        final Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Model actualModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        DisjoinCommand noParticipantCommand = prepareCommand(invalidIndex, validIndex, actualModel);

        assertCommandFailure(noParticipantCommand, actualModel, expectedMessage);

    }

    @Test
    public void testSuccess() {
        Index personIndex = INDEX_SECOND_PERSON;
        Index eventIndex = INDEX_FIRST_EVENT;
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        Person person = (Person) expectedModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event event = (Event) expectedModel.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String expectedMessage = String.format(DisjoinCommand.MESSAGE_DISJOIN_SUCCESS, person.getName(),
            event.getEventName());
        DisjoinCommand command = prepareCommand(personIndex, eventIndex, model);
        quitEvent(expectedModel, INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code DisjoinCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private DisjoinCommand prepareCommand(Index personIndex, Index eventIndex, Model model) {
        DisjoinCommand command = new DisjoinCommand(personIndex, eventIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\commands\EditEventCommandTest.java
``` java
/**
 * An integration test for edit event command and model
 */
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    @Test
    public void testLargeIndexFailure() {
        Index invalidLargeIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_SECOND).build();
        EditEventCommand command = prepareCommand(invalidLargeIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void testInvalidIndexInFilteredListFailure() {
        showFirstEventOnly(model);
        Index invalidIndex = INDEX_SECOND_PERSON;
        // ensures that the index is still in bounds of list
        assertTrue(invalidIndex.getZeroBased() < model.getEventList().getEventList().size());

        EditEventCommand command = prepareCommand(invalidIndex,
            new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST).build());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void testDuplicateEventFailure() {
        Event firstEvent = new Event(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        EditEventDescriptor descriptor = new EventDescriptorBuilder(firstEvent).build();
        EditEventCommand command = prepareCommand(INDEX_SECOND_EVENT, descriptor);

        assertCommandFailure(command, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void testDuplicateInFilteredListFailure() {
        showFirstEventOnly(model);

        ReadOnlyEvent event = model.getEventList().getEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        EditEventCommand command = prepareCommand(INDEX_FIRST_EVENT,
            new EventDescriptorBuilder(event).build());

        assertCommandFailure(command, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void testEditAllFields() throws Exception {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EventDescriptorBuilder(editedEvent).build();
        EditEventCommand command = prepareCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(model.getAddressBook(), new EventList(model.getEventList()),
            new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), editedEvent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void testEditSomeFields() throws Exception {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        ReadOnlyEvent lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder event = new EventBuilder(lastEvent);
        Event editedEvent = event.withName(VALID_EVENT_NAME_SECOND).withDescription(VALID_EVENT_DESC_SECOND)
            .withTime(VALID_EVENT_TIME_THIRD).build();

        EditEventDescriptor descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_SECOND)
            .withDescription(VALID_EVENT_DESC_SECOND).withTime(VALID_EVENT_TIME_THIRD).build();
        EditEventCommand command = prepareCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(model.getAddressBook(), new EventList(model.getEventList()),
            new UserPrefs());
        expectedModel.updateEvent(lastEvent, editedEvent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void testEditInFilteredList() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent personInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(personInFilteredList).withName(VALID_EVENT_NAME_SECOND).build();
        EditEventCommand command = prepareCommand(INDEX_FIRST_EVENT,
            new EventDescriptorBuilder().withName(VALID_EVENT_NAME_SECOND).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(model.getAddressBook(), new EventList(model.getEventList()),
            new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), editedEvent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_EVENT, DESC_EVENT_FIRST);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_EVENT_FIRST);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_EVENT, DESC_EVENT_FIRST)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_EVENT, DESC_EVENT_SECOND)));
    }

    /**
     * Returns an {@code EditEventCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEventCommand prepareCommand(Index index, EditEventCommand.EditEventDescriptor descriptor) {
        EditEventCommand command = new EditEventCommand(index, descriptor);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\EditEventDescriptorTest.java
``` java
/**
 * Test equality of EditEventDescriptorTest
 */
public class EditEventDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_EVENT_FIRST);
        assertTrue(DESC_EVENT_FIRST.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EVENT_FIRST.equals(DESC_EVENT_FIRST));

        // different types -> returns false
        assertFalse(DESC_EVENT_FIRST.equals(1));

        // different values -> returns false
        assertFalse(DESC_EVENT_FIRST.equals(DESC_EVENT_SECOND));

        // different name -> returns false
        EditEventDescriptor editedFirst = new EventDescriptorBuilder(DESC_EVENT_FIRST)
            .withName(VALID_EVENT_NAME_SECOND).build();
        assertFalse(DESC_EVENT_FIRST.equals(editedFirst));

        // different phone -> returns false
        editedFirst = new EventDescriptorBuilder(DESC_EVENT_FIRST).withDescription(VALID_EVENT_DESC_SECOND).build();
        assertFalse(DESC_EVENT_FIRST.equals(editedFirst));

        // different email -> returns false
        editedFirst = new EventDescriptorBuilder(DESC_EVENT_FIRST).withTime(VALID_EVENT_TIME_SECOND).build();
        assertFalse(DESC_EVENT_FIRST.equals(editedFirst));
    }
}
```
###### \java\seedu\address\logic\commands\PortraitCommandTest.java
``` java
/**
 * An integration and unit test for portrait command
 */
public class PortraitCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    @Test
    public void validTestSuccess() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PortraitPath path = new PortraitPath("");
        Person editedPerson = new Person(personToEdit);
        editedPerson.setPortraitPath(path);
        PortraitCommand portraitCommand = prepareCommand(INDEX_FIRST_PERSON, path);

        String expectedMessage = String.format(PortraitCommand.MESSAGE_DELETE_PORTRAIT_SUCCESS,
            personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(portraitCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void invalidTestIndexFailure() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PortraitPath path = new PortraitPath("");
        Person editedPerson = new Person(personToEdit);
        editedPerson.setPortraitPath(path);
        Index invalidLargeIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PortraitCommand portraitCommand = prepareCommand(invalidLargeIndex, path);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        assertCommandFailure(portraitCommand, model, expectedMessage);
    }

    @Test
    public void testEquals() {
        PortraitPath expectedPath = null;
        PortraitPath differentPath = null;
        try {
            expectedPath = new PortraitPath(VALID_PORTRAIT_PATH_FIRST);
            differentPath = new PortraitPath(VALID_PORTRAIT_PATH_SECOND);
        } catch (IllegalValueException e) {
            fail("Test data cannot throw exception");
        }
        requireNonNull(expectedPath);

        PortraitCommand command = new PortraitCommand(INDEX_FIRST_PERSON, expectedPath);

        assertTrue(command.equals(command));

        PortraitCommand commandCopy = new PortraitCommand(INDEX_FIRST_PERSON, expectedPath);
        assertTrue(command.equals(commandCopy));

        assertFalse(command.equals(new ClearCommand()));

        assertFalse(command.equals(null));

        PortraitCommand differentCommandOne = new PortraitCommand(INDEX_FIRST_PERSON, differentPath);
        PortraitCommand differentCommandTwo = new PortraitCommand(INDEX_SECOND_PERSON, expectedPath);
        assertFalse(command.equals(differentCommandOne));
        assertFalse(command.equals(differentCommandTwo));
    }

    /**
     * Generates a new PortraitCommand with the details of the given image path and index.
     */
    private PortraitCommand prepareCommand(Index index, PortraitPath path) {
        PortraitCommand command = new PortraitCommand(index, path);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoAddCommandTest.java
``` java
/**
 * Test the redo function of AddCommand
 */
public class RedoAddCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddCommand addCommandOne = new AddCommand(HOON);
    private final AddCommand addCommandTwo = new AddCommand(IDA);

    @Test
    public void execute() {
        RedoCommand redoCommand = prepareRedoCommand(model, addCommandOne, addCommandTwo);
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

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStub = new ModelStubThrowException();
        RedoCommand redoCommand = prepareRedoCommand(modelStub, addCommandOne, addCommandTwo);
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;

        assertCommandAssertionError(redoCommand, expectedMessage);
    }

    private class ModelStubThrowException extends ModelStub {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoAddEventCommandTest.java
``` java
/**
 * Test the redo function of AddEventCommand
 */
public class RedoAddEventCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddEventCommand addCommandOne = new AddEventCommand(FIFTH);
    private final AddEventCommand addCommandTwo = new AddEventCommand(SIXTH);

    @Test
    public void execute() {
        RedoCommand redoCommand = prepareRedoCommand(model, addCommandOne, addCommandTwo);
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

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStub = new ModelStubThrowException();
        RedoCommand redoCommand = prepareRedoCommand(modelStub, addCommandOne, addCommandTwo);
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;

        assertCommandAssertionError(redoCommand, expectedMessage);
    }

    private class ModelStubThrowException extends ModelStub {
        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoClearCommandTest.java
``` java
/**
 * Test the redo function of ClearCommand
 */
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
/**
 * Test the redo function of DeleteCommand
 */
public class RedoDeleteCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_FIRST_PERSON);

    @Before
    public void setUp() {
        executeAndRecover(model, deleteCommandOne, deleteCommandTwo);
    }

    @Test
    public void execute() {
        RedoCommand redoCommand = prepareRedoCommand(model, deleteCommandOne, deleteCommandTwo);
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

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, deleteCommandOne, deleteCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, deleteCommandOne, deleteCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void deletePerson(ReadOnlyPerson person) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void deletePerson(ReadOnlyPerson person) throws DeleteOnCascadeException {
            throw new DeleteOnCascadeException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoDeleteEventCommandTest.java
``` java
/**
 * Test the redo function of DeleteEventCommand
 */
public class RedoDeleteEventCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteEventCommand deleteCommandOne = new DeleteEventCommand(INDEX_FIRST_EVENT);
    private final DeleteEventCommand deleteCommandTwo = new DeleteEventCommand(INDEX_FIRST_EVENT);

    @Before
    public void setUp() {
        executeAndRecover(model, deleteCommandOne, deleteCommandTwo);
    }

    @Test
    public void execute() {
        RedoCommand redoCommand = prepareRedoCommand(model, deleteCommandOne, deleteCommandTwo);
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

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, deleteCommandOne, deleteCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, deleteCommandOne, deleteCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void deleteEvent(ReadOnlyEvent event) throws EventNotFoundException {
            throw new EventNotFoundException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void deleteEvent(ReadOnlyEvent event) throws DeleteOnCascadeException {
            throw new DeleteOnCascadeException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoDisjoinCommandTest.java
``` java
/**
 * Test the redo function of DisjoinCommand
 */
public class RedoDisjoinCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DisjoinCommand disjoinCommandOne = new DisjoinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
    private final DisjoinCommand disjoinCommandTwo = new DisjoinCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

    @Before
    public void setUp() throws Exception {
        joinEvents(model);
        Person firstPerson = (Person) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event firstEvent = (Event) model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        disjoinCommandOne.assignValueForTest(firstPerson, firstEvent);

        Person secondPerson = (Person) model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event secondEvent = (Event) model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        disjoinCommandTwo.assignValueForTest(secondPerson, secondEvent);
    }

    @Test
    public void execute() {
        RedoCommand redoCommand = prepareRedoCommand(model, disjoinCommandOne, disjoinCommandTwo);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());

        // multiple commands in redoStack
        quitEvent(expectedModel, INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        quitEvent(expectedModel, INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, disjoinCommandOne, disjoinCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, disjoinCommandOne, disjoinCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void quitEvent(Person person, Event event) throws PersonNotParticipateException {
            throw new PersonNotParticipateException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void quitEvent(Person person, Event event) throws NotParticipateEventException {
            throw new NotParticipateEventException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoEditCommandTest.java
``` java
/**
 * Test the redo function of EditCommand
 */
public class RedoEditCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditCommand.EditPersonDescriptor firstDescriptor = new EditPersonDescriptorBuilder(IDA).build();
    private final EditCommand.EditPersonDescriptor secondDescriptor = new EditPersonDescriptorBuilder(HOON).build();
    private final EditCommand editCommandOne = new EditCommand(INDEX_FIRST_PERSON, firstDescriptor);
    private final EditCommand editCommandTwo = new EditCommand(INDEX_SECOND_PERSON, secondDescriptor);

    @Test
    public void execute() {
        executeAndRecover(model, editCommandOne, editCommandTwo);
        RedoCommand redoCommand = prepareRedoCommand(model, editCommandOne, editCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        modifyPerson(expectedModel, INDEX_FIRST_PERSON, IDA);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        modifyPerson(expectedModel, INDEX_SECOND_PERSON, HOON);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new RedoEditCommandTest.ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new RedoEditCommandTest.ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, editCommandOne, editCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, editCommandOne, editCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoEditEventCommandTest.java
``` java
/**
 * Test the redo function of EditEventCommand
 */
public class RedoEditEventCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditEventDescriptor firstDescriptor = new EventDescriptorBuilder(SEVENTH).build();
    private final EditEventDescriptor secondDescriptor = new EventDescriptorBuilder(EIGHTH).build();
    private final EditEventCommand editEventCommandOne = new EditEventCommand(INDEX_FIRST_EVENT, firstDescriptor);
    private final EditEventCommand editEventCommandTwo = new EditEventCommand(INDEX_SECOND_EVENT, secondDescriptor);

    @Test
    public void execute() {
        executeAndRecover(model, editEventCommandOne, editEventCommandTwo);
        RedoCommand redoCommand = prepareRedoCommand(model, editEventCommandOne, editEventCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        modifyEvent(expectedModel, INDEX_FIRST_EVENT, SEVENTH);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        modifyEvent(expectedModel, INDEX_SECOND_EVENT, EIGHTH);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new RedoEditEventCommandTest.ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new RedoEditEventCommandTest.ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, editEventCommandOne, editEventCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, editEventCommandOne, editEventCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException {
            throw new DuplicateEventException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws EventNotFoundException {
            throw new EventNotFoundException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\redo\command\test\RedoPortraitCommandTest.java
``` java
/**
 * Test the redo function of PortraitCommand
 */
public class RedoPortraitCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private PortraitCommand portraitCommandOne;
    private PortraitCommand portraitCommandTwo;
    private Person modifiedFirstPerson;
    private Person modifiedSecondPerson;

    @Before
    public void setUp() throws Exception {
        PortraitPath firstPortrait = new PortraitPath(VALID_PORTRAIT_PATH_FIRST);
        PortraitPath secondPortrait = new PortraitPath(VALID_PORTRAIT_PATH_SECOND);
        portraitCommandOne = new PortraitCommand(INDEX_FIRST_PERSON, firstPortrait);
        portraitCommandTwo = new PortraitCommand(INDEX_SECOND_PERSON, secondPortrait);
        modifiedFirstPerson = new PersonBuilder(model.getFilteredPersonList()
            .get(INDEX_FIRST_PERSON.getZeroBased())).withPortrait(VALID_PORTRAIT_PATH_FIRST).build();
        modifiedSecondPerson = new PersonBuilder(model.getFilteredPersonList()
            .get(INDEX_SECOND_PERSON.getZeroBased())).withPortrait(VALID_PORTRAIT_PATH_SECOND).build();
    }

    @Test
    public void execute() {
        executeAndRecover(model, portraitCommandOne, portraitCommandTwo);
        RedoCommand redoCommand = prepareRedoCommand(model, portraitCommandOne, portraitCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

        // multiple commands in redoStack
        modifyPerson(expectedModel, INDEX_FIRST_PERSON, modifiedFirstPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        modifyPerson(expectedModel, INDEX_SECOND_PERSON, modifiedSecondPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new RedoPortraitCommandTest.ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new RedoPortraitCommandTest.ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_REDO_ASSERTION_ERROR;
        RedoCommand redoCommandOne = prepareRedoCommand(modelStubOne, portraitCommandOne, portraitCommandTwo);
        assertCommandAssertionError(redoCommandOne, expectedMessage);

        RedoCommand redoCommandTwo = prepareRedoCommand(modelStubTwo, portraitCommandOne, portraitCommandTwo);
        assertCommandAssertionError(redoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoAddCommandTest.java
``` java
/**
 * Test the undo function of AddCommand
 */
public class UndoAddCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddCommand addCommandOne = new AddCommand(HOON);
    private final AddCommand addCommandTwo = new AddCommand(IDA);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, addCommandOne, addCommandTwo);
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

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_UNDO_ASSERTION_ERROR;

        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, addCommandOne, addCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, addCommandOne, addCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void deletePerson(ReadOnlyPerson person) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void deletePerson(ReadOnlyPerson person) throws DeleteOnCascadeException {
            throw new DeleteOnCascadeException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoAddEventCommandTest.java
``` java
/**
 * Test the undo function of AddEventCommand
 */
public class UndoAddEventCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final AddEventCommand addCommandOne = new AddEventCommand(FIFTH);
    private final AddEventCommand addCommandTwo = new AddEventCommand(SIXTH);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, addCommandOne, addCommandTwo);
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

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_UNDO_ASSERTION_ERROR;
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, addCommandOne, addCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, addCommandOne, addCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void deleteEvent(ReadOnlyEvent event) throws EventNotFoundException {
            throw new EventNotFoundException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void deleteEvent(ReadOnlyEvent event) throws DeleteOnCascadeException {
            throw new DeleteOnCascadeException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoClearCommandTest.java
``` java
/**
 * Test the undo function of ClearCommand
 */
public class UndoClearCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final ClearCommand clearCommandOne = new ClearCommand();
    private final ClearCommand clearCommandTwo = new ClearCommand();

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, clearCommandOne, clearCommandTwo);
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
/**
 * Test the undo function of DeleteCommand
 */
public class UndoDeleteCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_SECOND_PERSON);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, deleteCommandOne, deleteCommandTwo);
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
/**
 * Test the undo function of DeleteEventCommand
 */
public class UndoDeleteEventCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DeleteEventCommand deleteCommandOne = new DeleteEventCommand(INDEX_FIRST_EVENT);
    private final DeleteEventCommand deleteCommandTwo = new DeleteEventCommand(INDEX_SECOND_EVENT);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, deleteCommandOne, deleteCommandTwo);
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
###### \java\seedu\address\logic\commands\undo\command\test\UndoDisjoinCommandTest.java
``` java
/**
 * Test the undo function of DisjoinCommand
 */
public class UndoDisjoinCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final DisjoinCommand disjoinCommandOne = new DisjoinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
    private final DisjoinCommand disjoinCommandTwo = new DisjoinCommand(INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);
    private Model modelCopy;

    @Before
    public void setUp() {
        joinEvents(model);
        modelCopy = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
    }

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, disjoinCommandOne, disjoinCommandTwo);
        disjoinCommandOne.execute();
        disjoinCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(modelCopy.getAddressBook(), modelCopy.getEventList(), new UserPrefs());
        quitEvent(expectedModel, INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(modelCopy.getAddressBook(), modelCopy.getEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_UNDO_ASSERTION_ERROR;
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, disjoinCommandOne, disjoinCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, disjoinCommandOne, disjoinCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void joinEvent(Person person, Event event) throws PersonHaveParticipateException {
            throw new PersonHaveParticipateException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void joinEvent(Person person, Event event) throws HaveParticipateEventException {
            throw new HaveParticipateEventException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoEditCommandTest.java
``` java
/**
 * Test the undo function of EditCommand
 */
public class UndoEditCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditPersonDescriptor firstDescriptor = new EditPersonDescriptorBuilder(IDA).build();
    private final EditPersonDescriptor secondDescriptor = new EditPersonDescriptorBuilder(HOON).build();
    private final EditCommand editCommandOne = new EditCommand(INDEX_FIRST_PERSON, firstDescriptor);
    private final EditCommand editCommandTwo = new EditCommand(INDEX_SECOND_PERSON, secondDescriptor);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, editCommandOne, editCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        editCommandOne.execute();
        editCommandTwo.execute();

        // multiple commands in undoStack
        modifyPerson(expectedModel, INDEX_FIRST_PERSON, IDA);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_UNDO_ASSERTION_ERROR;
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, editCommandOne, editCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, editCommandOne, editCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoEditEventCommandTest.java
``` java
/**
 * Test the undo function of EditEventCommand
 */
public class UndoEditEventCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private final EditEventDescriptor firstDescriptor = new EventDescriptorBuilder(SEVENTH).build();
    private final EditEventDescriptor secondDescriptor = new EventDescriptorBuilder(EIGHTH).build();
    private final EditEventCommand editEventCommandOne = new EditEventCommand(INDEX_FIRST_EVENT, firstDescriptor);
    private final EditEventCommand editEventCommandTwo = new EditEventCommand(INDEX_SECOND_EVENT, secondDescriptor);

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, editEventCommandOne, editEventCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        editEventCommandOne.execute();
        editEventCommandTwo.execute();

        // multiple commands in undoStack
        modifyEvent(expectedModel, INDEX_FIRST_EVENT, SEVENTH);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_UNDO_ASSERTION_ERROR;
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, editEventCommandOne, editEventCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, editEventCommandOne, editEventCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException {
            throw new DuplicateEventException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws EventNotFoundException {
            throw new EventNotFoundException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\undo\command\test\UndoPortraitCommandTest.java
``` java
/**
 * Test the undo function of PortraitCommand
 */
public class UndoPortraitCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    private PortraitCommand portraitCommandOne;
    private PortraitCommand portraitCommandTwo;
    private Person modifiedFirstPerson;

    @Before
    public void setUp() throws Exception {
        PortraitPath firstPortrait = new PortraitPath(VALID_PORTRAIT_PATH_FIRST);
        PortraitPath secondPortrait = new PortraitPath(VALID_PORTRAIT_PATH_SECOND);
        portraitCommandOne = new PortraitCommand(INDEX_FIRST_PERSON, firstPortrait);
        portraitCommandTwo = new PortraitCommand(INDEX_SECOND_PERSON, secondPortrait);
        modifiedFirstPerson = new PersonBuilder(model.getFilteredPersonList()
            .get(INDEX_FIRST_PERSON.getZeroBased())).withPortrait(VALID_PORTRAIT_PATH_FIRST).build();
    }

    @Test
    public void execute() throws Exception {
        UndoCommand undoCommand = prepareUndoCommand(model, portraitCommandOne, portraitCommandTwo);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        portraitCommandOne.execute();
        portraitCommandTwo.execute();

        // multiple commands in undoStack
        modifyPerson(expectedModel, INDEX_FIRST_PERSON, modifiedFirstPerson);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeWithAssertionError() throws Exception {
        ModelStub modelStubOne = new ModelStubThrowExceptionOne();
        ModelStub modelStubTwo = new ModelStubThrowExceptionTwo();
        String expectedMessage = Messages.MESSAGE_UNDO_ASSERTION_ERROR;
        UndoCommand undoCommandOne = prepareUndoCommand(modelStubOne, portraitCommandOne, portraitCommandTwo);
        assertCommandAssertionError(undoCommandOne, expectedMessage);

        UndoCommand undoCommandTwo = prepareUndoCommand(modelStubTwo, portraitCommandOne, portraitCommandTwo);
        assertCommandAssertionError(undoCommandTwo, expectedMessage);
    }

    private class ModelStubThrowExceptionOne extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }
    }

    private class ModelStubThrowExceptionTwo extends ModelStub {
        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }
    }
}
```
###### \java\seedu\address\logic\commands\UndoRedoCommandUtil.java
``` java

/**
 * Refactor the undo/redo tests. Extract some common parts as util
 */
public class UndoRedoCommandUtil {
    public static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    public static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    /**
     * Create a RedoCommand by given model and 2 commands
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoableCommand commandOne, UndoableCommand commandTwo) {
        commandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        commandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        UndoRedoStack undoRedoStack = prepareStack(Collections.emptyList(), Arrays.asList(commandTwo, commandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        return redoCommand;
    }

    /**
     * Create an UndoCommand by given model and 2 commands
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoableCommand commandOne, UndoableCommand commandTwo) {
        commandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        commandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        UndoRedoStack undoRedoStack = prepareStack(Arrays.asList(commandOne, commandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        return undoCommand;
    }


    /**
     * Execute the undo/redo part with only assertion error
     */
    public static void assertCommandAssertionError(Command command, String expectedMessage) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (AssertionError ae) {
            assertEquals(expectedMessage, ae.getMessage());
        } catch (Exception e) {
            fail("Impossible");
        }
    }

    /**
     * Execute commands to store some data, but does not affect model
     */
    public static void executeAndRecover(Model model, UndoableCommand commandOne, UndoableCommand commandTwo) {
        Model modelCopy = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        commandOne.setData(modelCopy, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        commandTwo.setData(modelCopy, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        try {
            commandOne.execute();
            commandTwo.execute();
        } catch (CommandException e) {
            fail("Impossible");
        }

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

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_EVENT_NAME + EVENT_DESC_FIRST
                + INVALID_EVENT_TIME_SECOND, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandAddEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(AddEventCommand.COMMAND_WORD + " "
                + EventsUtil.getEventDetails(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommandDisjoin() throws Exception {
        Index personIndex = INDEX_SECOND_PERSON;
        Index eventIndex = INDEX_FIRST_EVENT;
        DisjoinCommand command = (DisjoinCommand) parser.parseCommand(DisjoinCommand.COMMAND_WORD + " "
                + PREFIX_EVENT + eventIndex.getOneBased() + " " + PREFIX_PERSON + personIndex.getOneBased());
        assertEquals(new DisjoinCommand(personIndex, eventIndex), command);
    }

    @Test
    public void parseCommandPortrait() throws Exception {
        PortraitCommand command = (PortraitCommand) parser.parseCommand(PortraitCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PORTRAIT_DESC_FIRST);
        assertEquals(new PortraitCommand(INDEX_FIRST_PERSON, new PortraitPath(VALID_PORTRAIT_PATH_FIRST)), command);
    }

    @Test
    public void parseCommandEditEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventDescriptor descriptor = new EventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + EventsUtil.getEventDetails(event));
        assertEquals(new EditEventCommand(INDEX_FIRST_PERSON, descriptor), command);
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
###### \java\seedu\address\logic\parser\EditEventCommandParserTest.java
``` java
/**
 * Test the EditEventCommandParser
 */
public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parseMissingPartsFailure() {
        // no index specified
        assertParseFailure(parser, EVENT_NAME_FIRST, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidParametersFailure() {
        // negative index
        assertParseFailure(parser, "-5" + EVENT_NAME_FIRST, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENT_NAME_FIRST, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 not an integer", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidValueFailure() {
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME,
            EventName.MESSAGE_EVENT_NAME_CONSTRAINTS); // invalid name

        assertParseFailure(parser, "1" + INVALID_EVENT_DESC,
            EventDescription.MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS); // invalid phone

        assertParseFailure(parser, "1" + INVALID_EVENT_TIME_FIRST,
            EventTime.MESSAGE_EVENT_TIME_CONSTRAINTS); // invalid email
    }

    @Test
    public void parseAllFieldsSpecified() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + EVENT_DESC_SECOND + EVENT_TIME_SECOND + EVENT_NAME_FIRST;

        EditEventDescriptor descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST)
            .withDescription(VALID_EVENT_DESC_SECOND).withTime(VALID_EVENT_TIME_SECOND).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parsePartFieldsSpecified() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_FIRST;
        EditEventDescriptor descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_DESC_SECOND;
        descriptor = new EventDescriptorBuilder().withDescription(VALID_EVENT_DESC_SECOND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_TIME_SECOND;
        descriptor = new EventDescriptorBuilder().withTime(VALID_EVENT_TIME_SECOND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_DESC_SECOND + EVENT_TIME_SECOND;
        descriptor = new EventDescriptorBuilder().withDescription(VALID_EVENT_DESC_SECOND)
            .withTime(VALID_EVENT_TIME_SECOND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_TIME_SECOND + EVENT_NAME_FIRST;
        descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST)
            .withTime(VALID_EVENT_TIME_SECOND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_DESC_SECOND + EVENT_NAME_FIRST;
        descriptor = new EventDescriptorBuilder().withDescription(VALID_EVENT_DESC_SECOND)
            .withName(VALID_EVENT_NAME_FIRST).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
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
###### \java\seedu\address\model\event\EventTimeTest.java
``` java
        // Add more boundary tests
        assertFalse(EventTime.isValidEventTime("29/02/2100")); // not a leap year
        assertFalse(EventTime.isValidEventTime("30/02/2000")); // No 30th day in Feb
        assertFalse(EventTime.isValidEventTime("3/02/2000")); // should be 03 instead of 3
        assertFalse(EventTime.isValidEventTime("30/2/2000")); // should be 02 instead of 2
        assertFalse(EventTime.isValidEventTime("30/02/1899")); //Before year 1900

        assertTrue(EventTime.isValidEventTime("29/02/2000"));
        assertTrue(EventTime.isValidEventTime("29/02/2004"));
        assertTrue(EventTime.isValidEventTime("31/12/2003"));
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
        assertTrue(PortraitPath.isValidPortraitPath("G:/Name_mixed/ with -every\thi-ng 1234.png"));
    }
}
```
###### \java\seedu\address\storage\PersonEventInteractionStorageTest.java
``` java
/**
 * Test the XmlAdaptedEventNoParticipant and XmlAdaptedPersonNoParticipation
 * Only test the save and read behavior
 * Integrate with model
 */
public class PersonEventInteractionStorageTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private XmlAddressBookStorage xmlAddressBookStorage;
    private XmlEventStorage xmlEventListStorage;
    private String addressBookPath;
    private String eventListPath;

    @Before
    public void setUp() {
        joinEvents(model);
        addressBookPath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        eventListPath = testFolder.getRoot().getPath() + "TempEventList.xml";
        xmlAddressBookStorage = new XmlAddressBookStorage(addressBookPath);
        xmlEventListStorage = new XmlEventStorage(eventListPath);
    }

    @Test
    public void testSaveAndRead() throws Exception {
        assertsaveAndReadSuccess();

        // Disjoin an event
        Person person = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Event event = new Event(model.getFilteredEventList().get(INDEX_SECOND_EVENT.getZeroBased()));
        model.quitEvent(person, event);
        assertsaveAndReadSuccess();

        // Join an event
        person = new Person(model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased()));
        event = new Event(model.getFilteredEventList().get(INDEX_THIRD_EVENT.getZeroBased()));
        model.joinEvent(person, event);
        assertsaveAndReadSuccess();
    }

    /**
     * Save and read back the model, and check whether they are the same
     */
    private void assertsaveAndReadSuccess() throws Exception {
        xmlAddressBookStorage.saveAddressBook(model.getAddressBook(), addressBookPath);
        xmlEventListStorage.saveEventStorage(model.getEventList(), eventListPath);
        ReadOnlyAddressBook readBackPerson = xmlAddressBookStorage.readAddressBook(addressBookPath).get();
        ReadOnlyEventList readBackEvent = xmlEventListStorage.readEventStorage(eventListPath).get();
        assertEquals(model.getAddressBook(), new AddressBook(readBackPerson));
        assertEquals(model.getEventList(), new EventList(readBackEvent));
    }
}
```
