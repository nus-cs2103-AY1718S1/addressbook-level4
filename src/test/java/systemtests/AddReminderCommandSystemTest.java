package systemtests;
//@@author RonakLakhotia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.DETAILS_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DETAILS_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.DUE_DATE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DUE_DATE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DETAILS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUE_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.testutil.TypicalPersons.TUTORIAL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddReminder;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.testutil.ReminderBuilder;
import seedu.address.testutil.ReminderUtil;

public class AddReminderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a reminder command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyReminder toAdd = TUTORIAL;
        String command = "   " + AddReminder.COMMAND_WORD + "  " + DETAILS_DESC_TUTORIAL + " "
                + PRIORITY_DESC_TUTORIAL + " " + DUE_DATE_DESC_TUTORIAL;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding TUTORIAL to the list -> SUCCESS*/
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding to the list -> SUCCESS */
        command = RedoCommand.COMMAND_WORD;
        model.addReminder(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);


        /* Case: add a duplicate reminder -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL
                + DUE_DATE_DESC_TUTORIAL;
        assertCommandFailure(command, AddReminder.MESSAGE_DUPLICATE_REMINDER);

        /* Case: add a reminder with all fields same as another reminder in the address book except priority -> added */

        toAdd = new ReminderBuilder().withDetails(VALID_DETAILS_TUTORIAL)
                .withPriority("Priority Level: " + VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_TUTORIAL).build();

        command = AddReminder.COMMAND_WORD + DETAILS_DESC_TUTORIAL + PRIORITY_DESC_ASSIGNMENT
                + DUE_DATE_DESC_TUTORIAL;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with all fields same as another reminder in the address book except details -> added */
        toAdd = new ReminderBuilder().withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority("Priority Level: " + VALID_PRIORITY_TUTORIAL)
                .withDueDate(VALID_DUE_DATE_TUTORIAL).build();

        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_TUTORIAL
                + DUE_DATE_DESC_TUTORIAL;
        assertCommandSuccess(command, toAdd);


        /* Case: missing duedate -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE));

        /* Case: missing priority -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + DUE_DATE_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE));

        /* Case: missing details -> rejected */
        command = AddReminder.COMMAND_WORD  + PRIORITY_DESC_ASSIGNMENT + DUE_DATE_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "reminders " + ReminderUtil.getReminderDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid details -> rejected */
        command = AddReminder.COMMAND_WORD + INVALID_DETAILS_DESC + PRIORITY_DESC_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT;
        assertCommandFailure(command, ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);

        /* Case: invalid priority -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + INVALID_PRIORITY_DESC
                + DUE_DATE_DESC_ASSIGNMENT;

        assertCommandFailure(command, Priority.PRIORITY_CONSTRAINTS);

        /* Case: invalid duedate -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                + INVALID_DUE_DATE_DESC;
        assertCommandFailure(command, DueDate.MESSAGE_DATE_CONSTRAINTS);


    }
    //@@author
    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code AddReminderCommand}
     * with the
     * details of {@code toAdd}, and the model related components equal to the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyReminder toAdd) {
        assertCommandSuccess(ReminderUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)}. Executes {@code command}
     * instead.
     * @see AddReminderCommandSystemTest#assertCommandSuccess(ReadOnlyReminder)
     */
    private void assertCommandSuccess(String command, ReadOnlyReminder toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addReminder(toAdd);
        } catch (DuplicateReminderException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddReminder.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddReminderCommandSystemTest#assertCommandSuccess(String, ReadOnlyReminder)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
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
