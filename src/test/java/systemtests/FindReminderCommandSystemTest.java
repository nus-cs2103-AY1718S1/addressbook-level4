//@@author inGall
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_REMINDERS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalReminders.BIRTHDAY;
import static seedu.address.testutil.TypicalReminders.DATING;
import static seedu.address.testutil.TypicalReminders.GATHERING;
import static seedu.address.testutil.TypicalReminders.KEYWORD_MATCHING_GROUP;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindReminderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindReminderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {

        Model expectedModel = getModel();

        /* Case: find reminder where reminder list is not displaying the reminder we are finding -> 1 reminder found */
        String command = FindReminderCommand.COMMAND_WORD + " Dating";
        ModelReminderHelper.setFilteredReminderList(expectedModel, DATING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where reminder list is displaying the reminders we are finding
         * -> 2 reminders found
         */
        command = FindReminderCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GROUP;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple reminders in address book, 2 keywords -> 2 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " birthday Gathering";
        ModelReminderHelper.setFilteredReminderList(expectedModel, BIRTHDAY, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple reminders in address book, 2 keywords in reversed order -> 2 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Gathering birthday";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple reminders in address book, 2 keywords with 1 repeat -> 2 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Gathering birthday Gathering";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple reminders in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 reminders found
         */
        command = FindReminderCommand.COMMAND_WORD + " Gathering birthday NonMatchingKeyWord";
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

        /* Case: find same reminders in address book after deleting 1 of them -> 1 reminder found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getReminderList().contains(BIRTHDAY);
        command = FindReminderCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GROUP;
        expectedModel = getModel();
        ModelReminderHelper.setFilteredReminderList(expectedModel, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder in address book, keyword is same as name but of different case -> 1 reminder found */
        command = FindReminderCommand.COMMAND_WORD + " GrOuP";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder in address book, keyword is substring of name -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Gro";
        ModelReminderHelper.setFilteredReminderList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder in address book, name is substring of keyword -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Groups";
        ModelReminderHelper.setFilteredReminderList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder not in address book -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Fishing";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority of reminder in address book -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " " + GATHERING.getPriority().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find date of reminder in address book -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " " + GATHERING.getDate().date;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find message of reminder in address book -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " " + GATHERING.getMessage().message;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of reminder in address book -> 0 reminders found */
        List<Tag> tags = new ArrayList<>(GATHERING.getTags());
        command = FindReminderCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder in empty address book -> 0 reminders found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getReminderList().size() == 0;
        command = FindReminderCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GROUP;
        expectedModel = getModel();
        ModelReminderHelper.setFilteredReminderList(expectedModel, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdRemiNdeR Group";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_REMINDER_LISTED_OVERVIEW} with the number of reminders in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_REMINDERS_LISTED_OVERVIEW, expectedModel.getFilteredReminderList().size());

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
