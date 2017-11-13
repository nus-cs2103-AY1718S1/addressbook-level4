package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstReminderOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReadOnlyReminder;
//@@author RonakLakhotia
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveReminderCommand}.
 */
public class RemoveReminderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyReminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        RemoveReminderCommand removeCommand = prepareCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(RemoveReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        RemoveReminderCommand removeCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstReminderOnly(model);

        ReadOnlyReminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        RemoveReminderCommand removeCommand = prepareCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(RemoveReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);
        showNoReminder(expectedModel);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstReminderOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_REMINDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReminderList().size());

        RemoveReminderCommand removeCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RemoveReminderCommand removeFirstCommand = new RemoveReminderCommand(INDEX_FIRST_REMINDER);
        RemoveReminderCommand removeSecondCommand = new RemoveReminderCommand(INDEX_SECOND_REMINDER);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveReminderCommand removeFirstCommandCopy = new RemoveReminderCommand(INDEX_FIRST_REMINDER);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * Returns a {@code ReomveCommand} with the parameter {@code index}.
     */
    private RemoveReminderCommand prepareCommand(Index index) {
        RemoveReminderCommand removeCommand = new RemoveReminderCommand(index);
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoReminder(Model model) {
        model.updateFilteredReminderList(p -> false);

        assert model.getFilteredReminderList().isEmpty();
    }

}
