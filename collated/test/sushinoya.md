# sushinoya
###### \java\seedu\room\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortBy(String sortCriteria) throws AlreadySortedException {
            fail("This method should not be called.");
        }

```
###### \java\seedu\room\logic\commands\AddCommandTest.java
``` java
        public void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyEvent person) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException,
                                                                                            EventNotFoundException {
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
        public void sortEventsBy(String sortCriteria) throws AlreadySortedException {
            fail("This method should not be called.");
        }
    }

```
###### \java\seedu\room\logic\commands\AddEventCommandTest.java
``` java
        @Override
        public void sortBy(String sortCriteria) throws AlreadySortedException {
            fail("This method should not be called.");
        }

```
###### \java\seedu\room\logic\commands\AddEventCommandTest.java
``` java
        public void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyEvent person) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException,
                EventNotFoundException {
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
        public void sortEventsBy(String sortCriteria) throws AlreadySortedException {
            fail("This method should not be called.");
        }
    }

```
###### \java\seedu\room\logic\commands\CommandTestUtil.java
``` java
    /**
     * Updates {@code model}'s filtered list to show only the first event in the {@code model}'s event book.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getEventBook().getEventList().get(0);
        final String[] splitTitle = event.getTitle().value.split("\\s+");
        model.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(splitTitle[0])));

        assert model.getFilteredEventList().size() == 1;
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s event book.
     */
    public static void deleteFirstEvent(Model model) {
        ReadOnlyEvent firstEvent = model.getFilteredEventList().get(0);
        try {
            model.deleteEvent(firstEvent);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError("Event in filtered list must exist in model.", enfe);
        }
    }
}
```
###### \java\seedu\room\logic\commands\DeleteEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), getTypicalEventBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), model.getEventBook(), new UserPrefs());
        ReadOnlyEvent expectedEventToDelete = expectedModel.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.deleteEvent(expectedEventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = prepareCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        Model expectedModel = new ModelManager(model.getResidentBook(), model.getEventBook(), new UserPrefs());
        ReadOnlyEvent expectedEventToDelete = expectedModel.getFilteredEventList()
                .get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.deleteEvent(expectedEventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstEventOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of event book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventBook().getEventList().size());

        DeleteEventCommand deleteEventCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteSecondEventCommand = new DeleteEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstEventCommandCopy = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstEventCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstEventCommand.equals(deleteSecondEventCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
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
###### \java\seedu\room\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommandByName;
    private SortCommand sortCommandByPhone;
    private SortCommand sortCommandByInvalidField;
    private String sortCriteriaDefault;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalResidentBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        sortCriteriaDefault = "name";

        sortCommandByName = new SortCommand(sortCriteriaDefault);
        sortCommandByName.setData(model, new CommandHistory(), new UndoRedoStack());

        sortCommandByPhone = new SortCommand("phone");
        sortCommandByPhone.setData(model, new CommandHistory(), new UndoRedoStack());

        sortCommandByInvalidField = new SortCommand("tag");
        sortCommandByInvalidField.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortByName_onAlreadySortedByName() {
        String failureMessage = String.format(SortCommand.MESSAGE_FAILURE, sortCriteriaDefault);
        assertCommandFailure(sortCommandByName, model, failureMessage);
    }

    @Test
    public void execute_sortByPhone_onAlreadySortedByName() {
        String successMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone");
        try {
            expectedModel.sortBy("phone");
        } catch (AlreadySortedException e) {
            fail("This should never be called");
        }
        assertCommandSuccess(sortCommandByPhone, model, successMessage, expectedModel);
    }

    @Test
    public void execute_sortByInvalidField() {
        String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertCommandFailure(sortCommandByInvalidField, model, failureMessage);
    }

}
```
###### \java\seedu\room\logic\commands\SwaproomCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SwaproomCommand}.
 */
