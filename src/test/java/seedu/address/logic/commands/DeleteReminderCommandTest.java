//@@author duyson98

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstReminderOnly;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static seedu.address.testutil.TypicalReminders.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteReminderCommand}.
 */
public class DeleteReminderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyReminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        DeleteReminderCommand command = prepareCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        DeleteReminderCommand command = prepareCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstReminderOnly(model);

        ReadOnlyReminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        DeleteReminderCommand command = prepareCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);
        showNoReminder(expectedModel);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstReminderOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_REMINDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReminderList().size());

        DeleteReminderCommand command = prepareCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteReminderCommand command = new DeleteReminderCommand(INDEX_FIRST_REMINDER);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        DeleteReminderCommand commandCopy = new DeleteReminderCommand(INDEX_FIRST_REMINDER);
        assertTrue(command.equals(commandCopy));

        // different types -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(command.equals(null));

        // different reminder -> returns false
        DeleteReminderCommand anotherCommand = new DeleteReminderCommand(INDEX_SECOND_REMINDER);
        assertFalse(command.equals(anotherCommand));
    }

    /**
     * Returns a {@code DeleteReminderCommand} with the parameter {@code index}.
     */
    private DeleteReminderCommand prepareCommand(Index index) {
        DeleteReminderCommand command = new DeleteReminderCommand(index);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Updates {@code model}'s filtered list to show no reminder.
     */
    private void showNoReminder(Model model) {
        model.updateFilteredReminderList(r -> false);

        assert model.getFilteredReminderList().isEmpty();
    }
}
