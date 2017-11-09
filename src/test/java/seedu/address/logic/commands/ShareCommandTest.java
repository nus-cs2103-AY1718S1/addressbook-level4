package seedu.address.logic.commands;

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
import seedu.address.logic.UndoRedoStack;
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
        ShareCommand shareCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(shareCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {

        ShareCommand emailFirstCommand = new ShareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        ShareCommand emailSecondCommand = new ShareCommand(INDEX_SECOND_PERSON, shareEmailArray);

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        ShareCommand emailFirstCommandCopy = new ShareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        assertTrue(emailFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }


    /**
     * Returns a {@code ShareCommand} with the parameter {@code index}.
     */
    private ShareCommand prepareCommand(Index index) {
        ShareCommand ShareCommand = new ShareCommand(index, shareEmailArray);
        ShareCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ShareCommand;
    }
}
