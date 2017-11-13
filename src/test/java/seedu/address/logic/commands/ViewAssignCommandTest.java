package seedu.address.logic.commands;
//@@author Esilocke
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

public class ViewAssignCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyTask taskToShow = model.getFilteredTaskList().get(5);
        ViewAssignCommand viewAssignCommand = prepareCommand(Index.fromZeroBased(5));

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 3);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.viewAssignees(taskToShow);

        assertCommandSuccess(viewAssignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ViewAssignCommand viewAssignCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(viewAssignCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

        ViewAssignCommand viewAssignCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(viewAssignCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewAssignCommand viewFirstCommand = new ViewAssignCommand(INDEX_FIRST_TASK);
        ViewAssignCommand viewSecondCommand = new ViewAssignCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewAssignCommand viewFirstCommandCopy = new ViewAssignCommand(INDEX_FIRST_TASK);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    /**
     * Returns a {@code ViewAssignCommand} with the parameter {@code index}.
     */
    private ViewAssignCommand prepareCommand(Index index) {
        ViewAssignCommand viewAssignCommand = new ViewAssignCommand(index);
        viewAssignCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewAssignCommand;
    }
}
