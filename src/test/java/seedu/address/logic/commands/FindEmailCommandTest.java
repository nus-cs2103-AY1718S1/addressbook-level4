//@@author inGall
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EMAILS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEmailCommand}.
 */
public class FindEmailCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void equals() {
        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("first"));
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEmailCommand findFirstEmailCommand = new FindEmailCommand(firstPredicate);
        FindEmailCommand findSecondEmailCommand = new FindEmailCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstEmailCommand.equals(findFirstEmailCommand));

        // same values -> returns true
        FindEmailCommand findFirstEmailCommandCopy = new FindEmailCommand(firstPredicate);
        assertTrue(findFirstEmailCommand.equals(findFirstEmailCommandCopy));

        // different types -> returns false
        assertFalse(findFirstEmailCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstEmailCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstEmailCommand.equals(findSecondEmailCommand));
    }

    @Test
    public void execute_zeroKeywords_noEmailFound() {
        String expectedMessage = String.format(MESSAGE_EMAILS_LISTED_OVERVIEW, 0);
        FindEmailCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleEmailsFound() {
        String expectedMessage = String.format(MESSAGE_EMAILS_LISTED_OVERVIEW, 3);
        FindEmailCommand command = prepareCommand("alice@example.com cornelia@example.com anna@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, DANIEL, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code FindEmailCommand}.
     */
    private FindEmailCommand prepareCommand(String userInput) {
        FindEmailCommand command =
                new FindEmailCommand(new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindEmailCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
