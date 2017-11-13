package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.DETAILS_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DUE_DATE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DETAILS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUE_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeReminderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.testutil.ReminderBuilder;
//@@author RonakLakhotia
public class ChangeReminderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown
 ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_REMINDER;
        String command = " " + ChangeReminderCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + DETAILS_DESC_ASSIGNMENT + "  " + PRIORITY_DESC_ASSIGNMENT + " " + DUE_DATE_DESC_ASSIGNMENT;

        Reminder editedReminder = new ReminderBuilder().withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority("Priority Level: " + VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();
        assertCommandSuccess(command, index, editedReminder);

        /* Case: undo editing the last reminder in the list -> last reminder restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last reminder in the list -> last reminder edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateReminder(
                getModel().getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased()), editedReminder);
        assertCommandSuccess(command, model, expectedResultMessage);


        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_REMINDER;
        command = ChangeReminderCommand.COMMAND_WORD + " " + index.getOneBased() + DETAILS_DESC_ASSIGNMENT;
        ReadOnlyReminder reminderToEdit = getModel().getFilteredReminderList().get(index.getZeroBased());
        editedReminder = new ReminderBuilder(reminderToEdit).withDetails(VALID_DETAILS_ASSIGNMENT).build();
        assertCommandSuccess(command, index, editedReminder);


        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " 0" + DETAILS_DESC_ASSIGNMENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ChangeReminderCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " -1" + DETAILS_DESC_ASSIGNMENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ChangeReminderCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */

        int invalidIndex = getModel().getFilteredReminderList().size() + 1;
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + invalidIndex
                        + DETAILS_DESC_ASSIGNMENT,
                Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ChangeReminderCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased(),
                ChangeReminderCommand.MESSAGE_NOT_CHANGED);

        /* Case: invalid details -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased()
                        + INVALID_DETAILS_DESC,
                ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);

        /* Case: invalid priority -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased()
                        + INVALID_PRIORITY_DESC,
                Priority.PRIORITY_CONSTRAINTS);

        /* Case: invalid duedate -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased()
                        + INVALID_DUE_DATE_DESC,
                DueDate.MESSAGE_DATE_CONSTRAINTS);


    }
    //@@author

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyReminder, Index)} except
     that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see ChangeReminderCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyReminder, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyReminder editedReminder) {
        assertCommandSuccess(command, toEdit, editedReminder, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,
     <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see ChangeReminderCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyReminder editedReminder,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateReminder(
                    expectedModel.getFilteredReminderList().get(toEdit.getZeroBased()), editedReminder);
            expectedModel.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        } catch (DuplicateReminderException | ReminderNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedReminder is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(ChangeReminderCommand
                        .MESSAGE_CHANGE_REMINDER_SUCCESS, editedReminder), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see ChangeReminderCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
