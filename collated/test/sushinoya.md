# sushinoya
###### /java/seedu/room/logic/parser/AddCommandParserTest.java
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
###### /java/seedu/room/logic/parser/SwaproomCommandParserTest.java
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
###### /java/seedu/room/logic/commands/SwaproomCommandTest.java
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
###### /java/seedu/room/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sortBy(String sortCriteria) throws AlreadySortedException {
            fail("This method should not be called.");
        }

```
###### /java/seedu/room/logic/commands/AddCommandTest.java
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
###### /java/seedu/room/storage/StorageManagerTest.java
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


}
```
###### /java/seedu/room/model/person/RoomTest.java
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
