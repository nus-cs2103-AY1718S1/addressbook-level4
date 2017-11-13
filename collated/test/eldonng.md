# eldonng
###### \java\seedu\address\logic\commands\CreateGroupCommandTest.java
``` java
public class CreateGroupCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Storage storage = new TypicalStorage().setUp();

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();
        Group validGroup = new GroupBuilder().build();

        CommandResult commandResult = getAddCommandForGroup(validGroup, modelStub).execute();

        assertEquals(String.format(CreateGroupCommand.MESSAGE_SUCCESS, validGroup.getGroupName().fullName,
                validGroup.getGroupMembers().size()), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGroup), modelStub.groupsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateGroupException();
        Group validGroup = new GroupBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_GROUP);

        getAddCommandForGroup(validGroup, modelStub).execute();
    }
    @Test
    public void equals() throws IllegalValueException {

        GroupName groupName1 = new GroupName("Group 1");
        GroupName groupName2 = new GroupName("Group 2");
        List<Index> groupList = new ArrayList<>();
        CreateGroupCommand addGroup1Command = new CreateGroupCommand(groupName1, groupList);
        CreateGroupCommand addGroup2Command = new CreateGroupCommand(groupName2, groupList);

        // same object -> returns true
        assertTrue(addGroup1Command.equals(addGroup1Command));

        // same values -> returns true
        CreateGroupCommand addGroup1CommandCopy = new CreateGroupCommand(groupName1, groupList);
        assertTrue(addGroup1Command.equals(addGroup1CommandCopy));

        // different types -> returns false
        assertFalse(addGroup1Command.equals(1));

        // null -> returns false
        assertFalse(addGroup1Command.equals(null));

        // different person -> returns false
        assertFalse(addGroup1Command.equals(addGroup2Command));
    }

    /**
     * Generates a new CreateGroupCommand with the details of the given group.
     */
    private CreateGroupCommand getAddCommandForGroup(Group group, Model model) {
        CreateGroupCommand command = new CreateGroupCommand(group.getGroupName(),
                        new ArrayList<>(Arrays.asList(Index.fromOneBased(1))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateGroupException when trying to add a group.
     */
    private class ModelStubThrowingDuplicateGroupException extends ModelStub {
        @Override
        public void addGroup(ReadOnlyGroup group) throws DuplicateGroupException {
            throw new DuplicateGroupException();
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return FXCollections.observableList(new ArrayList<>(Arrays.asList(ALICE)));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the group being added.
     */
    private class ModelStubAcceptingGroupAdded extends ModelStub {
        final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public void addGroup(ReadOnlyGroup group) throws DuplicateGroupException {
            groupsAdded.add(new Group(group));
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return FXCollections.observableList(new ArrayList<>(Arrays.asList(ALICE)));
        }

        @Override
        public void updateFilteredGroupList(Predicate<ReadOnlyGroup> predicate) {
            assert true;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}


```
###### \java\seedu\address\logic\commands\DeleteGroupCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstGroupOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.storage.Storage;
import seedu.address.testutil.TypicalStorage;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Storage storage = new TypicalStorage().setUp();

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyGroup groupToDelete = model.getGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = prepareCommand(INDEX_FIRST_GROUP);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS,
                groupToDelete.getGroupName().fullName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getGroupList().size() + 1);
        DeleteGroupCommand deleteGroupCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteGroupCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstGroupOnly(model);

        ReadOnlyGroup groupToDelete = model.getGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = prepareCommand(INDEX_FIRST_GROUP);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS,
                groupToDelete.getGroupName().fullName);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstGroupOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_GROUP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getGroupList().size());

        DeleteGroupCommand deleteGroupCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteGroupCommand, model, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteGroupCommand deleteFirstGroupCommand = new DeleteGroupCommand(INDEX_FIRST_GROUP);
        DeleteGroupCommand deleteSecondGroupCommand = new DeleteGroupCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertTrue(deleteFirstGroupCommand.equals(deleteFirstGroupCommand));

        // same values -> returns true
        DeleteGroupCommand deleteFirstCommandCopy = new DeleteGroupCommand(INDEX_FIRST_GROUP);
        assertTrue(deleteFirstGroupCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstGroupCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstGroupCommand.equals(null));

        // different group -> returns false
        assertFalse(deleteFirstGroupCommand.equals(deleteSecondGroupCommand));
    }

    /**
     * Returns a {@code DeleteGroupCommand} with the parameter {@code index}.
     */
    private DeleteGroupCommand prepareCommand(Index index) {
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(index);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        return deleteGroupCommand;
    }

}
```
###### \java\seedu\address\logic\commands\PinCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code PinCommand and UnpinCommand}.
 */
public class PinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Storage storage = new TypicalStorage().setUp();

    @Test
    public void executeValidIndexUnfilteredListSuccess() throws Exception {
        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PinCommand pinCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);

        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);
        personToPin = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.unpinPerson(personToPin);
        expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS, personToPin);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PinCommand pinCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        UnpinCommand unpinCommand = prepareUnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeValidIndexFilteredListSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.unpinPerson(personToPin);
        PinCommand pinCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS, personToPin);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.pinPerson(personToPin);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);

        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);
        personToPin = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.unpinPerson(personToPin);
        expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS, personToPin);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void executeAlreadyPinnedUnpinned() throws Exception {
        showFirstPersonOnly(model);
        ReadOnlyPerson personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.pinPerson(personToPin);
        PinCommand pinCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = PinCommand.MESSAGE_ALREADY_PINNED;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.pinPerson(personToPin);
        expectedModel.pinPerson(personToPin);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);

        UnpinCommand unpinCommand = prepareUnpinCommand(INDEX_FIRST_PERSON);
        personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.unpinPerson(personToPin);

        expectedMessage = UnpinCommand.MESSAGE_ALREADY_UNPINNED;

        personToPin = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.unpinPerson(personToPin);
        expectedModel.unpinPerson(personToPin);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PinCommand pinCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        UnpinCommand unpinCommand = prepareUnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_PERSON);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_PERSON);
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(pinFirstCommand.equals(pinFirstCommand));
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        PinCommand pinFirstCommandCopy = new PinCommand(INDEX_FIRST_PERSON);
        assertTrue(pinFirstCommand.equals(pinFirstCommandCopy));
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_PERSON);
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));


        // different types -> returns false
        assertFalse(pinFirstCommand.equals(1));
        assertFalse(unpinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(pinFirstCommand == null);
        assertFalse(unpinFirstCommand == null);

        // different person -> returns false
        assertFalse(pinFirstCommand.equals(pinSecondCommand));
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    /**
     * Returns a {@code PinCommand} with the parameter {@code index}.
     */
    private PinCommand prepareCommand(Index index) {
        PinCommand pinCommand = new PinCommand(index);
        pinCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        return pinCommand;
    }

    private UnpinCommand prepareUnpinCommand(Index index) {
        UnpinCommand unpinCommand = new UnpinCommand(index);
        unpinCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        return unpinCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SelectGroupCommandTest.java
``` java
public class SelectGroupCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private Storage storage;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        storage = new TypicalStorage().setUp();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getGroupList().size());

        assertExecutionSuccess(INDEX_FIRST_GROUP);
        assertExecutionSuccess(INDEX_THIRD_GROUP);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getGroupList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstGroupOnly(model);

        assertExecutionSuccess(INDEX_FIRST_GROUP);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstGroupOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_GROUP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getGroupList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectGroupCommand selectFirstCommand = new SelectGroupCommand(INDEX_FIRST_GROUP);
        SelectGroupCommand selectSecondCommand = new SelectGroupCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectGroupCommand selectFirstCommandCopy = new SelectGroupCommand(INDEX_FIRST_GROUP);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectGroupCommand selectGroupCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectGroupCommand.execute();
            assertEquals(String.format(SelectGroupCommand.MESSAGE_SELECT_GROUP_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToGroupListRequestEvent lastEvent =
                (JumpToGroupListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectGroupCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectGroupCommand selectGroupCommand = prepareCommand(index);

        try {
            selectGroupCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private SelectGroupCommand prepareCommand(Index index) {
        SelectGroupCommand selectGroupCommand = new SelectGroupCommand(index);
        selectGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);
        return selectGroupCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SetColourCommandTest.java
``` java
public class SetColourCommandTest {

    private static final String VALID_TAG_FRIEND = "friends";
    private static final String INVALID_TAG = "asd123";

    private Model model;
    private Storage storage;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        storage = new TypicalStorage().setUp();
    }

    @Test
    public void execute_setColourTag_success() throws Exception {
        SetColourCommand setColourCommand = new SetColourCommand(VALID_TAG_FRIEND, "red");
        setColourCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setTagColour(VALID_TAG_FRIEND, "red");

        assertCommandSuccess(setColourCommand, model,
                String.format(SetColourCommand.SETCOLOUR_SUCCESS, VALID_TAG_FRIEND, "red"), expectedModel);
    }

    @Test
    public void execute_setInvalidColour_failure() throws Exception {
        SetColourCommand setColourCommand = new SetColourCommand(VALID_TAG_FRIEND, "nocolour");
        setColourCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        String expectedMessage = String.format(SetColourCommand.SETCOLOUR_INVALID_COLOUR, "nocolour");

        assertCommandFailure(setColourCommand, model, expectedMessage);
    }

    @Test
    public void execute_setInvalidTag_failure() throws Exception {
        SetColourCommand setColourCommand = new SetColourCommand(INVALID_TAG, "red");
        setColourCommand.setData(model, new CommandHistory(), new UndoRedoStack(), storage);

        String expectedMessage = SetColourCommand.SETCOLOUR_INVALID_TAG;

        assertCommandFailure(setColourCommand, model, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\CreateGroupCommandParserTest.java
``` java
public class CreateGroupCommandParserTest {

    private CreateGroupCommandParser parser = new CreateGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Group expectedGroup = new GroupBuilder().withGroupName(VALID_GROUP_NAME)
                .withGroupMembers(Arrays.asList(ALICE)).build();
        assertParseSuccess(parser, CreateGroupCommand.COMMAND_WORD + GROUP_NAME_DESC + GROUP_INDEX_DESC,
                new CreateGroupCommand(expectedGroup.getGroupName(), Arrays.asList(Index.fromOneBased(1))));

        //multiple same indexes - accepted
        assertParseSuccess(parser, CreateGroupCommand.COMMAND_WORD + GROUP_NAME_DESC + GROUP_INDEX_DESC
                + " " + VALID_INDEX, new CreateGroupCommand(expectedGroup.getGroupName(),
                Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(1))));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD + VALID_GROUP_NAME + GROUP_INDEX_DESC,
                expectedMessage);

        //missing all prefix
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD + VALID_GROUP_NAME + VALID_INDEX,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid group name
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD + INVALID_GROUP_NAME_DESC
                + GROUP_INDEX_DESC, GroupName.MESSAGE_NAME_CONSTRAINTS);

        //invalid index
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD + GROUP_NAME_DESC
                + INVALID_GROUP_INDEX_DESC, MESSAGE_INVALID_INDEX);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteGroupCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteGroupCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteGroupCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGroupCommand() {
        assertParseSuccess(parser, "1", new DeleteGroupCommand(INDEX_FIRST_GROUP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteGroupCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\SelectGroupCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class SelectGroupCommandParserTest {
    private SelectGroupCommandParser parser = new SelectGroupCommandParser();

    @Test
    public void parse_validArgs_returnsSelectGroupCommand() {
        assertParseSuccess(parser, "1", new SelectGroupCommand(INDEX_FIRST_GROUP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectGroupCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\SetColourCommandParserTest.java
``` java
/**
 * Parses SetColourCommand arguments and creates a SetColourCommand object
 */
public class SetColourCommandParserTest {

    private SetColourCommandParser parser = new SetColourCommandParser();

    @Test
    public void parse_validArgs_returnsSetColourCommand() {
        assertParseSuccess(parser, "friends red", new SetColourCommand("friends", "red"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetColourCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\testutil\TypicalGroups.java
``` java
/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final ReadOnlyGroup SAMPLE_GROUP_1 = new GroupBuilder().withGroupName("Sample Group 1")
            .withGroupMembers(new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL))).build();

    public static final ReadOnlyGroup SAMPLE_GROUP_2 = new GroupBuilder().withGroupName("Sample Group 2")
            .withGroupMembers(new ArrayList<>(Arrays.asList(DANIEL, ELLE, FIONA))).build();

    public static final ReadOnlyGroup SAMPLE_GROUP_3 = new GroupBuilder().withGroupName("Sample Group 3")
            .withGroupMembers(new ArrayList<>(Arrays.asList(ALICE, GEORGE))).build();

    /**
     * Adds all the groups to the sample address book
     * @param ab
     */
    public static void addGroupsToAddressBook(AddressBook ab) {
        for (ReadOnlyGroup group : TypicalGroups.getTypicalGroups()) {
            try {
                ab.addGroup(group);
            } catch (DuplicateGroupException e) {
                assert false : "not possible";
            }
        }
    }

    public static List<ReadOnlyGroup> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(SAMPLE_GROUP_1, SAMPLE_GROUP_2, SAMPLE_GROUP_3));
    }
}
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find phone number of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find address of person in address book -> 3 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel, CARL, DANIEL, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find email of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find tags of person in address book -> 1 persons found */
        List<Tag> tags = new ArrayList<>(BENSON.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find person in address book, keyword is username of email -> 1 person found */
        command = FindCommand.COMMAND_WORD + " johnd";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find person in address book, keyword is multiple usernames of email -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " johnd" + " alice";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /*Case: find person in address book, keyword is domain names of email -> 7 persons found */
        command = FindCommand.COMMAND_WORD + " example.com";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find person in address book, keyword is invalid domain name of email -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " gmail.com";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find person in address book, keyword is invalid username of email -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " hello";
        assertCommandSuccess(command, expectedModel);

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find person in address book, keyword is substring of name -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

```
###### \java\systemtests\PinCommandSystemTest.java
``` java
public class PinCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_PIN_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE);

    @Test
    public void pinAndUnpin() {
        /* ----------------- Performing pin operation while an unfiltered list is being shown -------------------- */

        /* Case: pin the first person in the list, command with leading spaces and trailing spaces -> pinned */
        Model expectedModel = getModel();
        String command = "     " + PinCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson pinnedPerson = pinPerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /*Case: unpin the first person in the list, command with leading spaces and trailing spaces -> unpinned */
        command = "     " + UnpinCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        pinnedPerson = unpinPerson(expectedModel, INDEX_FIRST_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: pin the last person in the list -> pinned */
        Model modelBeforePinningLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforePinningLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: unpin the pinned person in the list */
        pinnedPerson = unpinPerson(getModel(), INDEX_FIRST_PERSON);
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, modelBeforePinningLast, expectedResultMessage);

        /* Case: pin the middle person in the list -> pinned */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* Case: unpin the pinned person in the list */
        pinnedPerson = unpinPerson(getModel(), INDEX_FIRST_PERSON);
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, modelBeforePinningLast, expectedResultMessage);

        /* ------------------ Performing pin operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, pin index within bounds of address book and person list -> pinned */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: unpin the pinned person in the list */
        pinnedPerson = unpinPerson(getModel(), INDEX_FIRST_PERSON);
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, modelBeforePinningLast, expectedResultMessage);

        /* Case: filtered person list, pin index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = PinCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------------- Performing multiple pin and unpin operations------------------------------- */

        /* Case: Pins last 3 persons and unpins them accordingly */
        //Pin last person
        showAllPersons();
        expectedModel = getModel();
        lastPersonIndex = getLastIndex(expectedModel);
        command = PinCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased();
        pinnedPerson = pinPerson(expectedModel, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Pin second last person
        expectedModel = getModel();
        lastPersonIndex = getLastIndex(expectedModel);
        command = PinCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased();
        pinnedPerson = pinPerson(expectedModel, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Pin third last person
        expectedModel = getModel();
        lastPersonIndex = getLastIndex(expectedModel);
        command = PinCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased();
        pinnedPerson = pinPerson(expectedModel, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Unpin third person
        expectedModel = getModel();
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_THIRD_PERSON.getOneBased();
        pinnedPerson = unpinPerson(expectedModel, INDEX_THIRD_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Unpin second person
        expectedModel = getModel();
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_SECOND_PERSON.getOneBased();
        pinnedPerson = unpinPerson(expectedModel, INDEX_SECOND_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        //Unpin first person
        expectedModel = getModel();
        command = UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        pinnedPerson = unpinPerson(expectedModel, INDEX_FIRST_PERSON);
        expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, pinnedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid pin operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = PinCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_PIN_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = PinCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_PIN_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = PinCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(PinCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_PIN_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(PinCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_PIN_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("PiN 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Pins the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the pinned person
     */
    private ReadOnlyPerson pinPerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = model.getFilteredPersonList().get(index.getZeroBased());
        try {
            model.pinPerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (CommandException ce) {
            throw new AssertionError("targetPerson unable to be pinned");
        } catch (EmptyAddressBookException eabe) {
            throw new AssertionError("address book is empty");
        }
        return targetPerson;
    }

    /**
     * Unpins the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the unpinned person
     */
    private ReadOnlyPerson unpinPerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = model.getFilteredPersonList().get(index.getZeroBased());
        try {
            model.unpinPerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (CommandException ce) {
            throw new AssertionError("targetPerson unable to be unpinned");
        } catch (EmptyAddressBookException eabe) {
            throw new AssertionError("address book is empty");
        }
        return targetPerson;
    }

    /**
     * Removes all pins in the model
     */
    private void removeAllPin() {
        Model unpinModel = getModel();
        List<ReadOnlyPerson> list = unpinModel.getFilteredPersonList().filtered(PREDICATE_SHOW_PINNED_PERSONS);
        for (ReadOnlyPerson person: list) {
            removePin(unpinModel, person);
        }
    }

    /**
     * Removes pin tag from a person
     * @param model
     * @param person
     */
    private void removePin(Model model, ReadOnlyPerson person) {
        Set<Tag> listTags = person.getTags();
        try {
            for (Tag tag : listTags) {
                if ("Pinned".equals(tag.tagName)) {
                    try {
                        model.unpinPerson(person);
                    } catch (EmptyAddressBookException eabe) {
                        throw new AssertionError("EmptyAddressBookException error");
                    }
                }

            }
        } catch (CommandException ce) {
            throw new AssertionError("CommandException error");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("PersonNotFoundException error");
        }
    }

    /**
     * Unpins the person at {@code toUnpin} by creating a default {@code UnpinCommand} using {@code toUnpin} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see PinCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertUnpinCommandSuccess(Index toPin) {
        Model expectedModel = getModel();
        ReadOnlyPerson unpinnedPerson = unpinPerson(expectedModel, toPin);
        String expectedResultMessage = String.format(MESSAGE_UNPIN_PERSON_SUCCESS, unpinnedPerson);

        assertCommandSuccess(
                PinCommand.COMMAND_WORD + " " + toPin.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Pins the person at {@code toPin} by creating a default {@code PinCommand} using {@code toPin} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see PinCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toPin) {
        Model expectedModel = getModel();
        ReadOnlyPerson pinnedPerson = pinPerson(expectedModel, toPin);
        String expectedResultMessage = String.format(MESSAGE_PIN_PERSON_SUCCESS, pinnedPerson);

        assertCommandSuccess(
                PinCommand.COMMAND_WORD + " " + toPin.getOneBased(), expectedModel, expectedResultMessage);
    }


    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
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