public class SwaproomCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson person1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson person2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        SwaproomCommand swapCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(SwaproomCommand.MESSAGE_SWAP_PERSONS_SUCCESS, person1.getName(),
                person2.getName());

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.swapRooms(person1, person2);

        assertCommandSuccess(swapCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex1 = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredPersonList().size() + 2);
        SwaproomCommand swapCommand = prepareCommand(outOfBoundIndex1, outOfBoundIndex2);

        assertCommandFailure(swapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex1 = INDEX_SECOND_PERSON;
        Index outOfBoundIndex2 = INDEX_THIRD_PERSON;

        // ensures that outOfBoundIndex is still in bounds of resident book list
        assertTrue(outOfBoundIndex1.getZeroBased() < model.getResidentBook().getPersonList().size());
        assertTrue(outOfBoundIndex2.getZeroBased() < model.getResidentBook().getPersonList().size());

        SwaproomCommand swapCommand = prepareCommand(outOfBoundIndex1, outOfBoundIndex2);

        assertCommandFailure(swapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SwaproomCommand swapFirstCommand = new SwaproomCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        SwaproomCommand swapSecondCommand = new SwaproomCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON);
        SwaproomCommand swapThirdCommand = new SwaproomCommand(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        // same object -> returns true
        assertTrue(swapFirstCommand.equals(swapFirstCommand));

        // same values -> returns true
        SwaproomCommand swapFirstCommandCopy = new SwaproomCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(swapFirstCommand.equals(swapFirstCommandCopy));

        // same values with swapped arguments -> returns true
        assertTrue(swapFirstCommand.equals(swapSecondCommand));

        // different types -> returns false
        assertFalse(swapFirstCommand.equals(1));

        // null -> returns false
        assertFalse(swapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(swapFirstCommand.equals(swapThirdCommand));
    }

    /**
     * Returns a {@code SwaproomCommand} with the parameter {@code index}.
     */
    private SwaproomCommand prepareCommand(Index index1, Index index2) {
        SwaproomCommand swapCommand = new SwaproomCommand(index1, index2);
        swapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return swapCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### \java\seedu\room\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ROOM_DESC_AMY, new AddCommand(expectedPerson));

        // without phone
        Person expectedPerson2 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(DEFAULT_NOT_SET)
                .withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DEFAULT_UNSET
                        + EMAIL_DESC_AMY + ROOM_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson2));

        // without email
        Person expectedPerson3 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(DEFAULT_NOT_SET).withRoom(VALID_ROOM_AMY).withTags(VALID_TAG_HUSBAND,
                VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DEFAULT_UNSET + ROOM_DESC_AMY + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson3));

        // without room
        Person expectedPerson4 = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withRoom(DEFAULT_NOT_SET)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ROOM_DEFAULT_UNSET + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson4));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ROOM_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ROOM_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                        + ROOM_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid room
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ROOM_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Room.MESSAGE_ROOM_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ROOM_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ROOM_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
```
###### \java\seedu\room\logic\parser\AddEventCommandParserTest.java
``` java
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_ORIENTATION)
                .withDescription(VALID_DESCRIPTION_ORIENTATION)
                .withLocation(VALID_LOCATION_ORIENTATION).withDatetime(VALID_DATETIME_ORIENTATION).build();

        // multiple titles - last title accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_POLYMATH
                + TITLE_DESC_ORIENTATION + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // [alias] multiple titles - last title accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_POLYMATH
                + TITLE_DESC_ORIENTATION + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // multiple description - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_POLYMATH + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // [alias] multiple description - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_POLYMATH + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // multiple locations - last location accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_POLYMATH
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // [alias] multiple locations - last location accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_POLYMATH
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // multiple datetime - last datetime accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_ORIENTATION
                + DATETIME_DESC_POLYMATH + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // [alias] multiple datetime - last datetime accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_ORIENTATION
                + DATETIME_DESC_POLYMATH + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, expectedMessage);

        // missing location prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, expectedMessage);

        // missing datetime prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_ORIENTATION , expectedMessage);

        // missing description prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE_DESC + LOCATION_DESC_POLYMATH
                + DESCRIPTION_DESC_POLYMATH + DATETIME_DESC_POLYMATH, Title.MESSAGE_TITLE_CONSTRAINTS);

        // [alias] invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + INVALID_TITLE_DESC + LOCATION_DESC_POLYMATH
                + DESCRIPTION_DESC_POLYMATH + DATETIME_DESC_POLYMATH, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_POLYMATH + INVALID_LOCATION_DESC
                + DESCRIPTION_DESC_POLYMATH + DATETIME_DESC_POLYMATH, Location.MESSAGE_LOCATION_CONSTRAINTS);

        // [alias] invalid location
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_POLYMATH + INVALID_LOCATION_DESC
                + DESCRIPTION_DESC_POLYMATH + DATETIME_DESC_POLYMATH, Location.MESSAGE_LOCATION_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_POLYMATH + LOCATION_DESC_POLYMATH
                + INVALID_DESCRIPTION_DESC + DATETIME_DESC_POLYMATH, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // [alias] invalid description
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_POLYMATH
                + LOCATION_DESC_POLYMATH + INVALID_DESCRIPTION_DESC
                + DATETIME_DESC_POLYMATH, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid datetime
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_POLYMATH + LOCATION_DESC_POLYMATH
                        + DESCRIPTION_DESC_POLYMATH + INVALID_DATETIME_DESC, Datetime.DATE_CONSTRAINTS_VIOLATION);

        // [alias] invalid datetime
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_POLYMATH
                        + LOCATION_DESC_POLYMATH + DESCRIPTION_DESC_POLYMATH + INVALID_DATETIME_DESC,
                Datetime.DATE_CONSTRAINTS_VIOLATION);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE_DESC + LOCATION_DESC_POLYMATH
                + DESCRIPTION_DESC_POLYMATH + INVALID_DATETIME_DESC, Title.MESSAGE_TITLE_CONSTRAINTS);

        // [alias] two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + INVALID_TITLE_DESC
                + LOCATION_DESC_POLYMATH + DESCRIPTION_DESC_POLYMATH + INVALID_DATETIME_DESC,
                Title.MESSAGE_TITLE_CONSTRAINTS);
    }
}
```
###### \java\seedu\room\logic\parser\DeleteEventCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteEventCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
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
###### \java\seedu\room\logic\parser\ResidentBookParserTest.java
``` java
    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddEventCommand(event));
        assertEquals(new AddEventCommand(event), command);
    }
