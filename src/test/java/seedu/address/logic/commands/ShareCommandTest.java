package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.InternetConnectionCheck;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author hanselblack
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ShareCommand}.
 */
public class ShareCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String[] shareEmailArray = {"unifycs2103@gmail.com"};

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ShareCommand shareCommand = prepareCommand(outOfBoundIndex, shareEmailArray);
        assertCommandFailure(shareCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexRecipient() throws Exception {

        shareEmailArray = new String[]{"unifycs2103@gmail.com", "-1"};
        ShareCommand shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_FAILURE, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_share_success() {

        ShareCommand shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        shareCommand = prepareCommand(INDEX_SECOND_PERSON, shareEmailArray);
        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        shareEmailArray = new String[]{"unifycs2103@gmail.com", "1"};
        shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_share_invalidEmail() {

        shareEmailArray = new String[]{"wrongemailformat"};
        ShareCommand shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);

        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_EMAIL_NOT_VALID, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        shareEmailArray = new String[]{"unifycs2103@gmail.com", "wrongemailformat"};
        shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);

        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_EMAIL_NOT_VALID, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void equals() {

        ShareCommand shareFirstCommand = new ShareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        ShareCommand shareSecondCommand = new ShareCommand(INDEX_SECOND_PERSON, shareEmailArray);

        // same object -> returns true
        assertTrue(shareFirstCommand.equals(shareFirstCommand));

        // same values -> returns true
        ShareCommand emailFirstCommandCopy = new ShareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        assertTrue(shareFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(shareFirstCommand.equals(1));

        // null -> returns false
        assertFalse(shareFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(shareFirstCommand.equals(shareSecondCommand));
    }


    /**
     * Returns a {@code ShareCommand} with the parameter {@code index}.
     */
    private ShareCommand prepareCommand(Index index, String[] shareEmailArray) {
        ShareCommand shareCommand = new ShareCommand(index, shareEmailArray);
        shareCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return shareCommand;
    }
}
