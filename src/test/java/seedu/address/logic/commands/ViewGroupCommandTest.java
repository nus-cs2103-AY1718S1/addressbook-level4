//@@author hthjthtrh
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewGroupCommand.MESSAGE_GROUPING_PERSON_SUCCESS;
import static seedu.address.logic.commands.ViewGroupCommand.MESSAGE_GROUP_NONEXISTENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
        TypicalGroups typicalGroups = new TypicalGroups();
        Group testGroup = typicalGroups.getTypicalGroups().get(2);

        ViewGroupCommand viewGroupCommand = prepareCommand("TestGrp3", null);
        String expectedMessage = String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                testGroup.getPersonList().size(), testGroup.getGrpName());
        assertCommandSuccess(viewGroupCommand, model, expectedMessage, expectedModel);
    }



    @Test
    public void execute_grpIndexGrpExist_success() {
        TypicalGroups typicalGroups = new TypicalGroups();
        Group testGroup = typicalGroups.getTypicalGroups().get(2);

        ViewGroupCommand viewGroupCommand = prepareCommand(null, Index.fromOneBased(3));
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
//@@author