```
###### \java\seedu\room\logic\parser\ResidentBookParserTest.java
``` java
    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\room\logic\parser\SortCommandParserTest.java
``` java

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgsPhone_returnsSwapCommand() {
        assertParseSuccess(parser, " phone", new SortCommand("phone"));
    }

    @Test
    public void parse_validArgsName_returnsSwapCommand() {
        assertParseSuccess(parser, " name", new SortCommand("name"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " tag", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNumArgs_throwsParseException() {
        assertParseFailure(parser, " name phone", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\room\logic\parser\SwaproomCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class SwaproomCommandParserTest {

    private SwaproomCommandParser parser = new SwaproomCommandParser();

    @Test
    public void parse_validArgs_returnsSwapCommand() {
        assertParseSuccess(parser, " 1 2", new SwaproomCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwaproomCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNumArgs_throwsParseException() {
        assertParseFailure(parser, " 1 3 4", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SwaproomCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\room\model\EventBookTest.java
``` java
public class EventBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventBook eventBook = new EventBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventBook.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEventBook_replacesData() {
        EventBook newData = getTypicalEventBook();
        eventBook.resetData(newData);
        assertEquals(newData, eventBook);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventBook.getEventList().remove(0);
    }

    /**
     * A stub ReadOnlyEventBook whose eventlists can violate interface constraints.
     */
    private static class EventBookStub implements ReadOnlyEventBook {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        EventBookStub(Collection<? extends ReadOnlyEvent> events) {

            this.events.setAll(events);
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }
    }
}
```
###### \java\seedu\room\model\person\RoomTest.java
``` java
public class RoomTest {

