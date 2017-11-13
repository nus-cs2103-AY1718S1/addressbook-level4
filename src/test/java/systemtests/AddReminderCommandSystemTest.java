package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMINDER_DESC_TIME;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_DATE_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_TIME_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.REMINDER_DESC_TIME_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_DATE_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_TIME_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMINDER_TIME_COFFEE;
import static seedu.address.testutil.TypicalReminders.COFFEE_REMINDER;
import static seedu.address.testutil.TypicalReminders.HOMEWORK_REMINDER;
import static seedu.address.testutil.TypicalReminders.MEETING_REMINDER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.reminders.Date;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.Time;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.testutil.ReminderBuilder;
import seedu.address.testutil.ReminderUtil;

//@@author justinpoh
public class AddReminderCommandSystemTest extends UniqueReminderListSystemTest {

    @Test
    public void addreminder() throws Exception {
        Model model = getModel();

        /* Case: add a reminder, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyReminder toAdd = COFFEE_REMINDER;
        String command = "   " + AddReminderCommand.COMMAND_WORD + "  " + REMINDER_DESC_COFFEE + "  "
                + REMINDER_DESC_DATE_COFFEE + "  " + REMINDER_DESC_TIME_COFFEE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a duplicate reminder -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_COFFEE;
        assertCommandFailure(command, AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);

        /* Case: add a reminder with all fields same as another reminder in the reminder list except reminder -> added */
        toAdd = new ReminderBuilder().withReminder(VALID_REMINDER_ASSIGNMENT).withDate(VALID_REMINDER_DATE_COFFEE)
                .withTime(VALID_REMINDER_TIME_COFFEE).build();
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_COFFEE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with all fields same as another reminder in the reminder list except date -> added */
        toAdd = new ReminderBuilder().withReminder(VALID_REMINDER_COFFEE).withDate(VALID_REMINDER_DATE_ASSIGNMENT)
                .withTime(VALID_REMINDER_TIME_COFFEE).build();
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_ASSIGNMENT
                + REMINDER_DESC_TIME_COFFEE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with all fields same as another reminder in the reminder list except time -> added */
        toAdd = new ReminderBuilder().withReminder(VALID_REMINDER_COFFEE).withDate(VALID_REMINDER_DATE_COFFEE)
                .withTime(VALID_REMINDER_TIME_ASSIGNMENT).build();
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty reminder list -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandSuccess(MEETING_REMINDER);

        /* Case: missing reminder -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_DATE_ASSIGNMENT + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_DATE_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addreminders " + ReminderUtil.getReminderDetails(HOMEWORK_REMINDER);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid reminder -> rejected */
        command = AddReminderCommand.COMMAND_WORD + INVALID_REMINDER_DESC + REMINDER_DESC_DATE_ASSIGNMENT
                + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        /* Case: invalid date -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + INVALID_REMINDER_DESC_DATE
                + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_DATE_ASSIGNMENT
                + INVALID_REMINDER_DESC_TIME;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS);

    }

    /**
     * Executes the {@code AddReminderCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing
     * {@code AddReminderCommand} with the details of {@code toAdd}, and the model related components equal to the
     * current model added with {@code toAdd}. These verifications are done by
     * {@code UniqueReminderListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class.
     * @see UniqueReminderListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyReminder toAdd) {
        assertCommandSuccess(ReminderUtil.getAddReminderCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyReminder)}. Executes {@code command}
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
        String expectedResultMessage = String.format(AddReminderCommand.MESSAGE_SUCCESS, toAdd);


        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyReminder)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddReminderCommandSystemTest#assertCommandSuccess(String, ReadOnlyReminder)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code UniqueReminderListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remain unchanged, and the command box has the
     * error style.
     * @see UniqueReminderListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }
}
