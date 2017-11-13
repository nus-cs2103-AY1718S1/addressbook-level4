# royceljh
###### \java\seedu\address\logic\commands\AddEventCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() throws Exception {
        Event validEvent = new EventBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEvent(validEvent);

        assertCommandSuccess(prepareCommand(validEvent, model), model,
                String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event eventInList = new Event(model.getAddressBook().getEventList().get(0));
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
        Event meeting = new EventBuilder().withHeader("Meeting").build();
        Event birthday = new EventBuilder().withHeader("Birthday").build();
        AddEventCommand addMeetingCommand = new AddEventCommand(meeting);
        AddEventCommand addBirthdayCommand = new AddEventCommand(birthday);

        // same object -> returns true
        assertTrue(addMeetingCommand.equals(addMeetingCommand));

        // same values -> returns true
        AddEventCommand addMeetingCommandCopy = new AddEventCommand(meeting);
        assertTrue(addMeetingCommand.equals(addMeetingCommandCopy));

        // different types -> returns false
        assertFalse(addMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetingCommand.equals(null));

        // different event -> returns false
        assertFalse(addMeetingCommand.equals(addBirthdayCommand));
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
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Updates {@code model}'s filtered list to show only the first event in the {@code model}'s address book.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getAddressBook().getEventList().get(0);
        final String[] splitHeader = event.getHeader().value.split("\\s+");
        model.updateFilteredEventList(new HeaderContainsKeywordsPredicate(Arrays.asList(splitHeader[0])));

        assert model.getFilteredEventList().size() == 1;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstEventOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
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
        model.updateFilteredEventList(e -> false);

        assert model.getFilteredEventList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\EditEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEventCommand.
 */
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT, new EditEventDescriptor());
        ReadOnlyEvent editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withHeader(VALID_HEADER_BIRTHDAY).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder().withHeader(VALID_HEADER_BIRTHDAY).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = new Event(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_SECOND_EVENT, descriptor);

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showFirstEventOnly(model);

        // edit event in filtered list into a duplicate in address book
        ReadOnlyEvent eventInList = model.getAddressBook().getEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder(eventInList).build());

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withHeader(VALID_HEADER_BIRTHDAY).build();
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
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EditEventCommand editEventCommand = prepareCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withHeader(VALID_HEADER_BIRTHDAY).build());

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_EVENT, DESC_MEETING);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_MEETING);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_EVENT, DESC_MEETING)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_EVENT, DESC_BIRTHDAY)));
    }

    /**
     * Returns an {@code EditEventCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEventCommand prepareCommand(Index index, EditEventDescriptor descriptor) {
        EditEventCommand editEventCommand = new EditEventCommand(index, descriptor);
        editEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editEventCommand;
    }
}
```
###### \java\seedu\address\logic\commands\EditEventDescriptorTest.java
``` java
public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_MEETING);
        assertTrue(DESC_MEETING.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MEETING.equals(DESC_MEETING));

        // null -> returns false
        assertFalse(DESC_MEETING.equals(null));

        // different types -> returns false
        assertFalse(DESC_MEETING.equals(5));

        // different values -> returns false
        assertFalse(DESC_MEETING.equals(DESC_BIRTHDAY));

        // different header -> returns false
        EditEventDescriptor editedMeeting = new EditEventDescriptorBuilder(DESC_MEETING)
                .withHeader(VALID_HEADER_BIRTHDAY).build();
        assertFalse(DESC_MEETING.equals(editedMeeting));

        // different desc -> returns false
        editedMeeting = new EditEventDescriptorBuilder(DESC_MEETING).withDesc(VALID_DESC_BIRTHDAY).build();
        assertFalse(DESC_MEETING.equals(editedMeeting));

        // different eventDate -> returns false
        editedMeeting = new EditEventDescriptorBuilder(DESC_MEETING).withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        assertFalse(DESC_MEETING.equals(editedMeeting));
    }
}
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredEventList().remove(0);
    }
```
###### \java\seedu\address\logic\parser\AddEventCommandParserTest.java
``` java
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY).withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();

        // multiple header - last header accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_MEETING + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY, new AddEventCommand(expectedEvent));

        // multiple desc - last desc accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + DESC_DESC_MEETING + DESC_DESC_BIRTHDAY
                + EVENT_DATE_DESC_BIRTHDAY, new AddEventCommand(expectedEvent));

        // multiple eventDates - last eventDate accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_MEETING
                + EVENT_DATE_DESC_BIRTHDAY, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing header prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_HEADER_BIRTHDAY
                + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY, expectedMessage);

        // missing desc prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + VALID_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY, expectedMessage);

        // missing eventDate prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + VALID_EVENT_DATE_BIRTHDAY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid header
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_HEADER_DESC + DESC_DESC_BIRTHDAY
                + EVENT_DATE_DESC_BIRTHDAY, Header.MESSAGE_HEADER_CONSTRAINTS);

        // invalid desc
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + INVALID_DESC_DESC + EVENT_DATE_DESC_BIRTHDAY, Desc.MESSAGE_DESC_CONSTRAINTS);

        // invalid eventDate
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + INVALID_EVENT_DATE_DESC, EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(event), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_EVENT), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EVENT.getOneBased() + " " + EventUtil.getEventDetails(event));
        assertEquals(new EditEventCommand(INDEX_FIRST_EVENT, descriptor), command);
    }
```
###### \java\seedu\address\logic\parser\DeleteEventCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteEventCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteEventCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteEventCommand() {
        assertParseSuccess(parser, "1", new DeleteEventCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\EditEventCommandParserTest.java
``` java
public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_HEADER_MEETING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + HEADER_DESC_MEETING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + HEADER_DESC_MEETING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_HEADER_DESC,
                Header.MESSAGE_HEADER_CONSTRAINTS); // invalid header
        assertParseFailure(parser, "1" + INVALID_DESC_DESC,
                Desc.MESSAGE_DESC_CONSTRAINTS); // invalid desc
        assertParseFailure(parser, "1" + INVALID_EVENT_DATE_DESC,
                EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS); // invalid eventDate


        // invalid desc followed by valid eventDate
        assertParseFailure(parser, "1" + INVALID_DESC_DESC + EVENT_DATE_DESC_MEETING,
                Desc.MESSAGE_DESC_CONSTRAINTS);

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + EVENT_DATE_DESC_BIRTHDAY + INVALID_EVENT_DATE_DESC,
                EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_HEADER_DESC + INVALID_DESC_DESC
                + VALID_EVENT_DATE_BIRTHDAY, Header.MESSAGE_HEADER_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_MEETING
                + HEADER_DESC_MEETING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withHeader(VALID_HEADER_MEETING)
                .withDesc(VALID_DESC_BIRTHDAY).withEventDate(VALID_EVENT_DATE_MEETING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_MEETING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_MEETING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // header
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + HEADER_DESC_MEETING;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withHeader(VALID_HEADER_MEETING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // desc
        userInput = targetIndex.getOneBased() + DESC_DESC_MEETING;
        descriptor = new EditEventDescriptorBuilder().withDesc(VALID_DESC_MEETING).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // eventDate
        userInput = targetIndex.getOneBased() + EVENT_DATE_DESC_MEETING;
        descriptor = new EditEventDescriptorBuilder().withEventDate(VALID_EVENT_DATE_MEETING).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_DESC_DESC + DESC_DESC_BIRTHDAY;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDesc(VALID_DESC_BIRTHDAY).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EVENT_DATE_DESC_BIRTHDAY + INVALID_DESC_DESC + DESC_DESC_BIRTHDAY;
        descriptor = new EditEventDescriptorBuilder().withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseHeader_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseHeader(null);
    }

    @Test
    public void parseHeader_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseHeader(Optional.of(INVALID_HEADER));
    }

    @Test
    public void parseHeader_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseHeader(Optional.empty()).isPresent());
    }

    @Test
    public void parseHeader_validValue_returnsHeader() throws Exception {
        Header expectedHeader = new Header(VALID_HEADER);
        Optional<Header> actualHeader = ParserUtil.parseHeader(Optional.of(VALID_HEADER));

        assertEquals(expectedHeader, actualHeader.get());
    }

    @Test
    public void parseDesc_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDesc(null);
    }

    @Test
    public void parseDesc_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDesc(Optional.of(INVALID_DESC));
    }

    @Test
    public void parseDesc_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDesc(Optional.empty()).isPresent());
    }

    @Test
    public void parseDesc_validValue_returnsDesc() throws Exception {
        Desc expectedDesc = new Desc(VALID_DESC);
        Optional<Desc> actualDesc = ParserUtil.parseDesc(Optional.of(VALID_DESC));

        assertEquals(expectedDesc, actualDesc.get());
    }

    @Test
    public void parseEventDate_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEventDate(null);
    }

    @Test
    public void parseEventDate_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEventDate(Optional.of(INVALID_EVENT_DATE));
    }

    @Test
    public void parseEventDate_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEventDate(Optional.empty()).isPresent());
    }

    @Test
    public void parseEventDate_validValue_returnsEventDate() throws Exception {
        EventDate expectedEventDate = new EventDate(VALID_EVENT_DATE);
        Optional<EventDate> actualEventDate = ParserUtil.parseEventDate(Optional.of(VALID_EVENT_DATE));

        assertEquals(expectedEventDate, actualEventDate.get());
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        // Repeat MEETING twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Event> newEvents = Arrays.asList(new Event(MEETING), new Event(MEETING));
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newEvents);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEventList().remove(0);
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }
```
###### \java\seedu\address\model\UniqueEventListTest.java
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
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableAddressBook addressBook = new XmlSerializableAddressBook();
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEventList().remove(0);
    }
```
###### \java\seedu\address\testutil\AddressBookBuilder.java
``` java
    /**
     * Adds a new {@code Event} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withEvent(ReadOnlyEvent event) {
        try {
            addressBook.addEvent(event);
        } catch (DuplicateEventException dee) {
            throw new IllegalArgumentException("event is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\EditEventDescriptorBuilder.java
``` java
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
        descriptor.setHeader(event.getHeader());
        descriptor.setDesc(event.getDesc());
        descriptor.setEventDate(event.getEventDate());
    }

    /**
     * Sets the {@code Header} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withHeader(String header) {
        try {
            ParserUtil.parseHeader(Optional.of(header)).ifPresent(descriptor::setHeader);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("header is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Desc} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDesc(String desc) {
        try {
            ParserUtil.parseDesc(Optional.of(desc)).ifPresent(descriptor::setDesc);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("desc is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventDate(String eventDate) {
        try {
            ParserUtil.parseEventDate(Optional.of(eventDate)).ifPresent(descriptor::setEventDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("eventDate is expected to be unique.");
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

    public static final String DEFAULT_HEADER = "Meeting";
    public static final String DEFAULT_DESC = "location";
    public static final String DEFAULT_EVENT_DATE = "2017-12-20";

    private Event event;

    public EventBuilder() {
        try {
            Header defaultHeader = new Header(DEFAULT_HEADER);
            Desc defaultDesc = new Desc(DEFAULT_DESC);
            EventDate defaultEventDate = new EventDate(DEFAULT_EVENT_DATE);
            this.event = new Event(defaultHeader, defaultDesc, defaultEventDate);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(ReadOnlyEvent eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code Header} of the {@code Event} that we are building.
     */
    public EventBuilder withHeader(String header) {
        try {
            this.event.setHeader(new Header(header));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("header is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Desc} of the {@code Event} that we are building.
     */
    public EventBuilder withDesc(String desc) {
        try {
            this.event.setDesc(new Desc(desc));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code Event} that we are building.
     */
    public EventBuilder withEventDate(String eventDate) {
        try {
            this.event.setEventDate(new EventDate(eventDate));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("eventDate is expected to be unique.");
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
    public static String getAddEventCommand(ReadOnlyEvent event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(ReadOnlyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_HEADER + event.getHeader().value + " ");
        sb.append(PREFIX_DESC + event.getDesc().value + " ");
        sb.append(PREFIX_EVENT_DATE + event.getEventDate().eventLocalDate.toString() + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TestUtil.java
``` java
    /**
     * Returns the last index of the event in the {@code model}'s event list.
     */
    public static Index getEventLastIndex(Model model) {
        return Index.fromOneBased(model.getAddressBook().getEventList().size());
    }

    /**
     * Returns the event in the {@code model}'s event list at {@code index}.
     */
    public static ReadOnlyEvent getEvent(Model model, Index index) {
        return model.getAddressBook().getEventList().get(index.getZeroBased());
    }
}
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final ReadOnlyEvent DATE = new EventBuilder().withHeader("Date").withDesc("dinner at restaurant")
            .withEventDate("2017-12-20").build();
    public static final ReadOnlyEvent MARATHON = new EventBuilder().withHeader("Marathon")
            .withDesc("21km run at sundown").withEventDate("2018-09-01").build();
    public static final ReadOnlyEvent OUTING = new EventBuilder().withHeader("Outing").withDesc("friends")
            .withEventDate("2017-12-12").build();
    public static final ReadOnlyEvent MOVIE = new EventBuilder().withHeader("Movie").withDesc("with date")
            .withEventDate("2017-11-06").build();

    // Manually added
    public static final ReadOnlyEvent MEETING = new EventBuilder().withHeader(VALID_HEADER_MEETING)
            .withDesc(VALID_DESC_MEETING).withEventDate(VALID_EVENT_DATE_MEETING).build();
    public static final ReadOnlyEvent BIRTHDAY = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY)
            .withDesc(VALID_DESC_BIRTHDAY).withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();

```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(DATE, MARATHON, OUTING, MOVIE));
    }
```
###### \java\seedu\address\ui\EventCardTest.java
``` java
public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Event event = new EventBuilder(MEETING).build();
        EventCard eventCard = new EventCard(event, 1);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, event, 1);

        // changes made to Event reflects on card
        guiRobot.interact(() -> {
            event.setHeader(BIRTHDAY.getHeader());
            event.setDesc(BIRTHDAY.getDesc());
            event.setEventDate(BIRTHDAY.getEventDate());
        });
        assertCardDisplay(eventCard, event, 1);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 0);

        // same event, same index -> returns true
        EventCard copy = new EventCard(event, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different event, same index -> returns false
        Event differentEvent = new EventBuilder().withHeader("differentHeader").build();
        assertFalse(eventCard.equals(new EventCard(differentEvent, 0)));

        // same event, different index -> returns false
        assertFalse(eventCard.equals(new EventCard(event, 1)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, ReadOnlyEvent expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysEvent(expectedEvent, eventCardHandle);
    }
}
```
###### \java\seedu\address\ui\EventListPanelTest.java
``` java
public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyEvent> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private EventListPanelHandle eventListPanelHandle;

    @Before
    public void setUp() {
        EventListPanel eventListPanel = new EventListPanel(TYPICAL_EVENTS);
        uiPartRule.setUiPart(eventListPanel);

        eventListPanelHandle = new EventListPanelHandle(getChildNode(eventListPanel.getRoot(),
                EventListPanelHandle.EVENT_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_EVENTS.size(); i++) {
            eventListPanelHandle.navigateToCard(TYPICAL_EVENTS.get(i));
            ReadOnlyEvent expectedEvent = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getEventCardHandle(i);

            assertCardDisplaysEvent(expectedEvent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysEvent(ReadOnlyEvent expectedEvent, EventCardHandle actualCard) {
        assertEquals(expectedEvent.getHeader().value, actualCard.getHeader());
        assertEquals(expectedEvent.getDesc().value, actualCard.getDesc());
        assertEquals(expectedEvent.getEventDate().value, actualCard.getEventDate());
    }
```
###### \java\systemtests\AddEventCommandSystemTest.java
``` java
public class AddEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add an event Birthday to a non-empty address book, command with leading spaces and trailing spaces
         * -> Birthday event added
         */
        ReadOnlyEvent toAdd = BIRTHDAY;
        String command = "   " + AddEventCommand.COMMAND_WORD + "  " + HEADER_DESC_BIRTHDAY + "  "
                + DESC_DESC_BIRTHDAY + " " + EVENT_DATE_DESC_BIRTHDAY + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Birthday to the list ->  Birthday deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Birthday to the list -> Birthday added again */
        command = RedoCommand.COMMAND_WORD;
        model.addEvent(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate event -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, AddEventCommand.MESSAGE_DUPLICATE_EVENT);


        /* Case: add an event with all fields same as another event in the address book except header -> added */
        toAdd = new EventBuilder().withHeader(VALID_HEADER_MEETING).withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_MEETING + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except desc -> added */
        toAdd = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY).withDesc(VALID_DESC_MEETING)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_MEETING + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except event date -> added */
        toAdd = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY).withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_MEETING).build();
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_MEETING;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getEventList().size() == 0;
        assertCommandSuccess(OUTING);

        /* Case: add an event with parameters in random order -> added */
        toAdd = MEETING;
        command = AddEventCommand.COMMAND_WORD + DESC_DESC_MEETING + HEADER_DESC_MEETING + EVENT_DATE_DESC_MEETING;
        assertCommandSuccess(command, toAdd);

        /* Case: missing header -> rejected */
        command = AddEventCommand.COMMAND_WORD + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing desc -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing event date -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_BIRTHDAY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addEv ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: invalid header -> rejected */
        command = AddEventCommand.COMMAND_WORD + INVALID_HEADER_DESC + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, Header.MESSAGE_HEADER_CONSTRAINTS);

        /* Case: invalid desc -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + INVALID_DESC_DESC + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, Desc.MESSAGE_DESC_CONSTRAINTS);

        /* Case: invalid event date -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_BIRTHDAY + INVALID_EVENT_DATE_DESC;
        assertCommandFailure(command, EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddEventCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing
     * {@code AddEventCommand} with the details of {@code toAdd}, and the model related components equal to
     * the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyEvent toAdd) {
        assertCommandSuccess(EventUtil.getAddEventCommand(toAdd), toAdd);
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
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\DeleteEventCommandSystemTest.java
``` java
public class DeleteEventCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);

    @Test
    public void delete() {

        /* Case: delete the first event in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteEventCommand.COMMAND_WORD + "      " + INDEX_FIRST_EVENT.getOneBased()
                + "       ";
        ReadOnlyEvent deletedEvent = removeEvent(expectedModel, INDEX_FIRST_EVENT);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EVENT_SUCCESS, deletedEvent);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last event in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastEventIndex = getEventLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastEventIndex);

        /* Case: undo deleting the last event in the list -> last event restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last event in the list -> last event deleted */
        command = RedoCommand.COMMAND_WORD;
        removeEvent(modelBeforeDeletingLast, lastEventIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteEventCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteEventCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getEventList().size() + 1);
        command = DeleteEventCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteEventCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteEventCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ReadOnlyEvent} at the specified {@code index} in {@code model}'s address book.
     * @return the removed event
     */
    private ReadOnlyEvent removeEvent(Model model, Index index) {
        ReadOnlyEvent targetEvent = getEvent(model, index);
        try {
            model.deleteEvent(targetEvent);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError("targetEvent is retrieved from model.");
        }
        return targetEvent;
    }

    /**
     * Deletes the event at {@code toDelete} by creating a default {@code DeleteEventCommand} using {@code toDelete}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ReadOnlyEvent deletedEvent = removeEvent(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EVENT_SUCCESS, deletedEvent);

        assertCommandSuccess(
                DeleteEventCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
                expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\EditEventCommandSystemTest.java
``` java
public class EditEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> event edited
         */
        Index index = INDEX_FIRST_EVENT;
        String command = " " + EditEventCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + HEADER_DESC_BIRTHDAY
                + "  " + DESC_DESC_BIRTHDAY + " " + EVENT_DATE_DESC_BIRTHDAY + " ";
        Event editedEvent = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY).withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        assertCommandSuccess(command, index, editedEvent);

        /* Case: undo editing the last event in the list -> last event restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last event in the list -> last event edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateEvent(
                getModel().getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), editedEvent);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_EVENT;
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + DESC_DESC_MEETING;
        ReadOnlyEvent eventToEdit = getModel().getFilteredEventList().get(index.getZeroBased());
        editedEvent = new EventBuilder(eventToEdit).withDesc(VALID_DESC_MEETING).build();
        assertCommandSuccess(command, index, editedEvent);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " 0" + HEADER_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " -1" + HEADER_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + invalidIndex + HEADER_DESC_MEETING,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + HEADER_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased(),
                EditEventCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid header -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + INVALID_HEADER_DESC, Header.MESSAGE_HEADER_CONSTRAINTS);

        /* Case: invalid desc -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + INVALID_DESC_DESC, Desc.MESSAGE_DESC_CONSTRAINTS);

        /* Case: invalid event date -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + INVALID_EVENT_DATE_DESC, EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);

        /* Case: edit an event with new values same as another event's values -> rejected */
        executeCommand(EventUtil.getAddEventCommand(BIRTHDAY));
        assertTrue(getModel().getAddressBook().getEventList().contains(BIRTHDAY));
        index = INDEX_FIRST_EVENT;
        assertFalse(getModel().getFilteredEventList().get(index.getZeroBased()).equals(BIRTHDAY));
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyEvent editedEvent) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateEvent(
                    expectedModel.getFilteredEventList().get(toEdit.getZeroBased()), editedEvent);
            expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        } catch (DuplicateEventException | EventNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedEvent is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
