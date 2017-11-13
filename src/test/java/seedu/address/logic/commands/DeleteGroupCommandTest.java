package seedu.address.logic.commands;


//@@author eldonng
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
