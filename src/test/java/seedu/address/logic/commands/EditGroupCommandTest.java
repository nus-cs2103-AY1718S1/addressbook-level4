//@@author hthjthtrh
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_ADD_PERSON_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_CHANGE_NAME_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.EditGroupCommand.MESSAGE_GROUP_NONEXISTENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
import seedu.address.testutil.TypicalGroups;
import seedu.address.testutil.TypicalPersons;

public class EditGroupCommandTest {

    private Model model;
    private Model expectedModel;
    private EditGroupCommand editGrpCmd;
    private List<ReadOnlyPerson> typicalPersons = TypicalPersons.getTypicalPersons();

    @Before
    public void setUp() {
        resetModel();
    }

    private void resetModel() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    private void prepareCommand(String grpName, Index grpIndex, String op, String newName, boolean indicateByIdx) {
        editGrpCmd = new EditGroupCommand(grpName, grpIndex, op, newName, indicateByIdx);
        editGrpCmd.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    private void prepareCommand(String grpName, Index grpIndex, String op, Index personIdx, boolean indicateByIdx) {
        editGrpCmd = new EditGroupCommand(grpName, grpIndex, op, personIdx, indicateByIdx);
        editGrpCmd.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_changeNameByName_success() throws DuplicateGroupException {
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        Group testGroup = testGrps.get(0);
        expectedModel.setGrpName(testGroup, "validName");

        // by group name
        prepareCommand("TestGrp1", null, "gn", "validName", false);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_CHANGE_NAME_SUCCESS, "TestGrp1", "validName"),
                expectedModel);
    }

    @Test
    public void execute_changeNameByIndex_success() throws DuplicateGroupException {
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        Group testGroup = testGrps.get(0);
        expectedModel.setGrpName(testGroup, "validName");

        // by index
        prepareCommand(null, Index.fromOneBased(1), "gn", "validName", true);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_CHANGE_NAME_SUCCESS, "TestGrp1", "validName"),
                expectedModel);
    }


    @Test
    public void execute_addDeletePerson_success() throws DuplicatePersonException, PersonNotFoundException {

        // by group name
        TypicalGroups typicalGroups = new TypicalGroups();
        List<Group> testGrps = typicalGroups.getTypicalGroups();
        Group testGroup = testGrps.get(0);
        ReadOnlyPerson person = typicalPersons.get(4);
        expectedModel.addPersonToGroup(testGroup, person);

        prepareCommand("TestGrp3", null, "add", Index.fromOneBased(5), false);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_ADD_PERSON_SUCCESS, "TestGrp3",
                person.toString()),
                expectedModel);

        // deleting the person that was added
        expectedModel.removePersonFromGroup(testGroup, person);
        prepareCommand("TestGrp3", null, "delete", Index.fromOneBased(4), false);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_DELETE_PERSON_SUCCESS, "TestGrp3",
                person.toString()),
                expectedModel);


        // by index
        expectedModel.addPersonToGroup(testGroup, person);

        prepareCommand(null, Index.fromOneBased(2), "add", Index.fromOneBased(5), true);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_ADD_PERSON_SUCCESS, "TestGrp2",
                person.toString()),
                expectedModel);

        // deleting the person that was added
        expectedModel.removePersonFromGroup(testGroup, person);
        prepareCommand(null, Index.fromOneBased(2), "delete", Index.fromOneBased(3), true);
        assertCommandSuccess(editGrpCmd, model, String.format(MESSAGE_DELETE_PERSON_SUCCESS, "TestGrp2",
                person.toString()),
                expectedModel);
    }


    @Test
    public void execute_groupNonExistent_failure() {
        // change name op
        prepareCommand("nonExistentGroup", null, "gn", "validName", false);
        assertCommandFailure(editGrpCmd, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_GROUP_NONEXISTENT);

        // add / delete op
        prepareCommand("nonExistentGroup", null, "add", Index.fromOneBased(1), false);
        assertCommandFailure(editGrpCmd, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_GROUP_NONEXISTENT);


        prepareCommand(null, Index.fromOneBased(20), "gn", "validName", true);
        assertCommandFailure(editGrpCmd, model, MESSAGE_EXECUTION_FAILURE + MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }


    @Test
    public void execute_addDeleteOutOfRangeIndex_failure() {
        prepareCommand("TestGrp3", null, "add", Index.fromOneBased(20), false);
        // exception header for this exception is null
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        prepareCommand("TestGrp1", null, "delete", Index.fromOneBased(20), false);
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        prepareCommand(null, Index.fromOneBased(1), "add", Index.fromOneBased(20), true);
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addDuplicatePerson_failure() {
        prepareCommand("TestGrp3", null, "add", Index.fromOneBased(1), false);
        assertCommandFailure(editGrpCmd, model,
                MESSAGE_EXECUTION_FAILURE + MESSAGE_DUPLICATE_PERSON);
    }


    @Test
    public void equals() {
        EditGroupCommand editGroupCommand1 = new EditGroupCommand("testName1", null,
                "add", Index.fromOneBased(2), false);
        EditGroupCommand editGroupCommand2 = new EditGroupCommand("testName1", null,
                "add", Index.fromOneBased(2), false);
        EditGroupCommand editGroupCommand3 = new EditGroupCommand("DiffName", null,
                "gn", "AnotherName", false);

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
//@@author
