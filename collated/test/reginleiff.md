# reginleiff
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
    public void equals() {
        Event jackBirthday = new EventBuilder().withTitle("Jack's Birthday").build();
        Event weddingAnniversary = new EventBuilder().withTitle("Wedding Anniversary").build();
        AddEventCommand addBirthdayCommand = new AddEventCommand(jackBirthday);
        AddEventCommand addAnniversaryCommand = new AddEventCommand(weddingAnniversary);

        // same object -> returns true
        assertTrue(addBirthdayCommand.equals(addBirthdayCommand));

        // same values -> returns true
        AddEventCommand addBirthdayCommandCopy = new AddEventCommand(jackBirthday);
        assertTrue(addBirthdayCommand.equals(addBirthdayCommandCopy));

        // different types -> returns false
        assertFalse(addBirthdayCommand.equals(1));

        // null -> returns false
        assertFalse(addBirthdayCommand.equals(null));

        // different event -> returns false
        assertFalse(addBirthdayCommand.equals(addAnniversaryCommand));
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
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson person) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void sortPerson(String type) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyEvent event) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteEvent(ReadOnlyEvent event) {
            fail("This method should not be called.");
        }

        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyEvent> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyEvent> getSchedule() {
            fail("This method should not be called.");
            return null;
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ReadOnlyEvent event) {
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

    private Model model = new ModelManager(getTypicalEventAddressBook(), new UserPrefs());

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
        model.updateFilteredEventList(p -> false);

        assert model.getFilteredEventList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\event\EditEventCommandTest.java
``` java
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalEventAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Event editedEvent = new EventBuilder().withTitle(VALID_TITLE_SOCCER).withTimeslot(VALID_TIMESLOT_MIDTERM)
                .withDescription(VALID_DESCRIPTION_SOCCER).build();
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editCommand = prepareCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        ReadOnlyEvent lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList.withTitle(VALID_TITLE_MIDTERM).withTimeslot(VALID_TIMESLOT_MIDTERM).build();

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withTitle(VALID_TITLE_MIDTERM).withTimeslot(VALID_TIMESLOT_MIDTERM).build();
        EditEventCommand editCommand = prepareCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(lastEvent, editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editCommand = prepareCommand(INDEX_FIRST_EVENT, new EditEventCommand.EditEventDescriptor());
        ReadOnlyEvent editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withTitle(VALID_TITLE_SOCCER)
                .withTimeslot(VALID_TIMESLOT_MIDTERM).build();
        EditEventCommand editCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder().withTitle(VALID_TITLE_SOCCER)
                        .withTimeslot(VALID_TIMESLOT_MIDTERM).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventCommand.EditEventDescriptor descriptor =
                new EditEventDescriptorBuilder().withTitle(VALID_TITLE_MIDTERM).build();
        EditEventCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
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

        EditEventCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withTitle(VALID_TITLE_MIDTERM).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_EVENT, DESC_MIDTERM);

        // same values -> returns true
        EditEventCommand.EditEventDescriptor copyDescriptor = new EditEventCommand.EditEventDescriptor(DESC_MIDTERM);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_EVENT, DESC_MIDTERM)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_EVENT, DESC_SOCCER)));
    }

    /**
     * Returns an {@code EditEventCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEventCommand prepareCommand(Index index, EditEventCommand.EditEventDescriptor descriptor) {
        EditEventCommand editCommand = new EditEventCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

```
###### \java\seedu\address\logic\commands\event\FindEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalEventAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        FindEventCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        FindEventCommand command = prepareCommand("Jack's Final Ah");
        TreeMap<Timeslot, ReadOnlyEvent> map = new TreeMap<>();
        map.put(BIRTHDAY.getTimeslot(), BIRTHDAY);
        map.put(MOURN.getTimeslot(), MOURN);
        map.put(EXAM.getTimeslot(), EXAM);
        List<ReadOnlyEvent> list = new ArrayList<>(map.values());
        assertCommandSuccess(command, expectedMessage, list);
    }

    /**
     * Parses {@code userInput} into a {@code FindEventCommand}.
     */
    private FindEventCommand prepareCommand(String userInput) {
        FindEventCommand command =
                new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindEventCommand command, String expectedMessage,
                                      List<ReadOnlyEvent> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredEventList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\event\AddEventCommandParserTest.java
``` java
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_SOCCER).withTimeslot(VALID_TIMESLOT_SOCCER)
                .withDescription(VALID_DESCRIPTION_SOCCER).build();

        // multiple titles - last title accepted
        String event1 = AddEventCommand.COMMAND_WORD + TITLE_MIDTERM + TITLE_SOCCER + TIMESLOT_SOCCER
                + DESCRIPTION_SOCCER + TIMESLOT_SOCCER;
        assertParseSuccess(parser, event1, new AddEventCommand(expectedEvent));

        // multiple timings - last timing accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + TIMESLOT_MIDTERM + TIMESLOT_SOCCER
                + DESCRIPTION_SOCCER, new AddEventCommand(expectedEvent));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + TIMESLOT_SOCCER + DESCRIPTION_MIDTERM
                + DESCRIPTION_SOCCER, new AddEventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);
        // missing title prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_TITLE_SOCCER + TIMESLOT_SOCCER
                + DESCRIPTION_SOCCER, expectedMessage);
        // missing timing prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + VALID_TIMESLOT_SOCCER
                + VALID_TIMESLOT_MIDTERM, expectedMessage);
        // missing email prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + TIMESLOT_SOCCER
                + VALID_DESCRIPTION_SOCCER, expectedMessage);
        // all prefixes missing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_TITLE_SOCCER + VALID_TIMESLOT_SOCCER
                + VALID_DESCRIPTION_SOCCER, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE + TIMESLOT_SOCCER + DESCRIPTION_SOCCER,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid timing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + INVALID_TIMESLOT + DESCRIPTION_SOCCER,
                Timeslot.MESSAGE_TIMESLOT_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE + INVALID_TIMESLOT + DESCRIPTION_SOCCER,
                Title.MESSAGE_TITLE_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\event\DateParserTest.java
``` java
public class DateParserTest {

    private static final String invalidDay = "9/05/1999";
    private static final String invalidMonth = "09/5/1999";
    private static final String invalidYear = "09/05/199";
    private static final String validDate = "09/05/1999";
    private DateParser parser = new DateParser();

    @Test
    public void parseInvalidDay() throws ParseException {
        boolean thrown = false;

        try {
            parser.parse(invalidDay);
        } catch (ParseException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void parseInvalidMonth() throws ParseException {
        boolean thrown = false;

        try {
            parser.parse(invalidMonth);
        } catch (ParseException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void parseInvalidYear() throws ParseException {
        boolean thrown = false;

        try {
            parser.parse(invalidYear);
        } catch (ParseException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void parseValidDate() throws ParseException {
        boolean thrown = false;

        try {
            parser.parse(validDate);
        } catch (ParseException e) {
            thrown = true;
        }

        assertFalse(thrown);
    }

}
```
###### \java\seedu\address\logic\parser\event\DeleteEventCommandParserTest.java
``` java
public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteEventCommand() {
        assertParseSuccess(parser, "1", new DeleteEventCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\event\EditEventCommandParserTest.java
``` java
public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_MIDTERM, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_MIDTERM, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_MIDTERM, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE, Title.MESSAGE_TITLE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TIMESLOT, Timeslot.MESSAGE_TIMESLOT_CONSTRAINTS); // invalid timing

        // invalid timing followed by valid email
        assertParseFailure(parser, "1" + INVALID_TIMESLOT + DESCRIPTION_SOCCER, Timeslot.MESSAGE_TIMESLOT_CONSTRAINTS);

        // valid title followed by invalid timing. The test case for invalid timing followed by valid timing
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TITLE_MIDTERM + INVALID_TIMESLOT, Timeslot.MESSAGE_TIMESLOT_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE + INVALID_TIMESLOT + VALID_DESCRIPTION_MIDTERM,
                Title.MESSAGE_TITLE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + TIMESLOT_SOCCER
                + DESCRIPTION_MIDTERM + TITLE_MIDTERM;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withTitle(VALID_TITLE_MIDTERM)
                .withTimeslot(VALID_TIMESLOT_SOCCER).withDescription(VALID_DESCRIPTION_MIDTERM).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + TIMESLOT_SOCCER + DESCRIPTION_MIDTERM;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withTimeslot(VALID_TIMESLOT_SOCCER)
                .withDescription(VALID_DESCRIPTION_MIDTERM).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + TITLE_MIDTERM;
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withTitle(VALID_TITLE_MIDTERM).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // timing
        userInput = targetIndex.getOneBased() + TIMESLOT_MIDTERM;
        descriptor = new EditEventDescriptorBuilder().withTimeslot(VALID_TIMESLOT_MIDTERM).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_MIDTERM;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_MIDTERM).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + TIMESLOT_MIDTERM + DESCRIPTION_MIDTERM
                + TIMESLOT_MIDTERM + DESCRIPTION_MIDTERM + TIMESLOT_SOCCER + DESCRIPTION_SOCCER;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withTimeslot(VALID_TIMESLOT_SOCCER)
                .withDescription(VALID_DESCRIPTION_SOCCER).build();

        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_TIMESLOT + TIMESLOT_SOCCER;
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withTimeslot(VALID_TIMESLOT_SOCCER).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DESCRIPTION_SOCCER + INVALID_TIMESLOT + TIMESLOT_SOCCER;
        descriptor = new EditEventDescriptorBuilder().withTimeslot(VALID_TIMESLOT_SOCCER)
                .withDescription(VALID_DESCRIPTION_SOCCER).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\event\FindEventCommandParserTest.java
``` java
package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.model.event.TitleContainsKeywordsPredicate;

/**
 * @@reginleiff
 */
public class FindEventCommandParserTest {
    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindEventCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedFindEventCommand =
                new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList("CS2103", "Midterm")));
        assertParseSuccess(parser, "CS2103 Midterm", expectedFindEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2103 \n \t Midterm  \t", expectedFindEventCommand);
    }
}
```
###### \java\seedu\address\model\event\DescriptionTest.java
``` java
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescriptionTest() {
        // invalid title
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid title
        assertTrue(Description.isValidDescription("srgsrgdfh")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("sasdgsdg898 s9898")); // alphanumeric characters
        assertTrue(Description.isValidDescription("SGSDF839 SD928 92")); // with capital letters
        // long descriptions with symbols
        assertTrue(
                Description.isValidDescription("SDFIUSDFHIHI9839:2983983 2HIUDF938UN OJOFEIJ02JSLDLJDO90JS JOASDJ9"));
    }
}
```
###### \java\seedu\address\model\event\timeslot\DateTest.java
``` java
package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateTest {
    @Test
    public void gregorianDatesTest() throws IllegalValueException {
        assertFalse(isGregorianDate("29/02/2017"));
        assertFalse(isGregorianDate("00/02/2017"));
        assertFalse(isGregorianDate("07/00/2017"));
        assertTrue(isGregorianDate("23/10/2017"));
        assertTrue(isGregorianDate("29/02/2020"));
    }

    /**
     * Returns true if input date argument is a valid date in gregorian calendar.
     */
    public boolean isGregorianDate(String date) {
        try {
            new Date(date);
        } catch (IllegalValueException e) {
            return false;
        }
        return true;
    }


    @Test
    public void compareTo() throws Exception {
        Date one = new Date("22/10/2017");
        Date two = new Date("21/11/2017");
        Date three = new Date("22/12/2018");

        assertTrue(one.compareTo(two) < 0);
        assertTrue(two.compareTo(three) < 0);
        assertTrue(three.compareTo(one) > 0);
        assertTrue(one.compareTo(one) == 0);
    }

}
```
###### \java\seedu\address\model\event\timeslot\TimeslotTest.java
``` java
package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class TimeslotTest {
    private String validTimeOne = "22/10/2017 1053-1055";
    private String validTimeTwo = "22/10/2017 1032-1055";
    private String validTimeThree = "21/09/2017 1053-1055";
    private String validTimeFour = "22/10/2017 1053-2100";

    private String invalidDay = "2/10/2017 1053-1055";
    private String invalidMonth = "02/9/2017 1053-1055";
    private String invalidYear = "02/9/123 1053-1055";
    private String invalidTime = "02/10/2017 935-10";

    @Test
    public void compareTo() throws Exception {
        try {
            Timeslot one = new Timeslot(validTimeOne);
            Timeslot two = new Timeslot(validTimeTwo);
            Timeslot three = new Timeslot(validTimeThree);
            Timeslot four = new Timeslot(validTimeFour);

            assertTrue(one.compareTo(two) > 0);
            assertTrue(two.compareTo(three) > 0);
            assertTrue(three.compareTo(four) < 0);
            assertTrue(one.compareTo(four) == 0);
        } catch (IllegalValueException e) {
            // Should not happen
            throw e;
        }

    }

    @Test
    public void isValidTiming() throws Exception {
        // Valid timing
        assertTrue(Timeslot.isValidTiming("22/10/2017 1053-1055"));
        assertTrue(Timeslot.isValidTiming("22/10/2017 0000-0000"));
        assertTrue(Timeslot.isValidTiming("22/10/2017 2359-2359"));

        // Invalid day format
        assertFalse(Timeslot.isValidTiming(invalidDay));

        // Invalid month format
        assertFalse(Timeslot.isValidTiming(invalidMonth));

        // Invalid year format
        assertFalse(Timeslot.isValidTiming(invalidYear));

        //Invalid time format
        assertFalse(Timeslot.isValidTiming(invalidTime));
    }

}
```
###### \java\seedu\address\model\event\timeslot\TimingTest.java
``` java
package seedu.address.model.event.timeslot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimingTest {
    @Test
    public void compareTo() throws Exception {
        Timing timeOne = new Timing("0000-1300");
        Timing timeTwo = new Timing("1300-1400");
        Timing timeThree = new Timing("1300-1500");
        Timing timeFour = new Timing("1900-2100");

        assertTrue(timeOne.compareTo(timeTwo) < 0);
        assertTrue(timeTwo.compareTo(timeThree) == 0);
        assertTrue(timeThree.compareTo(timeFour) < 0);
        assertTrue(timeFour.compareTo(timeOne) > 0);

        assertFalse(Timing.isValidTiming(""));
        assertFalse(Timing.isValidTiming(" "));
        assertFalse(Timing.isValidTiming("1"));
        assertFalse(Timing.isValidTiming("1100"));
        assertFalse(Timing.isValidTiming("1-1"));
        assertFalse(Timing.isValidTiming("12-12"));
        assertFalse(Timing.isValidTiming("123-123"));
        assertFalse(Timing.isValidTiming("12345-12345"));
        assertFalse(Timing.isValidTiming("2500-2600")); // cannot accept non-24 hour times
        assertFalse(Timing.isValidTiming("1200-2400")); // 2400 hrs does not exist

        assertTrue(Timing.isValidTiming("0000-2359"));
        assertTrue(Timing.isValidTiming("1900-2100"));
    }
}

```
###### \java\seedu\address\model\event\TitleTest.java
``` java
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TitleTest {

    @Test
    public void isValidTitleTest() {
        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only

        // valid title
        assertTrue(Title.isValidTitle("peter's birthday")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("nus 50th anniversary")); // alphanumeric characters
        assertTrue(Title.isValidTitle("CS2103 MIDTERM")); // with capital letters
        // long names with symbols
        assertTrue(Title.isValidTitle("National University of Singapore: 2020 Homecoming Graduation Ceremony"));
    }
}
```
###### \java\seedu\address\model\EventListTest.java
``` java
    @Test
    public void getSubList_success() {
        EventList eventList = new EventList();
        EventList expectedSublist = new EventList();
        try {
            Date testDate = new Date("10/12/2017");

            eventList.add(BIRTHDAY);
            eventList.add(ANNIVERSARY);
            eventList.add(EXAM);
            eventList.add(MOURN);
            eventList.add(DEADLINE);

            expectedSublist.add(MOURN);
            expectedSublist.add(DEADLINE);

            ObservableList<ReadOnlyEvent> actualSublist = eventList.getObservableSubList(testDate);
            assertEquals(actualSublist, expectedSublist.asObservableList());
        } catch (Exception e) {
            return;
        }
    }
```
###### \java\seedu\address\testutil\EditEventDescriptorBuilder.java
``` java
package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventCommand.EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventCommand.EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventCommand.EditEventDescriptor descriptor) {
        this.descriptor = new EditEventCommand.EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(ReadOnlyEvent event) {
        descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setTitle(event.getTitle());
        descriptor.setTimeslot(event.getTimeslot());
        descriptor.setDescription(event.getDescription());
    }

    /**
     * Sets the {@code Title} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTitle(String title) {
        try {
            ParserUtil.parseTitle(Optional.of(title)).ifPresent(descriptor::setTitle);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("title is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Timeslot} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTimeslot(String timeslot) {
        try {
            ParserUtil.parseTimeslot(Optional.of(timeslot)).ifPresent(descriptor::setTimeslot);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Timeslot is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        try {
            ParserUtil.parseDescription(Optional.of(description)).ifPresent(descriptor::setDescription);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Description is expected to be unique.");
        }
        return this;
    }

    public EditEventCommand.EditEventDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\EventBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.Title;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "Jack's Birthday";
    public static final String DEFAULT_TIMESLOT = "23/10/2017 1900-2100";
    public static final String DEFAULT_DESCRIPTION = "Celebrating Jack's 21st, party all night";

    private Event event;

    public EventBuilder() {
        try {
            Title defaultTitle = new Title(DEFAULT_TITLE);
            Timeslot defaultTimeslot = new Timeslot(DEFAULT_TIMESLOT);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            this.event = new Event(defaultTitle, defaultTimeslot, defaultDescription);
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
     * Sets the {@code Title} of the {@code Event} that we are building.
     */
    public EventBuilder withTitle(String title) {
        try {
            this.event.setTitle(new Title(title));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("title is expected to be unique.");
        }
        return this;
    }


    /**
     * Sets the {@code Timeslot} of the {@code Event} that we are building.
     */
    public EventBuilder withTimeslot(String timeslot) {
        try {
            this.event.setTimeslot(new Timeslot(timeslot));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Timeslot is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        try {
            this.event.setDescription(new Description(description));
        } catch (IllegalValueException ive) {

            throw new IllegalArgumentException("Description is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
```
###### \java\seedu\address\testutil\EventsUtil.java
``` java
package seedu.address.testutil;

import guitests.GuiRobot;
import javafx.application.Platform;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Helper methods related to events.
 */
public class EventsUtil {
    /**
     * Posts {@code event} to all registered subscribers. This method will return successfully after the {@code event}
     * has been posted to all subscribers.
     */
    public static void postNow(BaseEvent event) {
        new GuiRobot().interact(() -> EventsCenter.getInstance().post(event));
    }

    /**
     * Posts {@code event} to all registered subscribers at some unspecified time in the future.
     */
    public static void postLater(BaseEvent event) {
        Platform.runLater(() -> EventsCenter.getInstance().post(event));
    }
}
```
###### \java\seedu\address\testutil\TypicalEvents.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventTimeClashException;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent BIRTHDAY = new EventBuilder().withTitle("Jack's Birthday")
            .withTimeslot("23/10/2017 1900-2300").withDescription("Celebrating Jack's 21st").build();
    public static final ReadOnlyEvent ANNIVERSARY = new EventBuilder().withTitle("Wedding Anniversary")
            .withTimeslot("29/12/2018 0000-2359").withDescription("2nd Wedding Anniversary").build();
    public static final ReadOnlyEvent EXAM = new EventBuilder().withTitle("CS2103 Final Exam")
            .withTimeslot("09/12/2017 1300-1500").withDescription("We are screwed").build();
    public static final ReadOnlyEvent MOURN = new EventBuilder().withTitle("Bai Ah Gong")
            .withTimeslot("10/12/2017 1900-2300").withDescription("@ CCK Cemetery").build();
    public static final ReadOnlyEvent DEADLINE = new EventBuilder().withTitle("Paper Submission")
            .withTimeslot("10/12/2017 2359-2359").withDescription("Submit on IVLE").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalEventAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyEvent event : getTypicalEvents()) {
            try {
                ab.addEvent(event);
            } catch (EventTimeClashException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(BIRTHDAY, ANNIVERSARY, MOURN, EXAM));
    }
}
```
