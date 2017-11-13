package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DATE_ASSIGNMENT;
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
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.Reminder;
import seedu.address.testutil.EditReminderDescriptorBuilder;
import seedu.address.testutil.ReminderBuilder;

//@@author justinpoh
public class EditReminderCommandTest {


    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        Reminder editedReminder = new ReminderBuilder().build();
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(editedReminder).build();
        EditReminderCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updateReminder(model.getSortedReminderList().get(0), editedReminder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws Exception {
        // time field not changed
        Index indexLastReminder = Index.fromOneBased(model.getSortedReminderList().size());
        ReadOnlyReminder lastReminder = model.getSortedReminderList().get(indexLastReminder.getZeroBased());

        ReminderBuilder reminderInList = new ReminderBuilder(lastReminder);
        Reminder editedReminder = reminderInList.withReminder(VALID_REMINDER_ASSIGNMENT)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).build();

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_ASSIGNMENT)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).build();
        EditReminderCommand editReminderCommand = prepareCommand(indexLastReminder, descriptor);

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updateReminder(lastReminder, editedReminder);

        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_noFieldSpecified_success() throws Exception {
        EditReminderCommand editReminderCommand = prepareCommand(INDEX_FIRST_PERSON, new EditReminderDescriptor());
        ReadOnlyReminder editedReminder = model.getSortedReminderList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());

        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReminder_failure() throws Exception {
        Reminder firstReminder = new Reminder(model.getSortedReminderList().get(INDEX_FIRST_PERSON.getZeroBased()));
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(firstReminder).build();
        EditReminderCommand editReminderCommand = prepareCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editReminderCommand, model, EditReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_invalidReminderIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedReminderList().size() + 1);
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withReminder(VALID_REMINDER_ASSIGNMENT).build();
        EditReminderCommand editReminderCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editReminderCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditReminderCommand standardCommand = new EditReminderCommand(INDEX_FIRST_PERSON, REMINDER_COFFEE);

        // same values -> returns true
        EditReminderDescriptor copyDescriptor = new EditReminderDescriptor(REMINDER_COFFEE);
        EditReminderCommand commandWithSameValues = new EditReminderCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditReminderCommand(INDEX_SECOND_PERSON, REMINDER_COFFEE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditReminderCommand(INDEX_FIRST_PERSON, REMINDER_ASSIGNMENT)));
    }

    /**
     * Returns an {@code EditReminderCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditReminderCommand prepareCommand(Index index, EditReminderDescriptor descriptor) {
        EditReminderCommand editReminderCommand = new EditReminderCommand(index, descriptor);
        editReminderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editReminderCommand;
    }
}
