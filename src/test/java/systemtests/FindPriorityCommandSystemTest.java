//@@author inGall
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PRIORITY_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalReminders.BIRTHDAY;
import static seedu.address.testutil.TypicalReminders.DATING;
import static seedu.address.testutil.TypicalReminders.GATHERING;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindPriorityCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindPriorityCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {

        Model expectedModel = getModel();

        /* Case: find priority where reminder list is not displaying the reminder we are finding -> 1 reminder found */
        String command = FindPriorityCommand.COMMAND_WORD + " Low";
        ModelReminderHelper.setFilteredReminderList(expectedModel, DATING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where reminder list is displaying the reminders we are finding
         * -> 2 reminders found
         */
        command = FindPriorityCommand.COMMAND_WORD + " Low";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find multiple priorities in address book, 2 keywords -> 2 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Low Medium";
        ModelReminderHelper.setFilteredReminderList(expectedModel, BIRTHDAY, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple priorities in address book, 2 keywords in reversed order -> 2 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Medium Low";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple priorities in address book, 2 keywords with 1 repeat -> 2 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Medium Low Medium";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple priorities in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 reminders found
         */
        command = FindPriorityCommand.COMMAND_WORD + " Medium Low NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find priority in address book, keyword is substring of name -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Med";
        ModelReminderHelper.setFilteredReminderList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority in address book, name is substring of keyword -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Lows";
        ModelReminderHelper.setFilteredReminderList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority not in address book -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Average";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of reminder in address book -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " " + GATHERING.getTask().taskName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find date of reminder in address book -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " " + GATHERING.getDate().date;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find message of reminder in address book -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " " + GATHERING.getMessage().message;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of reminder in address book -> 0 reminders found */
        List<Tag> tags = new ArrayList<>(GATHERING.getTags());
        command = FindPriorityCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority in empty address book -> 0 reminders found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getReminderList().size() == 0;
        command = FindPriorityCommand.COMMAND_WORD + " Medium";
        expectedModel = getModel();
        ModelReminderHelper.setFilteredReminderList(expectedModel, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdpRIORITY Low";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PRIORITY_LISTED_OVERVIEW} with the number of reminders in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PRIORITY_LISTED_OVERVIEW, expectedModel.getFilteredReminderList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
