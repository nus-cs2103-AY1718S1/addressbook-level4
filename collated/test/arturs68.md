# arturs68
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
        Region groupContainer = getChildNode(GROUPS_FIELD_ID);
        this.groupLabels = groupContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public List<String> getGroups() {
        return groupLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
```
###### \java\seedu\address\logic\commands\ChangePicCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.model.person.ProfilePicture.DEFAULT_PICTURE;
import static seedu.address.model.util.SampleDataUtil.SAMPLE_PICTURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ChangePicCommand.
 */
public class ChangePicCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_changepic_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .build();
        editedPerson.setProfilePicture(new ProfilePicture(SAMPLE_PICTURE));

        ChangePicCommand changePicCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getProfilePicture().value);

        String expectedMessage = String.format(ChangePicCommand.MESSAGE_CHANGEPIC_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(changePicCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).build();
        editedPerson.setProfilePicture(new ProfilePicture(SAMPLE_PICTURE));
        ChangePicCommand changePicCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getProfilePicture().value);
        String expectedMessage = String.format(ChangePicCommand.MESSAGE_CHANGEPIC_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(changePicCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ChangePicCommand changePicCommand = prepareCommand(outOfBoundIndex, SAMPLE_PICTURE);

        assertCommandFailure(changePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ChangePicCommand changePicCommand = prepareCommand(outOfBoundIndex, SAMPLE_PICTURE);

        assertCommandFailure(changePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ChangePicCommand standardCommand = new ChangePicCommand(INDEX_FIRST_PERSON, DEFAULT_PICTURE);

        // same values -> returns true
        ChangePicCommand commandWithSameValues = new ChangePicCommand(INDEX_FIRST_PERSON, DEFAULT_PICTURE);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ChangePicCommand(INDEX_SECOND_PERSON, SAMPLE_PICTURE)));

        // different groups -> returns false
        assertFalse(standardCommand.equals(new ChangePicCommand(INDEX_FIRST_PERSON, SAMPLE_PICTURE)));
    }

    /**
     * Returns an {@code ChangePicCommand} with parameters {@code index} and {@code group}.
     */
    private ChangePicCommand prepareCommand(Index index, String picturePath) {
        ChangePicCommand changePictureCommand = new ChangePicCommand(index, picturePath);
        changePictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changePictureCommand;
    }
}
```
###### \java\seedu\address\logic\commands\GroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_FAMILY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GroupCommand.
 */
public class GroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addGroup_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withGroups("Some Group").build();

        GroupCommand groupCommand = prepareCommand(INDEX_FIRST_PERSON, new Group("Some Group"));

        String expectedMessage =
                String.format(GroupCommand.MESSAGE_ADD_GROUP_SUCCESS, editedPerson.getName(), "Some Group");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withGroups("Some Group").build();
        GroupCommand groupCommand = prepareCommand(INDEX_FIRST_PERSON, new Group("Some Group"));

        String expectedMessage =
                String.format(GroupCommand.MESSAGE_ADD_GROUP_SUCCESS, editedPerson.getName(), "Some Group");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GroupCommand groupCommand = prepareCommand(outOfBoundIndex, new Group(VALID_GROUP_NAME_FAMILY));

        assertCommandFailure(groupCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GroupCommand remarkCommand = prepareCommand(outOfBoundIndex, new Group(VALID_GROUP_NAME_FAMILY));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        final GroupCommand standardCommand = new GroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_FAMILY));

        // same values -> returns true
        GroupCommand commandWithSameValues = new GroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_FAMILY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new GroupCommand(INDEX_SECOND_PERSON, new Group(VALID_GROUP_NAME_FAMILY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new GroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_CS2103))));
    }

    /**
     * Returns an {@code GroupCommand} with parameters {@code index} and {@code group}
     */
    private GroupCommand prepareCommand(Index index, Group group) {
        GroupCommand groupCommand = new GroupCommand(index, group);
        groupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return groupCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class RemoveTagCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
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
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON);
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
        SelectCommand selectCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectCommand.execute();
            assertEquals(String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = prepareCommand(index);

        try {
            selectCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private SelectCommand prepareCommand(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        selectCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectCommand;
    }
}
```
###### \java\seedu\address\logic\commands\UngroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_FAMILY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UngroupCommand.
 */
public class UngroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_ungroup_success() throws Exception {
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person afterEditPerson = new PersonBuilder(personInFilteredList).build();
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withGroups("Some group").build();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        model.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        UngroupCommand ungroupCommand = prepareCommand(INDEX_FIRST_PERSON, new Group("Some group"));
        String expectedMessage =
                String.format(UngroupCommand.MESSAGE_UNGROUP_SUCCESS, afterEditPerson.getName(), "Some group");

        assertCommandSuccess(ungroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_ungroupGroupNotFound_failure() throws Exception {
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person person = new PersonBuilder(personInFilteredList).build();

        UngroupCommand ungroupCommand = prepareCommand(INDEX_FIRST_PERSON, new Group("Some grou"));
        String expectedMessage =
                String.format(UngroupCommand.MESSAGE_GROUP_NOT_FOUND, person.getName(), "Some grou");

        assertCommandFailure(ungroupCommand, model, expectedMessage);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person afterEditPerson = new PersonBuilder(personInFilteredList).build();
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withGroups("Some group").build();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        model.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        UngroupCommand ungroupCommand = prepareCommand(INDEX_FIRST_PERSON, new Group("Some group"));
        String expectedMessage =
                String.format(UngroupCommand.MESSAGE_UNGROUP_SUCCESS, afterEditPerson.getName(), "Some group");

        assertCommandSuccess(ungroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UngroupCommand ungroupCommand = prepareCommand(outOfBoundIndex, new Group(VALID_GROUP_NAME_FAMILY));

        assertCommandFailure(ungroupCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UngroupCommand ungroupCommand = prepareCommand(outOfBoundIndex, new Group(VALID_GROUP_NAME_FAMILY));

        assertCommandFailure(ungroupCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        final UngroupCommand standardCommand =
                new UngroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_FAMILY));

        // same values -> returns true
        UngroupCommand commandWithSameValues =
                new UngroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_FAMILY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        Assert.assertFalse(standardCommand.equals(null));

        // different types -> returns false
        Assert.assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        Assert.assertFalse(
                standardCommand.equals(new UngroupCommand(INDEX_SECOND_PERSON, new Group(VALID_GROUP_NAME_FAMILY))));

        // different descriptor -> returns false
        Assert.assertFalse(
                standardCommand.equals(new UngroupCommand(INDEX_FIRST_PERSON, new Group(VALID_GROUP_NAME_CS2103))));
    }

    /**
     * Returns an {@code UngroupCommand} with parameters {@code index} and {@code group}
     */
    private UngroupCommand prepareCommand(Index index, Group group) {
        UngroupCommand ungroupCommand = new UngroupCommand(index, group);
        ungroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ungroupCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_removeTag() throws Exception {
        final String tag = "SomeTag";
        RemoveTagCommand command = (RemoveTagCommand) parser.parseCommand(RemoveTagCommand.COMMAND_WORD
                + " " + PREFIX_TAG + tag);
        assertEquals(new RemoveTagCommand(new Tag(tag)), command);
    }

    @Test
    public void parseCommand_group() throws Exception {
        final String groupName = "Some group name";
        GroupCommand command = (GroupCommand) parser.parseCommand(GroupCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_GROUP_NAME + " " + groupName);
        assertEquals(new GroupCommand(INDEX_FIRST_PERSON, new Group(groupName)), command);
    }

    @Test
    public void parseCommand_ungroup() throws Exception {
        final Group group = new Group("Some group");
        UngroupCommand command = (UngroupCommand) parser.parseCommand(UngroupCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_GROUP_NAME + " " + group.groupName);
        assertEquals(new UngroupCommand(INDEX_FIRST_PERSON, group), command);
    }
```
###### \java\seedu\address\logic\parser\ChangePicCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.util.SampleDataUtil.SAMPLE_PICTURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangePicCommand;

public class ChangePicCommandParserTest {
    private ChangePicCommandParser parser = new ChangePicCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String picturePath = SAMPLE_PICTURE;

        // has picturePath
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_PATH.toString() + " " + picturePath;
        ChangePicCommand expectedCommand = new ChangePicCommand(INDEX_FIRST_PERSON, picturePath);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no picturePath specified
        /*userInput = targetIndex.getOneBased() + " " + PREFIX_PATH.toString();
        expectedCommand = new ChangePicCommand(INDEX_FIRST_PERSON, "");
        assertParseFailure(parser, userInput, expectedCommand); */
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePicCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, ChangePicCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\GroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.model.group.Group;

public class GroupCommandParserTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String groupName = "Some group name";

        // have group
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_GROUP_NAME.toString() + " " + groupName;
        GroupCommand expectedCommand = new GroupCommand(INDEX_FIRST_PERSON, new Group(groupName));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no group specified - exception thrown
        thrown.expect(IllegalValueException.class);
        new GroupCommand(INDEX_FIRST_PERSON, new Group(""));
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, GroupCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() throws Exception {
        String commandText = RemoveTagCommand.COMMAND_WORD + " " + PREFIX_TAG + VALID_TAG_FRIEND;
        assertParseSuccess(parser, commandText, new RemoveTagCommand(new Tag(VALID_TAG_FRIEND)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\UngroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UngroupCommand;
import seedu.address.model.group.Group;

public class UngroupCommandParserTest {
    private UngroupCommandParser parser = new UngroupCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Group group = new Group("Some group");

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_GROUP_NAME.toString() + " " + group.groupName;
        UngroupCommand expectedCommand = new UngroupCommand(INDEX_FIRST_PERSON, group);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UngroupCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, UngroupCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void sorted() {
        AddressBook newData = getTypicalAddressBook();
        SortedList<ReadOnlyPerson> sorted =
                newData.getPersonList()
                        .sorted(Comparator.comparing((ReadOnlyPerson person) -> person.getName().toString()));
        assertEquals(sorted, newData.getPersonList());
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getGroupList().remove(0);
    }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void removeTag() throws IllegalValueException, PersonNotFoundException {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        // Removing a tag that does not exist in an address book does not change address book
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);

        modelManager.removeTag(new Tag("xx"));
        assertTrue(modelManager.equals(modelManagerCopy));

        //Removing a tag from an address book changes it
        ObservableList<Tag> tags = modelManager.getAddressBook().getTagList();
        ObservableList<Tag> tagsWithoutFriends = tags.filtered(x -> !x.tagName.equals("friends"));
        modelManager.removeTag(new Tag("friends"));
        ReadOnlyAddressBook addressBookAfterTagRemoval = modelManager.getAddressBook();
        assertFalse(addressBookAfterTagRemoval.getTagList().contains(new Tag("friends")));
        assertTrue(modelManager.getAddressBook().getTagList().size() == tagsWithoutFriends.size());

        //Making sure no person has the removed tag
        ObservableList<ReadOnlyPerson> personListAfterTagRemoval = addressBookAfterTagRemoval.getPersonList();
        for (ReadOnlyPerson person : personListAfterTagRemoval) {
            assertFalse(person.getTags().contains(new Tag("friends")));
        }
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Parses the {@code groups} into a {@code Set<Group>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withGroups(String ... groups) {
        try {
            this.person.setGroups(SampleDataUtil.getGroupSet(groups));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("groups are expected to be unique.");
        }
        return this;
    }
```
