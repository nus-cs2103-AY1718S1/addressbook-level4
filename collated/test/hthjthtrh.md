# hthjthtrh
###### /java/seedu/address/logic/commands/DeleteCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ArrayList<Index> testIndexes = new ArrayList<>();

    @Test
    public void execute_validIndexUnfilteredListSinglePerson_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        testIndexes.clear();
        testIndexes.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = prepareCommand(testIndexes);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        ArrayList<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        personsToDelete.add(personToDelete);

        assertCommandSuccess(deleteCommand, model,
                DeleteCommand.getSb(personsToDelete), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredListSingleOutOfBound_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        testIndexes.clear();
        testIndexes.add(outOfBoundIndex);

        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        assertCommandFailure(deleteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        testIndexes.clear();
        testIndexes.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        ArrayList<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        personsToDelete.add(personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model,
                DeleteCommand.getSb(personsToDelete), expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        testIndexes.clear();
        testIndexes.add(outOfBoundIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(testIndexes);

        assertCommandFailure(deleteCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
    }

    @Test
    public void equals() {

        testIndexes.clear();
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(testIndexes);
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(testIndexes);

        ArrayList<Index> testIndexes2 = new ArrayList<>();
        testIndexes2.add(TypicalIndexes.INDEX_SECOND_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(testIndexes2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(ArrayList<Index> index) {
        //testIndexes.clear();
        DeleteCommand deleteCommand = new DeleteCommand(testIndexes);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
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
###### /java/seedu/address/logic/commands/DeleteGroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_NONEXISTENT_GROUP;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.testutil.TypicalGroups;

public class DeleteGroupCommandTest {

    private Model model;
    private Model expectedModel;
    private DeleteGroupCommand deleteGroupCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    private void prepareCommand(String grpName) {
        deleteGroupCommand = new DeleteGroupCommand(grpName);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }
    @Test
    public void execute_grpNameExist_success() {
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        expectedModel.deleteGroup(testGrps.get(0));

        prepareCommand("TestGrp3");
        assertCommandSuccess(deleteGroupCommand, model,
                String.format(MESSAGE_DELETE_GROUP_SUCCESS, "TestGrp3"), expectedModel);
    }

    @Test
    public void execute_grpNameNonExistent_failure() {
        String expectedFailureMessage = MESSAGE_EXECUTION_FAILURE
                + String.format(MESSAGE_NONEXISTENT_GROUP, "Non_Existent");
        prepareCommand("Non_Existent");
        assertCommandFailure(deleteGroupCommand, model, expectedFailureMessage);

        expectedFailureMessage = MESSAGE_EXECUTION_FAILURE + String.format(MESSAGE_NONEXISTENT_GROUP, "12Wot");
        prepareCommand("12Wot");
        assertCommandFailure(deleteGroupCommand, model, expectedFailureMessage);
    }

    @Test
    public void equals() {
        DeleteGroupCommand deleteGroupCommand1 = new DeleteGroupCommand("HOT");
        DeleteGroupCommand deleteGroupCommand2 = new DeleteGroupCommand("HOT");
        DeleteGroupCommand deleteGroupCommand3 = new DeleteGroupCommand("Not_So_Hot");

        // same object -> returns true
        assertTrue(deleteGroupCommand1.equals(deleteGroupCommand1));

        // same value -> return true
        assertTrue(deleteGroupCommand1.equals(deleteGroupCommand2));

        // different argument type -> return false
        assertFalse(deleteGroupCommand1.equals(deleteGroupCommand3));

        // different types -> returns false
        assertFalse(deleteGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(deleteGroupCommand1.equals(null));
    }
}
```
###### /java/seedu/address/logic/commands/EditGroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_ADD_PERSON_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_CHANGE_NAME_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_GROUP_NONEXISTENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.GroupContainsPersonPredicate;
import seedu.address.testutil.TypicalGroups;
import seedu.address.testutil.TypicalPersons;

public class EditGroupCommandTest {

    private Model model;
    private Model expectedModel;
    private EditGroupCommand editGrpCmd;
    private List<ReadOnlyPerson> typicalPersons = TypicalPersons.getTypicalPersons();
    private Predicate predicate;

    @Before
    public void setUp() {
        resetModel();
    }

    private void resetModel() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    private void prepareCommand(String grpName, String op, String detail) {
        editGrpCmd = new EditGroupCommand(grpName, op, detail);
        editGrpCmd.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_changeName_success() throws DuplicateGroupException {
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        Group testGroup = testGrps.get(0);
        expectedModel.setGrpName(testGroup, "validName");

        prepareCommand("TestGrp3", "grpName", "validName");
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_CHANGE_NAME_SUCCESS, "TestGrp3", "validName"),
                expectedModel);
    }

    @Test
    public void execute_addDeletePerson_success() throws DuplicatePersonException, PersonNotFoundException {
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        Group testGroup = testGrps.get(0);
        ReadOnlyPerson person = typicalPersons.get(4);
        expectedModel.addPersonToGroup(testGroup, person);
        predicate = new GroupContainsPersonPredicate(testGroup);
        expectedModel.updateFilteredPersonList(predicate);

        prepareCommand("TestGrp3", "add", "5");
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_ADD_PERSON_SUCCESS, "TestGrp3",
                person.toString()),
                expectedModel);

        // deleting the person that was added
        expectedModel.removePersonFromGroup(testGroup, person);
        predicate = new GroupContainsPersonPredicate(testGroup);
        expectedModel.updateFilteredPersonList(predicate);
        prepareCommand("TestGrp3", "delete", "4");
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_DELETE_PERSON_SUCCESS, "TestGrp3",
                person.toString()),
                expectedModel);
    }

    @Test
    public void execute_groupNonExistent_failure() {
        // change name op
        prepareCommand("nonExistentGroup", "grpName", "validName");
        assertCommandFailure(editGrpCmd, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_GROUP_NONEXISTENT);

        // add / delete op
        prepareCommand("nonExistentGroup", "add", "1");
        assertCommandFailure(editGrpCmd, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_GROUP_NONEXISTENT);
    }

    @Test
    public void execute_addDeleteOutOfRangeIndex_failure() {
        prepareCommand("TestGrp3", "add", "10");
        // exception header for this exception is null
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        prepareCommand("testGrp1", "delete", "20");
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        EditGroupCommand editGroupCommand1 = new EditGroupCommand("testName1", "add", "2");
        EditGroupCommand editGroupCommand2 = new EditGroupCommand("testName1", "add", "2");
        EditGroupCommand editGroupCommand3 = new EditGroupCommand("DiffName", "grpName", "AnotherName");

        // same object -> returns true
        assertTrue(editGroupCommand1.equals(editGroupCommand1));

        // same value -> return true
        assertTrue(editGroupCommand1.equals(editGroupCommand2));

        // different argument type -> return false
        assertFalse(editGroupCommand1.equals(editGroupCommand3));

        // different types -> returns false
        assertFalse(editGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(editGroupCommand1.equals(null));
    }
}
```
###### /java/seedu/address/logic/commands/GroupingCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.person.ReadOnlyPerson;

public class GroupingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ArrayList<Index> testIndexes = new ArrayList<>();

    @Test
    public void execute_newGroup_success() {
        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(1));
        testIndexes.add(Index.fromOneBased(3));

        GroupingCommand testCommand = prepareCommand("newGroup", testIndexes);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(expectedModel.getAddressBook().getPersonList().get(0));
        personList.add(expectedModel.getAddressBook().getPersonList().get(2));

        try {
            expectedModel.createGroup("newGroup", personList);
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        }

        String expectedMessage = GroupingCommand.getSb("newGroup", personList);

        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_newGroupSomeIndexOutOfBound_success() {
        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(1));
        testIndexes.add(Index.fromOneBased(20));

        GroupingCommand testCommand = prepareCommand("newGroup", testIndexes);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(expectedModel.getAddressBook().getPersonList().get(0));

        try {
            expectedModel.createGroup("newGroup", personList);
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        }

        String expectedMessage = GroupingCommand.getSb("newGroup", personList);

        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addExistingGroup_failure() {

        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(1));
        testIndexes.add(Index.fromOneBased(3));

        GroupingCommand testCommand = prepareCommand("testGrp1", testIndexes);

        String expectedMessage = MESSAGE_EXECUTION_FAILURE + GroupingCommand.MESSAGE_DUPLICATE_GROUP_NAME;

        assertCommandFailure(testCommand, model, expectedMessage);
    }

    @Test
    public void execute_addNewGroupNoValidIndex_failure() {
        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(9));
        testIndexes.add(Index.fromOneBased(10));

        GroupingCommand testCommand = prepareCommand("newGroup", testIndexes);

        String expectedMessage = MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_INDEX_ALL;

        assertCommandFailure(testCommand, model, expectedMessage);
    }

    @Test
    public void equals() {

        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(1));

        GroupingCommand groupFirstCommand = new GroupingCommand("sample", testIndexes);
        GroupingCommand groupFirstCommandCopy = new GroupingCommand("sample", testIndexes);
        GroupingCommand groupSecondCommand = new GroupingCommand("anotherSample", testIndexes);

        // same object -> returns true
        assertTrue(groupFirstCommand.equals(groupFirstCommand));

        // same values -> returns true
        assertTrue(groupFirstCommand.equals(groupFirstCommandCopy));

        // different types -> returns false
        assertFalse(groupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(groupFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(groupFirstCommand.equals(groupSecondCommand));

    }

    /**
     * helper function to create a grouping command
     * @param grpName
     * @param index of people to include in the group
     * @return the grouping command
     */
    private GroupingCommand prepareCommand(String grpName, List<Index> index) {
        //testIndexes.clear();
        GroupingCommand grpCommand = new GroupingCommand(grpName, index);
        grpCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return grpCommand;
    }
}
```
###### /java/seedu/address/logic/commands/UndoCommandTest.java
``` java
    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        //need to refresh the Undo command since previously its step was reduced to 0
        undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack. Refresh the undo command again
        undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        assertCommandFailure(undoCommand, model, MESSAGE_EXECUTION_FAILURE + UndoCommand.MESSAGE_FAILURE);
    }
```
###### /java/seedu/address/logic/commands/ViewGroupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewGroupCommand.MESSAGE_GROUPING_PERSON_SUCCESS;
import static seedu.address.logic.commands.ViewGroupCommand.MESSAGE_GROUP_NONEXISTENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.predicates.GroupContainsPersonPredicate;
import seedu.address.testutil.TypicalGroups;

public class ViewGroupCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewGroupCommand viewGrpCmd;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    /**
     * creates the viewgroupcommand depending on grpName and idx
     */
    private ViewGroupCommand prepareCommand(String grpName, Index idx) {
        if (grpName != null) {
            viewGrpCmd = new ViewGroupCommand(grpName);
        } else {
            viewGrpCmd = new ViewGroupCommand(idx);
        }
        viewGrpCmd.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewGrpCmd;
    }

    @Test
    public void execute_grpNameGrpExist_success() {
        // preparing expectedModel
        TypicalGroups typicalGroups = new TypicalGroups();
        Group testGroup = typicalGroups.getTestGroup3();
        Predicate predicate = new GroupContainsPersonPredicate(testGroup);
        expectedModel.updateFilteredPersonList(predicate);

        ViewGroupCommand viewGroupCommand = prepareCommand("TestGrp3", null);
        String expectedMessage = String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                testGroup.getPersonList().size(), testGroup.getGrpName());
        assertCommandSuccess(viewGroupCommand, model, expectedMessage, expectedModel);

        testGroup = typicalGroups.getTestGroup4();
        predicate = new GroupContainsPersonPredicate(testGroup);
        expectedModel.updateFilteredPersonList(predicate);

        viewGroupCommand = prepareCommand("TestGrp4", null);
        expectedMessage = String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                testGroup.getPersonList().size(), testGroup.getGrpName());
        assertCommandSuccess(viewGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_grpIndexGrpExist_success() {
        TypicalGroups typicalGroups = new TypicalGroups();
        Group testGroup = typicalGroups.getTestGroup3();
        Predicate predicate = new GroupContainsPersonPredicate(testGroup);
        expectedModel.updateFilteredPersonList(predicate);

        ViewGroupCommand viewGroupCommand = prepareCommand(null, Index.fromOneBased(1));
        String expectedMessage = String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                testGroup.getPersonList().size(), testGroup.getGrpName());
        assertCommandSuccess(viewGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_grpNonExistent_failure() {
        ViewGroupCommand viewGroupCommand = prepareCommand("DoesntExist", null);
        assertCommandFailure(viewGroupCommand, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_GROUP_NONEXISTENT);
    }

    @Test
    public void execute_indexOutOfBound_failure() {
        ViewGroupCommand viewGroupCommand = prepareCommand(null, Index.fromOneBased(10));
        assertCommandFailure(viewGroupCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewGroupCommand viewGroupCommand1 = new ViewGroupCommand("GroupName");
        ViewGroupCommand viewGroupCommand2 = new ViewGroupCommand("GroupName");
        ViewGroupCommand viewGroupCommand3 = new ViewGroupCommand("Group");
        ViewGroupCommand viewGroupCommand4 = new ViewGroupCommand(Index.fromOneBased(10));
        ViewGroupCommand viewGroupCommand5 = new ViewGroupCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(viewGroupCommand1.equals(viewGroupCommand1));

        // same value -> return true
        assertTrue(viewGroupCommand1.equals(viewGroupCommand2));

        // same object -> return true
        assertTrue(viewGroupCommand4.equals(viewGroupCommand4));

        // different index value -> return false
        assertFalse(viewGroupCommand4.equals(viewGroupCommand5));

        // different grp name value -> return false
        assertFalse(viewGroupCommand1.equals(viewGroupCommand3));

        // different argument type -> return false
        assertFalse(viewGroupCommand1.equals(viewGroupCommand4));

        // different types -> returns false
        assertFalse(viewGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(viewGroupCommand1.equals(null));
    }
}
```
###### /java/seedu/address/logic/parser/DeleteCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VALUE_ARGUMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.TypicalIndexes;
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private ArrayList<Index> testIndexes = new ArrayList<Index>();

    private DeleteCommandParser parser = new DeleteCommandParser();
    private final String deleteParseEmptyArg = MESSAGE_INVALID_COMMAND_FORMAT + DeleteCommand.MESSAGE_USAGE;
    private final String deleteParseInvalidValue = MESSAGE_INVALID_VALUE_ARGUMENT + DeleteCommand.MESSAGE_USAGE;

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new DeleteCommand(testIndexes));

        testIndexes.clear();
        testIndexes.add(TypicalIndexes.INDEX_FIFTH_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_EIGHTH_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_THIRDTEENTH_PERSON);
        assertParseSuccess(parser, "5 8 13", new DeleteCommand(testIndexes));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", deleteParseEmptyArg);
        assertParseFailure(parser, " ", deleteParseEmptyArg);
        assertParseFailure(parser, "a", deleteParseInvalidValue);
        assertParseFailure(parser, "1 3 a", deleteParseInvalidValue);

    }
}
```
###### /java/seedu/address/logic/parser/DeleteGroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGroupCommand;

public class DeleteGroupCommandParserTest {

    private static final String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT
            + DeleteGroupCommand.MESSAGE_USAGE;
    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();


    @Test
    public void parse_correctGrpNameFormat_success() {
        assertParseSuccess(parser, "testGroupName", new DeleteGroupCommand("testGroupName"));
        assertParseSuccess(parser, "     testGroupName      ", new DeleteGroupCommand("testGroupName"));
        assertParseSuccess(parser, "1GrpNameWithNumber", new DeleteGroupCommand("1GrpNameWithNumber"));
    }

    @Test
    public void parse_multipleArgument_failure() {
        assertParseFailure(parser, "blahh blahhh", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "blahh 1 2", MESSAGE_PARSE_FAILURE);
    }

    @Test
    public void parse_invalidGrpNameFormat_failure() {
        assertParseFailure(parser, "1", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "54321", MESSAGE_PARSE_FAILURE);
    }
}
```
###### /java/seedu/address/logic/parser/EditGroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.EditGroupCommand;

public class EditGroupCommandParserTest {

    private static String MESSAGE_PARSEFAILURE_TEST = MESSAGE_INVALID_COMMAND_FORMAT + EditGroupCommand.MESSAGE_USAGE;

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    public void parse_validArgument_success() {
        assertParseSuccess(parser, "  testGrp1 grpName TestGrp1!",
                new EditGroupCommand("testGrp1", "grpName", "TestGrp1!"));

        assertParseSuccess(parser, "TestGroup1 add 1", new EditGroupCommand("TestGroup1", "add", "1"));
        assertParseSuccess(parser, "TestGroup1 delete 1", new EditGroupCommand("TestGroup1", "delete", "1"));
    }

    @Test
    public void parse_invalidGroupName_failure() {
        testFailure("123 grpName validName");
        testFailure("validName grpName 123");
    }

    @Test
    public void parse_invalidOperation_failure() {
        testFailure("validName invalidOp validNameToo");
        testFailure("validName ADD 1");
    }

    @Test
    public void parse_invalidArgumentsCount_failure() {
        testFailure("validName grpName 1 2 3 4");
        testFailure("");
        testFailure("grpName 1");

    }

    private void testFailure(String inputToTest) {
        assertParseFailure(parser, inputToTest, MESSAGE_PARSEFAILURE_TEST);
    }
}
```
###### /java/seedu/address/logic/parser/GroupingCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.GroupingCommandParser.MESSAGE_INCORRECT_GROUPNAME_FORMAT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupingCommand;
import seedu.address.testutil.TypicalIndexes;

public class GroupingCommandParserTest {
    private GroupingCommandParser parser = new GroupingCommandParser();
    private List<Index> testIndexes = new ArrayList<>();

    @Test
    public void parse_allFieldsPresent_success() {
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        String testGrpName = "test";
        assertParseSuccess(parser, "test 1", new GroupingCommand(testGrpName, testIndexes));

        //duplicate indexes
        assertParseSuccess(parser, "test 1 1 1", new GroupingCommand(testGrpName, testIndexes));

        //multiple indexes
        testIndexes.add(TypicalIndexes.INDEX_THIRD_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_THIRDTEENTH_PERSON);
        assertParseSuccess(parser, "test 1 3 13", new GroupingCommand(testGrpName, testIndexes));
    }

    @Test
    public void parse_indexFieldsMissing_failure() {
        String expectedMessage = MESSAGE_INVALID_COMMAND_FORMAT + GroupingCommand.MESSAGE_USAGE;

        assertParseFailure(parser, "test", expectedMessage);

        //trailing white spaces
        assertParseFailure(parser, "   test    ", expectedMessage);
    }

    @Test
    public void parse_emptyArgument_failure() {
        String expectedMessage = MESSAGE_INVALID_COMMAND_FORMAT + GroupingCommand.MESSAGE_USAGE;

        assertParseFailure(parser, "",  expectedMessage);
    }

    @Test
    public void parse_invalidGroupName_failure() {
        String expectedMessage = MESSAGE_INCORRECT_GROUPNAME_FORMAT + GroupingCommand.MESSAGE_USAGE;

        assertParseFailure(parser, "1234321 1 3 5 ", expectedMessage);
    }
}
```
###### /java/seedu/address/logic/parser/ViewGroupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewGroupCommand;

public class ViewGroupCommandParserTest {

    private static String MESSAGE_PARSE_FAILURE = MESSAGE_INVALID_COMMAND_FORMAT + ViewGroupCommand.MESSAGE_USAGE;

    private ViewGroupCommandParser parser = new ViewGroupCommandParser();

    @Test
    public void parse_groupName_success() {
        assertParseSuccess(parser, "testGroupName", new ViewGroupCommand("testGroupName"));
        assertParseSuccess(parser, "     testGroupName      ", new ViewGroupCommand("testGroupName"));
        assertParseSuccess(parser, "1GrpNameWithNumber", new ViewGroupCommand("1GrpNameWithNumber"));
    }

    @Test
    public void parse_index_success() {
        Index testIdx = Index.fromOneBased(1);
        assertParseSuccess(parser, "1", new ViewGroupCommand(testIdx));
        assertParseSuccess(parser, "  1      ", new ViewGroupCommand(testIdx));
        testIdx = Index.fromOneBased(999);
        assertParseSuccess(parser, "   999    ", new ViewGroupCommand(testIdx));
    }

    @Test
    public void parse_missingArgument_failure() {
        assertParseFailure(parser, "", MESSAGE_PARSE_FAILURE);
    }

    @Test
    public void parse_multipleArguments_failure() {
        assertParseFailure(parser, "hi lol", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "hi 1 blah blah", MESSAGE_PARSE_FAILURE);
        assertParseFailure(parser, "1 lol blah blahh", MESSAGE_PARSE_FAILURE);
    }
}
```
###### /java/seedu/address/model/UniqueGroupListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.UniqueGroupList;

public class UniqueGroupListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/testutil/AddressBookBuilder.java
``` java
    /**
     * adds a new group into the addressbook being built
     * @param grp
     * @return
     */
    public AddressBookBuilder withGroup(Group grp) {
        try {
            addressBook.addGroup(new Group(grp));
        } catch (DuplicatePersonException e) {
            throw new IllegalArgumentException("person is expected to be unique.");
        } catch (DuplicateGroupException e) {
            throw new IllegalArgumentException("group is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/TypicalGroups.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * to create typical groups
 */
public class TypicalGroups {

    private Group testGroup3;

    private Group testGroup4;

    public TypicalGroups() {
        testGroup3 = new Group("TestGrp3");
        try {
            testGroup3.add(TypicalPersons.ALICE);
            testGroup3.add(TypicalPersons.DANIEL);
            testGroup3.add(TypicalPersons.CARL);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

        try {
            testGroup4 = new Group(testGroup3);
            testGroup4.setGrpName("TestGrp4");
            testGroup4.add(TypicalPersons.ELLE);
            testGroup4.add(TypicalPersons.GEORGE);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public Group getTestGroup3() {
        return testGroup3;
    }

    public Group getTestGroup4() {
        return testGroup4;
    }

    public List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(testGroup3, testGroup4));
    }
}
```
