//@@author hthjthtrh
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
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
    public void execute_newGroup_createSuccessful() {
        List<Index> testIdx = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(3));
        testSuccess("newGroup", testIdx);
        testSuccess("##wEird##", testIdx);
    }

    @Test
    public void execute_addExistingGroup_failure() {

        List<Index> testIndexes = new ArrayList<>();
        testIndexes.add(Index.fromOneBased(1));
        testIndexes.add(Index.fromOneBased(3));

        GroupingCommand testCommand = prepareCommand("TestGrp1", testIndexes);

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

    /**
     * helper function to test correctness
     * @param grpName
     * @param testIndexes
     */
    private void testSuccess(String grpName, List<Index> testIndexes) {
        GroupingCommand testCommand = prepareCommand(grpName, testIndexes);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(expectedModel.getAddressBook().getPersonList().get(testIndexes.get(0).getZeroBased()));
        personList.add(expectedModel.getAddressBook().getPersonList().get(testIndexes.get(1).getZeroBased()));

        try {
            expectedModel.createGroup(grpName, personList);
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        }

        String expectedMessage = GroupingCommand.getSb(grpName, personList);

        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }
}
//@@author
