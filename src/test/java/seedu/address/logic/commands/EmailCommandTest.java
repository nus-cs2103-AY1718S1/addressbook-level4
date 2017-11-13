package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EmailCommand.MESSAGE_NOT_SENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

//@@author sidhmads
/**
 * Contains integration tests (interaction with the Model) for {@code EmailCommand}.
 */
public class EmailCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        EmailCommand findFirstCommand = new EmailCommand(firstPredicate, "", "");
        EmailCommand findSecondCommand = new EmailCommand(secondPredicate, "", "");

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        EmailCommand findFirstCommandCopy = new EmailCommand(firstPredicate, "", "");
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_NOT_SENT);
        EmailCommand command = prepareCommand(" ");
        assertCommandSuccess(command, model, expectedMessage, model);
    }
    /**
     * The execution of EmailCommand makes the Travis CI fail.
     * Travis CI is unable to open the desktop mail application.
     * When running locally, use the below test. Comment it otherwise.
     */
    //    @Test
    //    public void execute_multipleKeywords_multiplePersonsFound() {
    //        String expectedMessage = String.format(MESSAGE_EMAIL_APP);
    //        EmailCommand command = prepareCommand("email to/Kurz Elle Kunz");
    //        assertCommandSuccess(command, model, expectedMessage, model);
    //    }

    /**
     * Parses {@code userInput} into a {@code EmailCommand}.
     */
    private EmailCommand prepareCommand(String userInput) {
        EmailCommand command =
                new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))), "", "");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
