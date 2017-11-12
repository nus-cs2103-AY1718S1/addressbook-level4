# junyango
###### \java\guitests\guihandles\event\EventCardHandle.java
``` java
/**
 * Provides a handle to an event card in the {@code EventListPanel}.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String VENUE_FIELD_ID = "#venue";
    private static final String TIME_FIELD_ID = "#time";


    private final Label idLabel;
    private final Label nameLabel;
    private final Label venueLabel;
    private final Label timeLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.venueLabel = getChildNode(VENUE_FIELD_ID);
        this.timeLabel = getChildNode(TIME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEventName() {
        return nameLabel.getText();
    }

    public String getVenue() {
        return venueLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

}
```
###### \java\guitests\guihandles\event\EventListPanelHandle.java
``` java
/**
 * Provides a handle for {@code EventListPanel} containing the list of {@code EventCard}.
 */
public class EventListPanelHandle extends NodeHandle<ListView<EventCard>> {
    public static final String EVENT_LIST_VIEW_ID = "#eventListView";

    private Optional<EventCard> lastRememberedSelectedEventCard;

    public EventListPanelHandle(ListView<EventCard> eventListPanelNode) {
        super(eventListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code EventCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public EventCardHandle getHandleToSelectedCard() {
        List<EventCard> eventList = getRootNode().getSelectionModel().getSelectedItems();

        if (eventList.size() != 1) {
            throw new AssertionError("Event list size expected 1.");
        }

        return new EventCardHandle(eventList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<EventCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the event.
     */
    public void navigateToCard(ReadOnlyEvent event) {
        List<EventCard> cards = getRootNode().getItems();
        Optional<EventCard> matchingCard = cards.stream().filter(card -> card.event.equals(event)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Event does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the event card handle of a event associated with the {@code index} in the list.
     */
    public EventCardHandle getEventCardHandle(int index) {
        return getEventCardHandle(getRootNode().getItems().get(index).event);
    }

    /**
     * Returns the {@code EventCardHandle} of the specified {@code event} in the list.
     */
    public EventCardHandle getEventCardHandle(ReadOnlyEvent event) {
        Optional<EventCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.event.equals(event))
                .map(card -> new EventCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Event does not exist."));
    }

    /**
     * Selects the {@code EventCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code EventCard} in the list.
     */
    public void rememberSelectedEventCard() {
        List<EventCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedEventCard = Optional.empty();
        } else {
            lastRememberedSelectedEventCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code EventCard} is different from the value remembered by the most recent
     * {@code rememberSelectedEventCard()} call.
     */
    public boolean isSelectedEventCardChanged() {
        List<EventCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedEventCard.isPresent();
        } else {
            return !lastRememberedSelectedEventCard.isPresent()
                    || !lastRememberedSelectedEventCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\seedu\address\commons\events\ui\SwitchThemeEventTest.java
``` java
public class SwitchThemeEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new SwitchThemeEvent();
        assertEquals("SwitchThemeEvent", event.toString());
    }
}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getAddressBook().getEventList().get(0);
        final String[] splitName = event.getName().getValue().split("\\s+");
        model.updateFilteredEventsList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredEventList().size() == 1;
    }
