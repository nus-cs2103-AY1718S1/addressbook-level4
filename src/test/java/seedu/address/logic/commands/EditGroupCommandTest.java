//@@author hthjthtrh
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
//@@author
