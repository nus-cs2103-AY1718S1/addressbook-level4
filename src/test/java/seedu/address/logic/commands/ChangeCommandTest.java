package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUE_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
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
import seedu.address.logic.commands.ChangeReminderCommand.ChangeReminderDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ChangeReminderDescriptorBuilder;
import seedu.address.testutil.ReminderBuilder;
//@@author RonakLakhotia
/**
 * Contains integration tests (interaction with the Model) and unit tests for ChangeReminderCommand.
 */
public class ChangeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Reminder changedReminder = new ReminderBuilder().build();
        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder(changedReminder).build();
        ChangeReminderCommand changeCommand = prepareCommand(INDEX_FIRST_REMINDER, descriptor);

        String expectedMessage = String.format(ChangeReminderCommand.MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReminder(model.getFilteredReminderList().get(0), changedReminder);

        assertCommandSuccess(changeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastReminder = Index.fromOneBased(model.getFilteredReminderList().size());
        ReadOnlyReminder lastReminder = model.getFilteredReminderList().get(indexLastReminder.getZeroBased());

        ReminderBuilder reminderInList = new ReminderBuilder(lastReminder);
        Reminder changedReminder = reminderInList.withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority("Priority Level: " + VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();


        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();
        ChangeReminderCommand changeCommand = prepareCommand(indexLastReminder, descriptor);

        String expectedMessage = String.format(ChangeReminderCommand.MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReminder(lastReminder, changedReminder);

        assertCommandSuccess(changeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ChangeReminderCommand changeCommand = prepareCommand(INDEX_FIRST_REMINDER, new ChangeReminderDescriptor());
        ReadOnlyReminder changedReminder = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());

        String expectedMessage = String.format(ChangeReminderCommand.MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(changeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstReminderOnly(model);

        ReadOnlyReminder reminderInFilteredList = model.getFilteredReminderList()
                .get(INDEX_FIRST_REMINDER.getZeroBased());

        Reminder changedReminder = new ReminderBuilder(reminderInFilteredList)
                .withDetails(VALID_DETAILS_ASSIGNMENT).build();
        ChangeReminderCommand changeCommand = prepareCommand(INDEX_FIRST_REMINDER,
                new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_ASSIGNMENT).build());

        String expectedMessage = String.format(ChangeReminderCommand.MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReminder(model.getFilteredReminderList().get(0), changedReminder);

        assertCommandSuccess(changeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReminderUnfilteredList_failure() {
        Reminder firstReminder = new Reminder(model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased()));
        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder(firstReminder).build();
        ChangeReminderCommand changeCommand = prepareCommand(INDEX_SECOND_REMINDER, descriptor);

        assertCommandFailure(changeCommand, model, ChangeReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_duplicateReminderFilteredList_failure() {
        showFirstReminderOnly(model);

        // edit reminder in filtered list into a duplicate in address book
        ReadOnlyReminder reminderInList = model.getAddressBook().getReminderList()
                .get(INDEX_SECOND_REMINDER.getZeroBased());

        ChangeReminderCommand changeCommand = prepareCommand(INDEX_FIRST_REMINDER,
                new ChangeReminderDescriptorBuilder(reminderInList).build());

        assertCommandFailure(changeCommand, model, ChangeReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_invalidReminderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_MEETING)
                .build();
        ChangeReminderCommand changeCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(changeCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    /**
     * Change filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidReminderIndexFilteredList_failure() {
        showFirstReminderOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_REMINDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ChangeReminderCommand changeCommand = prepareCommand(outOfBoundIndex,
                new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_MEETING).build());

        assertCommandFailure(changeCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ChangeReminderCommand standardCommand = new ChangeReminderCommand(INDEX_FIRST_REMINDER, DESC_ASSIGNMENT);

        // same values -> returns true
        ChangeReminderDescriptor copyDescriptor = new ChangeReminderDescriptor(DESC_ASSIGNMENT);
        ChangeReminderCommand commandWithSameValues = new ChangeReminderCommand(INDEX_FIRST_REMINDER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ChangeReminderCommand(INDEX_SECOND_REMINDER, DESC_ASSIGNMENT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ChangeReminderCommand(INDEX_FIRST_REMINDER, DESC_MEETING)));
    }

    /**
     * Returns an {@code ChangeCommand} with parameters {@code index} and {@code descriptor}
     */
    private ChangeReminderCommand prepareCommand(Index index, ChangeReminderDescriptor descriptor) {
        ChangeReminderCommand changeCommand = new ChangeReminderCommand(index, descriptor);
        changeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeCommand;
    }
}
