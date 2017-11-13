//@@author inGall
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_REMINDERS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalReminders.BIRTHDAY;
import static seedu.address.testutil.TypicalReminders.GATHERING;
import static seedu.address.testutil.TypicalReminders.MEETING;
import static seedu.address.testutil.TypicalReminders.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.TaskContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindReminderCommand}.
 */
public class FindReminderCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void equals() {
        TaskContainsKeywordsPredicate firstPredicate =
                new TaskContainsKeywordsPredicate(Collections.singletonList("first"));
        TaskContainsKeywordsPredicate secondPredicate =
                new TaskContainsKeywordsPredicate(Collections.singletonList("second"));

        FindReminderCommand findFirstReminderCommand = new FindReminderCommand(firstPredicate);
        FindReminderCommand findSecondReminderCommand = new FindReminderCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstReminderCommand.equals(findFirstReminderCommand));

        // same values -> returns true
        FindReminderCommand findFirstReminderCommandCopy = new FindReminderCommand(firstPredicate);
        assertTrue(findFirstReminderCommand.equals(findFirstReminderCommandCopy));

        // different types -> returns false
        assertFalse(findFirstReminderCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstReminderCommand.equals(null));

        // different reminder -> returns false
        assertFalse(findFirstReminderCommand.equals(findSecondReminderCommand));
    }

    @Test
    public void execute_zeroKeywords_noReminderFound() {
        String expectedMessage = String.format(MESSAGE_REMINDERS_LISTED_OVERVIEW, 0);
        FindReminderCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleRemindersFound() {
        String expectedMessage = String.format(MESSAGE_REMINDERS_LISTED_OVERVIEW, 3);
        FindReminderCommand command = prepareCommand("birthday Gathering Meeting");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BIRTHDAY, GATHERING, MEETING));
    }

    /**
     * Parses {@code userInput} into a {@code FindReminderCommand}.
     */
    private FindReminderCommand prepareCommand(String userInput) {
        FindReminderCommand command =
                new FindReminderCommand(new TaskContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyReminder>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindReminderCommand command, String expectedMessage,
                                      List<ReadOnlyReminder> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredReminderList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