```
###### \java\seedu\address\logic\commands\event\AddEventCommandTest.java
``` java

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
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
        Event hack = new EventBuilder().withName("Hack").build();
        Event test = new EventBuilder().withName("Test").build();
        AddEventCommand addHackCommand = new AddEventCommand(hack);
        AddEventCommand addTestCommand = new AddEventCommand(test);

        // same object -> returns true
        assertTrue(addHackCommand.equals(addHackCommand));

        // same values -> returns true
        AddEventCommand addHackCommandCopy = new AddEventCommand(hack);
        assertTrue(addHackCommand.equals(addHackCommandCopy));

        // different types -> returns false
        assertFalse(addHackCommand.equals(1));

        // null -> returns false
        assertFalse(addHackCommand.equals(null));

        // different event -> returns false
        assertFalse(addHackCommand.equals(addTestCommand));
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
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            eventsAdded.add(new Event(event));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\event\DeleteEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        DeleteEventCommand deleteFirstCommand = new DeleteEventCommand(INDEX_FIRST_PERSON);
        DeleteEventCommand deleteSecondCommand = new DeleteEventCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteEventCommand} with the parameter {@code index}.
     */
    private DeleteEventCommand prepareCommand(Index index) {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(index);
        deleteEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteEventCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventsList(p -> false);

        assert model.getFilteredEventList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\event\EditEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEventCommand.
 */
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Event editedEvent = new EventBuilder().build();
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        ReadOnlyEvent lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList.withName(VALID_NAME_EVENT1).withDateTime(VALID_DATE_EVENT1).build();

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1)
                .withTime(VALID_DATE_EVENT1).build();
        EditEventCommand editEventCommand = prepareCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(lastEvent, editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editEventCommand =
                prepareCommand(INDEX_FIRST_PERSON, new EditEventCommand.EditEventDescriptor());
        ReadOnlyEvent editedEvent = model.getFilteredEventList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_PERSON.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withName(VALID_NAME_EVENT1).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_PERSON,
                new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = new Event(model.getFilteredEventList().get(INDEX_FIRST_PERSON.getZeroBased()));
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showFirstEventOnly(model);

        // edit person in filtered list into a duplicate in address book
        ReadOnlyEvent eventInList = model.getAddressBook().getEventList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_PERSON,
                new EditEventDescriptorBuilder(eventInList).build());

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventCommand.EditEventDescriptor descriptor =
                new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1).build();
        EditEventCommand editEventCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showFirstEventOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EditEventCommand editEventCommand = prepareCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1).build());

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_PERSON, DESC_EVENT1);

        // same values -> returns true
        EditEventCommand.EditEventDescriptor copyDescriptor = new EditEventCommand.EditEventDescriptor(DESC_EVENT1);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_PERSON, DESC_EVENT1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_PERSON, DESC_EVENT2)));
    }

    /**
     * Returns an {@code EditEventCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEventCommand prepareCommand(Index index, EditEventCommand.EditEventDescriptor descriptor) {
        EditEventCommand editEventCommand = new EditEventCommand(index, descriptor);
        editEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editEventCommand;
    }
}
```
###### \java\seedu\address\logic\commands\event\EditEventDescriptorTest.java
``` java
public class EditEventDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditEventCommand.EditEventDescriptor descriptorWithSameValues =
                new EditEventCommand.EditEventDescriptor(DESC_EVENT1);
        assertTrue(DESC_EVENT1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EVENT1.equals(DESC_EVENT1));

        // null -> returns false
        assertFalse(DESC_EVENT1.equals(null));

        // different types -> returns false
        assertFalse(DESC_EVENT1.equals(5));

        // different values -> returns false
        assertFalse(DESC_EVENT1.equals(DESC_EVENT2));

        // different name -> returns false
        EditEventCommand.EditEventDescriptor editedEvent1 =
                new EditEventDescriptorBuilder(DESC_EVENT1).withName(VALID_NAME_EVENT2).build();
        assertFalse(DESC_EVENT1.equals(editedEvent1));

        // different Time -> returns false
        editedEvent1 = new EditEventDescriptorBuilder(DESC_EVENT1).withTime(VALID_DATE_EVENT2).build();
        assertFalse(DESC_EVENT1.equals(editedEvent1));


        // different address -> returns false
        editedEvent1 = new EditEventDescriptorBuilder(DESC_EVENT1).withAddress(VALID_VENUE_EVENT2).build();
        assertFalse(DESC_EVENT1.equals(editedEvent1));
    }
}
```
###### \java\seedu\address\logic\commands\event\ListEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListEventCommandTest {

    private Model model;
    private Model expectedModel;
    private ListEventCommand listEventCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listEventCommand = new ListEventCommand();
        listEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listEventCommand, model, ListEventCommand.MESSAGE_EVENT_SUCCESS, expectedModel);
    }
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstEventOnly(model);
        assertCommandSuccess(listEventCommand, model, ListEventCommand.MESSAGE_EVENT_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\SwitchThemeTest.java
``` java

public class SwitchThemeTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_switch_success() {
        CommandResult result = new SwitchThemeCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof SwitchThemeEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}



```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddEvent(event));
        assertEquals(new AddEventCommand(event), command);
    }
    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_PERSON), command);
    }
    @Test
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + EventUtil.getEventDetails(event));
        assertEquals(new EditEventCommand(INDEX_FIRST_PERSON, descriptor), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_theme() throws Exception {
        assertTrue(parser.parseCommand(SwitchThemeCommand.COMMAND_WORD) instanceof SwitchThemeCommand);
        assertTrue(parser.parseCommand(SwitchThemeCommand.COMMAND_WORD + " 3") instanceof SwitchThemeCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_listEvents() throws Exception {
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD) instanceof ListEventCommand);
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD + " 3") instanceof ListEventCommand);
    }
```
###### \java\seedu\address\logic\parser\event\DeleteEventCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteEventCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteEventCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteEventCommandParserTest {

    private DeleteEventParser parser = new DeleteEventParser();

    @Test
    public void parse_validArgs_returnsDeleteEventCommand() {
        assertParseSuccess(parser, "1", new DeleteEventCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format
                (MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\event\EditEventCommandParserTest.java
``` java
public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventParser parser = new EditEventParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_EVENT1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                PropertyManager.getPropertyConstraintMessage("n")); // invalid name
        assertParseFailure(parser, "1" + INVALID_DATE_DESC,
                PropertyManager.getPropertyConstraintMessage("dt")); // invalid date time
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC,
                PropertyManager.getPropertyConstraintMessage("a")); // invalid address


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DATE_DESC + VALID_VENUE_EVENT2,
                PropertyManager.getPropertyConstraintMessage("n"));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1 + NAME_DESC_EVENT1;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1)
                .withTime(VALID_DATE_EVENT1).withAddress(VALID_VENUE_EVENT1).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DATE_DESC_EVENT2 + VENUE_DESC_EVENT1;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTime(VALID_DATE_EVENT2)
                .withAddress(VALID_VENUE_EVENT1).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_EVENT1;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date/time
        userInput = targetIndex.getOneBased() + DATE_DESC_EVENT1;
        descriptor = new EditEventDescriptorBuilder().withTime(VALID_DATE_EVENT1).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + VENUE_DESC_EVENT1;
        descriptor = new EditEventDescriptorBuilder().withAddress(VALID_VENUE_EVENT1).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEventList().remove(0);
    }
    @Test
    public void removeEvent_eventNotFound_expectException() throws Exception {
        thrown.expect(EventNotFoundException.class);

        AddressBook addressBook = getTypicalAddressBook();
        addressBook.removeEvent(EVENT1);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    /**
     * A stub of {@link ReadOnlyAddressBook} whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends ReadOnlyEvent> events,
                        Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.events.setAll(events);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
```
###### \java\seedu\address\model\event\EventTest.java
``` java
public class EventTest {
    private static Name name;
    private static DateTime dateTime;
    private static Address address;
    private static Set<Property> properties;

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();

        name = new Name(VALID_NAME_EVENT1);
        dateTime = new DateTime(VALID_DATE_EVENT1);
        address = new Address(VALID_ADDRESS_AMY);

        properties = new HashSet<>();
        properties.add(name);
        properties.add(dateTime);
        properties.add(address);
    }

    @Test
    public void createEvent_preDefinedFieldsPresent_checkCorrectness() throws Exception {
        Event event = new Event(name, dateTime, address, Collections.emptyList());
        assertNotNull(event);

        assertEquals(name, event.getName());
        assertEquals(dateTime, event.getTime());
        assertEquals(address, event.getAddress());
        assertEquals(0, event.getReminders().size());
        assertEquals(3, event.getProperties().size());
    }

```
###### \java\seedu\address\model\event\EventTest.java
``` java
    @Test
    public void equal_twoSameStateEvent_checkCorrectness() throws Exception {
        Event event = new Event(name, dateTime, address, new ArrayList<>());
        Event another = new Event(name, dateTime, address, new ArrayList<>());
        assertEquals(event, another);

        Event copied = new Event(event);
        assertEquals(event, copied);
    }

```
###### \java\seedu\address\model\event\UniqueEventListTest.java
``` java
public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEventList().remove(0);
    }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void addPerson_successfullyAddEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyPerson> persons = modelManager.getAddressBook().getPersonList();
        int originalPersonListSize = persons.size();
        modelManager.addPerson(TypicalPersons.HOON);
        int newPersonListSize = modelManager.getAddressBook().getPersonList().size();
        assertEquals(1, newPersonListSize - originalPersonListSize);
    }

    @Test
    public void sortEventList_successfullySortEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager1 = new ModelManager(addressBook, userPrefs);
        modelManager1.addEvent(EVENT2);
        modelManager1.addEvent(EVENT1);

        ModelManager modelManager2 = new ModelManager(addressBook, userPrefs);
        modelManager2.addEvent(EVENT1);
        modelManager2.addEvent(EVENT2);

        assertEquals(modelManager1, modelManager2);
    }

    @Test
    public void addEvent_successfullyAddEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyEvent> events = modelManager.getAddressBook().getEventList();
        int originalEventListSize = events.size();
        modelManager.addEvent(EVENT1);
        int newEventListSize = modelManager.getAddressBook().getEventList().size();
        assertEquals(1, newEventListSize - originalEventListSize);
    }
    @Test
    public void removePerson_successfullyRemoveEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyPerson> persons = modelManager.getAddressBook().getPersonList();
        int originalPersonListSize = persons.size();
        modelManager.deletePerson(persons.get(1));
        int newPersonListSize = modelManager.getAddressBook().getPersonList().size();
        assertEquals(1, originalPersonListSize - newPersonListSize);
    }

    @Test
    public void removeEvent_successfullyRemoveEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyEvent> events = modelManager.getAddressBook().getEventList();
        int originalEventListSize = events.size();
        modelManager.addEvent(EVENT1);
        modelManager.addEvent(EVENT2);
        modelManager.deleteEvent(events.get(1));
        int newEventListSize = modelManager.getAddressBook().getEventList().size();
        assertEquals(1, newEventListSize - originalEventListSize);
    }
    @Test
    public void addEvent_successfullyAddReminder() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyEvent> events = modelManager.getAddressBook().getEventList();
        modelManager.addEvent(EVENT1);
        Reminder r = new Reminder((Event) EVENT1, "You have an event today");
        events.get(0).addReminder(r);
        events.get(0).getReminders().size();
        assertEquals(1, events.get(0).getReminders().size());
    }

```
###### \java\seedu\address\model\property\DateTimeTest.java
``` java
public class DateTimeTest {
    @BeforeClass
    public static void setUp() {
        PropertyManager.initializePropertyManager();
    }

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(DateTime.isValidTime("")); // empty string
        assertFalse(DateTime.isValidTime("some random staff")); // unrelated string

        // valid time
        assertTrue(DateTime.isValidTime("25122015 08:30"));
        assertTrue(DateTime.isValidTime("14122016 13:30"));
        assertTrue(DateTime.isValidTime("09121924 23:30"));
    }

```
###### \java\seedu\address\model\property\EventNameContainsKeywordsPredicateTest.java
``` java
public class EventNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventNameContainsKeywordsPredicate firstPredicateCopy =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new EventBuilder().withName("Alice Bob").build()));
    }
}
```
###### \java\seedu\address\model\reminder\ReminderTest.java
``` java
public class ReminderTest {
    private static Event event;
    private static String message;

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();

        event = new Event(EVENT1);
        message = "You have an event";

    }
    @Test
    public void createReminder_preDefinedFieldsPresent_checkCorrectness() throws Exception {
        Reminder reminder = new Reminder(event, message);
        assertNotNull(reminder);

        assertEquals(event, reminder.getEvent());
        assertEquals(message, reminder.getMessage());
    }

    @Test
    public void setMessage_test_checkCorrectness() {
        Reminder reminder = new Reminder(event, message);
        assertNotNull(reminder);

        reminder.setMessage("test");
        assertEquals("test", reminder.getMessage());
    }

    @Test
    public void equal_twoSameTag_checkCorrectness() throws Exception {
        Reminder reminder1 = new Reminder((Event) EVENT1, EVENT1.getName().toString());
        Reminder reminder2 = new Reminder((Event) EVENT1, EVENT1.getName().toString());

        assertEquals(reminder1, reminder2);
    }

```
###### \java\seedu\address\testutil\EditEventDescriptorBuilder.java
``` java
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.util.ParserUtil;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(ReadOnlyEvent event) {
        descriptor = new EditEventDescriptor();
        descriptor.setName(event.getName());
        descriptor.setTime(event.getTime());
        descriptor.setAddress(event.getAddress());
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTime(String dateTime) {
        try {
            ParserUtil.parseTime(Optional.of(dateTime)).ifPresent(descriptor::setTime);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("date/time is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setAddress);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("Address is expected to be unique.");
        }
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\EventBuilder.java
``` java



/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_EVENT_NAME = "Hack Your Way 2017";
    public static final String DEFAULT_TIME = "25102010 12:00";
    public static final String DEFAULT_VENUE = "123, Clementi West Ave 6, #08-123";

    private Event event;

    static {
        PropertyManager.initializePropertyManager();
    }

    public EventBuilder() {
        try {
            Name defaultEventName = new Name(DEFAULT_EVENT_NAME);
            DateTime defaultTime = new DateTime(DEFAULT_TIME);
            Address defaultAddress = new Address(DEFAULT_VENUE);
            ArrayList<Reminder> defaultReminder = new ArrayList<>();
            this.event = new Event(defaultEventName, defaultTime, defaultAddress, defaultReminder);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Initializes the EventBuilder with the data of {@code EventToCopy}.
     */
    public EventBuilder(ReadOnlyEvent eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        try {
            this.event.setName(new Name(name));
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Adds a reminder into the event.
     */
    public EventBuilder withReminder() {
        this.event.getReminders().add(new Reminder(event, event.getTime().toString()));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withAddress(String address) {
        try {
            this.event.setAddress(new Address(address));
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date Time} of the {@code Event} that we are building.
     */
    public EventBuilder withDateTime(String time) {
        try {
            this.event.setDateTime(new DateTime(time));
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("Date and Time are expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
```
###### \java\seedu\address\testutil\EventUtil.java
``` java
/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getAddEvent(ReadOnlyEvent event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(ReadOnlyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName().toString() + " ");
        sb.append(PREFIX_DATE_TIME + event.getTime().toString() + " ");
        sb.append(PREFIX_ADDRESS + event.getAddress().toString());
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalEvents.java
``` java

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent EVENT1 = new EventBuilder().withName("HHN 6001")
            .withDateTime("22022015 08:30")
            .withAddress("123, Sentosa, #08-111").withReminder().build();
    public static final ReadOnlyEvent EVENT2 = new EventBuilder().withName("ZoukOut 6001")
            .withDateTime("25122017 10:30")
            .withAddress("123, Clarke Quay #01-111").withReminder().build();

    // Manually added
    public static final ReadOnlyEvent EVENTM1 = new EventBuilder().withName("Volleyball Tour 17")
            .withDateTime("25122017 08:30")
            .withAddress("OCBC ARENA Hall 3, #01-111").withReminder().build();
    public static final ReadOnlyEvent EVENTM2 = new EventBuilder().withName("Meeting with Jason")
            .withDateTime("25112016 02:30")
            .withAddress("123, Sheraton Towers , #06-111").withReminder().build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyEvent EV1 = new EventBuilder().withName(VALID_NAME_EVENT1)
            .withDateTime(VALID_DATE_EVENT1).withAddress(VALID_VENUE_EVENT1).build();
    public static final ReadOnlyEvent EV2 = new EventBuilder().withName(VALID_NAME_EVENT2)
            .withDateTime(VALID_DATE_EVENT2).withAddress(VALID_VENUE_EVENT2).build();


    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyEvent events : getTypicalEvents()) {
            try {
                ab.addEvent(events);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }
    static {
        PropertyManager.initializePropertyManager();
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT1, EVENT2));
    }
}
```
###### \java\systemtests\AddEventCommandSystemTest.java
``` java

public class AddEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        showAllEvents();
        Model model = getModel();
        /* Case: add a event without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyEvent toAdd = EV1;
        String command = "   " + AddEventCommand.COMMAND_WORD + "  " + NAME_DESC_EVENT1 + "  " + DATE_DESC_EVENT1
                + " " + VENUE_DESC_EVENT1;
        String inputCommand = command;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding EV1 to the list -> EV1 deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = String.format(UndoCommand.MESSAGE_SUCCESS, inputCommand);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding EV1 to the list -> EV1 added again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = String.format(RedoCommand.MESSAGE_SUCCESS, inputCommand);
        model.addEvent(toAdd);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate event -> rejected */
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandFailure(command, AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a event with all fields same as another event in the address book except name -> added */
        toAdd = new EventBuilder().withName(VALID_NAME_EVENT2).withDateTime(VALID_DATE_EVENT1)
                .withAddress(VALID_VENUE_EVENT1).build();
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT2 + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a event with all fields same as another event in the address book except date -> added */
        toAdd = new EventBuilder().withName(VALID_NAME_EVENT1).withDateTime(VALID_DATE_EVENT2)
                .withAddress(VALID_VENUE_EVENT1).build();
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT2 + VENUE_DESC_EVENT1;
        assertCommandSuccess(command, toAdd);


        /* Case: add a event with all fields same as another event in the address book except address -> added */
        toAdd = new EventBuilder().withName(VALID_NAME_EVENT1).withDateTime(VALID_DATE_EVENT1)
                .withAddress(VALID_VENUE_EVENT2).build();
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1 + VENUE_DESC_EVENT2;
        assertCommandSuccess(command, toAdd);


        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getEventList().size() == 0;
        assertCommandSuccess(EVENT1);

        /* Case: missing name -> rejected */
        command = AddEventCommand.COMMAND_WORD + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addEs " + EventUtil.getEventDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddEventCommand.COMMAND_WORD + INVALID_NAME_DESC + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandFailure(command, PropertyManager.getPropertyConstraintMessage("n"));

        /* Case: invalid address -> rejected */
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1 + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, PropertyManager.getPropertyConstraintMessage("a"));
    }

    /**
     * Executes the {@code AddEventCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing
     * {@code AddEventCommand} with the of {@code toAdd}, and the model related components equal to the current model
     * added with {@code toAdd}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyEvent toAdd) {
        assertCommandSuccess(EventUtil.getAddEvent(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyEvent)}. Executes {@code command}
     * instead.
     * @see AddEventCommandSystemTest#assertCommandSuccess(ReadOnlyEvent)
     */
    private void assertCommandSuccess(String command, ReadOnlyEvent toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addEvent(toAdd);
        } catch (DuplicateEventException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyEvent)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddEventCommandSystemTest#assertCommandSuccess(String, ReadOnlyEvent)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\util\ModelHelper.java
``` java

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyEvent} equals to {@code other}.
     */
    private static Predicate<ReadOnlyEvent> getPredicateMatching(ReadOnlyEvent other) {
        return event -> event.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyEvent} equals to {@code other}.
     */
    private static Predicate<ReadOnlyPerson> getPredicateMatching(ReadOnlyPerson other) {
        return person -> person.equals(other);
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredEventsList(Model model, List<ReadOnlyEvent> toDisplay) {
        Optional<Predicate<ReadOnlyEvent>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredEventsList(predicate.orElse(PREDICATE_MATCHING_NO_EVENTS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredEventsList(Model model, ReadOnlyEvent... toDisplay) {
        setFilteredEventsList(model, Arrays.asList(toDisplay));
    }
}


```
