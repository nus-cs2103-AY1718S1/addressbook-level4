package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminders.ReadOnlyReminder;

//@@author justinpoh
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteReminderCommand}.
 */
public class DeleteReminderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_validIndexSortedReminderList_success() throws Exception {
        ReadOnlyReminder reminderToDelete = model.getSortedReminderList().get(INDEX_FIRST_PERSON.getZeroBased());

        DeleteReminderCommand deleteReminderCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getUniqueReminderList(),
                new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(deleteReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexSortedReminderList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedReminderList().size() + 1);
        DeleteReminderCommand deleteReminderCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteReminderCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteReminderCommand deleteFirstReminderCommand = new DeleteReminderCommand(INDEX_FIRST_PERSON);
        DeleteReminderCommand deleteSecondReminderCommand = new DeleteReminderCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstReminderCommand.equals(deleteFirstReminderCommand));

        // same values -> returns true
        DeleteReminderCommand deleteFirstReminderCommandCopy = new DeleteReminderCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstReminderCommand.equals(deleteFirstReminderCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstReminderCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstReminderCommand.equals(null));

        // different reminder -> returns false
        assertFalse(deleteFirstReminderCommand.equals(deleteSecondReminderCommand));
    }

    /**
     * Returns a {@code DeleteReminderCommand} with the parameter {@code index}.
     */
    private DeleteReminderCommand prepareCommand(Index index) {
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(index);
        deleteReminderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteReminderCommand;
    }
}
