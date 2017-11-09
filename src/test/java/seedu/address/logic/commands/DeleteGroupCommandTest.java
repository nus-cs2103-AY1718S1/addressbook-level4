//@@author hthjthtrh
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
        deleteGroupCommand = new DeleteGroupCommand(grpName, false);
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
        DeleteGroupCommand deleteGroupCommand1 = new DeleteGroupCommand("HOT", false);
        DeleteGroupCommand deleteGroupCommand2 = new DeleteGroupCommand("HOT", false);
        DeleteGroupCommand deleteGroupCommand3 = new DeleteGroupCommand("Not_So_Hot", false);

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
//@@author
