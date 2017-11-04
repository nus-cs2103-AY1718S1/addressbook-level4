//@@author inGall
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PRIORITY_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
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
import seedu.address.model.reminder.PriorityContainsKeywordsPredicate;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPriorityCommand}.
 */
public class FindPriorityCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void equals() {
        PriorityContainsKeywordsPredicate firstPredicate =
                new PriorityContainsKeywordsPredicate(Collections.singletonList("first"));
        PriorityContainsKeywordsPredicate secondPredicate =
                new PriorityContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPriorityCommand findFirstPriorityCommand = new FindPriorityCommand(firstPredicate);
        FindPriorityCommand findSecondPriorityCommand = new FindPriorityCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstPriorityCommand.equals(findFirstPriorityCommand));

        // same values -> returns true
        FindPriorityCommand findFirstPriorityCommandCopy = new FindPriorityCommand(firstPredicate);
        assertTrue(findFirstPriorityCommand.equals(findFirstPriorityCommandCopy));

        // different types -> returns false
        assertFalse(findFirstPriorityCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstPriorityCommand.equals(null));

        // different reminder -> returns false
        assertFalse(findFirstPriorityCommand.equals(findSecondPriorityCommand));
    }

    @Test
    public void execute_zeroKeywords_noPriorityFound() {
        String expectedMessage = String.format(MESSAGE_PRIORITY_LISTED_OVERVIEW, 0);
        FindPriorityCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code FindPriorityCommand}.
     */
    private FindPriorityCommand prepareCommand(String userInput) {
        FindPriorityCommand command =
                new FindPriorityCommand(new PriorityContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyReminder>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPriorityCommand command, String expectedMessage,
                                      List<ReadOnlyReminder> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredReminderList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