    @Test
    public void isValidRoom() {
        // invalid rooms
        assertFalse(Room.isValidRoom("")); // empty string
        assertFalse(Room.isValidRoom(" ")); // spaces only
        assertFalse(Room.isValidRoom("-")); // one character
        assertFalse(Room.isValidRoom("123-1234")); // long room

        // valid rooms
        assertTrue(Room.isValidRoom("09-100"));

        // default empty room
        assertTrue(Room.isValidRoom("Not Set"));
    }
}
```
###### \java\seedu\room\storage\StorageManagerTest.java
``` java

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlEventBookStorageExceptionThrowingStub extends XmlEventBookStorage {

        public XmlEventBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveEventBook(ReadOnlyEventBook residentBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

```
###### \java\seedu\room\storage\XmlEventBookStorageTest.java
``` java
public class XmlEventBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil
            .getPath("./src/test/data/XmlEventBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEventBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEventBook(null);
    }

    private java.util.Optional<ReadOnlyEventBook> readEventBook(String filePath) throws Exception {
        return new XmlEventBookStorage(filePath).readEventBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEventBook("NotXmlFormatEventBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveEventBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempEventBook.xml";
        EventBook original = getTypicalEventBook();
        XmlEventBookStorage xmlEventBookStorage = new XmlEventBookStorage(filePath);

        //Save in new file and read back
        xmlEventBookStorage.saveEventBook(original, filePath);
        ReadOnlyEventBook readBack = xmlEventBookStorage.readEventBook(filePath).get();
        assertEquals(original.toString(), new EventBook(readBack).toString());

        //Modify data, overwrite exiting file, and read back
        original.addEvent(new Event(DANCE));
        ReadOnlyEvent eventToRemoved = original.getEventList().get(0);
        original.removeEvent(eventToRemoved);
        xmlEventBookStorage.saveEventBook(original, filePath);
        readBack = xmlEventBookStorage.readEventBook(filePath).get();
        assertEquals(original.toString(), new EventBook(readBack).toString());

        //Save and read without specifying file path
        original.addEvent(new Event(IFGTRAINING));
        xmlEventBookStorage.saveEventBook(original); //file path not specified
        readBack = xmlEventBookStorage.readEventBook().get(); //file path not specified
        assertEquals(original.toString(), new EventBook(readBack).toString());

    }

    @Test
    public void saveEventBook_nullResidentBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventBook(null, "SomeFile.xml");
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableEventBook eventBook = new XmlSerializableEventBook();
        thrown.expect(UnsupportedOperationException.class);
        eventBook.getEventList().remove(0);
    }

    /**
     * Saves {@code eventBook} at the specified {@code filePath}.
     */
    private void saveEventBook(ReadOnlyEventBook eventBook, String filePath) {
        try {
            new XmlEventBookStorage(filePath).saveEventBook(eventBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEventBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveEventBook(new EventBook(), null);
    }
}
```
###### \java\seedu\room\testutil\EventBookBuilder.java
``` java
/**
 * A utility class to help with building Eventbook objects.
 * Example usage: <br>
 * {@code EventBook ab = new EventBookBuilder().withEvent(USPolymath).build();}
 */
public class EventBookBuilder {

    private EventBook eventBook;

    public EventBookBuilder() {
        eventBook = new EventBook();
    }

    public EventBookBuilder(EventBook eventBook) {
        this.eventBook = eventBook;
    }

    /**
     * Adds a new {@code Event} to the {@code EventBook} that we are building.
     */
    public EventBookBuilder withEvent(ReadOnlyEvent event) {
        try {
            eventBook.addEvent(event);
        } catch (DuplicateEventException dpe) {
            throw new IllegalArgumentException("event is expected to be unique.");
        }
        return this;
    }


    public EventBook build() {
        return eventBook;
    }
}
```
###### \java\seedu\room\testutil\EventBuilder.java
``` java
/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "USPolymath Session";
    public static final String DEFAULT_LOCATION = "Cinnamon College";
    public static final String DEFAULT_DESCRIPTION = "Conducted by USC";
    public static final String DEFAULT_DATETIME = "05/11/2017 0830 to 2030";

    private Event event;

    public EventBuilder() {
        try {
            Title defaultTitle = new Title(DEFAULT_TITLE);
            Location defaultLocation = new Location(DEFAULT_LOCATION);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            Datetime defaultDatetime = new Datetime(DEFAULT_DATETIME);
            this.event = new Event(defaultTitle, defaultDescription, defaultLocation, defaultDatetime);
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
    public EventBuilder withTitle(String name) {
        try {
            this.event.setTitle(new Title(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }


    /**
     * Sets the {@code Datetime} of the {@code Event} that we are building.
     */
    public EventBuilder withDatetime(String room) {
        try {
            this.event.setDatetime(new Datetime(room));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("room is expected to be unique.");
        }
        return this;
    }


    /**
     * Sets the {@code Location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String phone) {
        try {
            this.event.setLocation(new Location(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }


    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String email) {
        try {
            this.event.setDescription(new Description(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
```
###### \java\seedu\room\testutil\EventUtil.java
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
        sb.append(PREFIX_TITLE + event.getTitle().value + " ");
        sb.append(PREFIX_LOCATION + event.getLocation().value + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().value + " ");
        sb.append(PREFIX_DATETIME + event.getDatetime().value + " ");

        return sb.toString();
    }

    public static String getEventDetailsForEdit(ReadOnlyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + event.getTitle().value + " ");
        sb.append(PREFIX_LOCATION + event.getLocation().value + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().value + " ");
        sb.append(PREFIX_DATETIME + event.getDatetime().value + " ");

        return sb.toString();
    }
}
```
###### \java\seedu\room\testutil\TypicalEvents.java
``` java
/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent ORIENTATION = new EventBuilder().withTitle("Orientation")
            .withDescription("Freshmen Incoming").withLocation("Cinnamon College")
            .withDatetime("21/10/2017 2000 to 2200").build();
    public static final ReadOnlyEvent USPOLYMATH = new EventBuilder().withTitle("USPolymath")
            .withDescription("Intellectual Talks").withLocation("Chatterbox")
            .withDatetime("12/12/2017 2000 to 2200").build();
    public static final ReadOnlyEvent IFGTRAINING = new EventBuilder().withTitle("IFG Training")
            .withDescription("Volleyball Training").withLocation("Sports Hall")
            .withDatetime("29/11/2017 1700 to 1800").build();
    public static final ReadOnlyEvent CONCERT = new EventBuilder().withTitle("Livecore Concert")
            .withDescription("Performance").withLocation("Dining Hall")
            .withDatetime("24/09/2017 1800 to 2100").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final ReadOnlyEvent DANCE = new EventBuilder().withTitle("Livecore Dance")
            .withDescription("Performance").withLocation("Dining Hall")
            .withDatetime("24/09/2017 1800 to 2100").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code EventBook} with all the typical events.
     */
    public static EventBook getTypicalEventBook() {
        EventBook ab = new EventBook();
        for (ReadOnlyEvent event : getTypicalEvents()) {
            try {
                ab.addEvent(event);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(USPOLYMATH, ORIENTATION, IFGTRAINING, CONCERT));
    }
}
```
