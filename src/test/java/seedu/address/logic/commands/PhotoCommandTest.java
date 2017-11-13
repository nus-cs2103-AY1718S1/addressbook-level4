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
//@@author RonakLakhotia
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code PhotoCommand}.
 */
public class PhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Out Of Bounds Exception Testing
     */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String FilePath = "/Users/ronaklakhotia/Desktop/Ronak.jpeg";
        PhotoCommand photoCommand = prepareCommand(outOfBoundIndex, FilePath);

        assertCommandFailure(photoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * No image to delete Exception Testing
     */
    @Test
    public void deleteFaliure() {
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());
        String filePath = "delete";
        PhotoCommand photoCommand = prepareCommand(index, filePath);
        assertCommandFailure(photoCommand, model, Messages.MESSAGE_NO_IMAGE_TO_DELETE);
    }

    /**
     * Returns a {@code PhotoCommand} with the parameter {@code index and Filepath}.
     */
    private PhotoCommand prepareCommand(Index index, String FilePath) {

        PhotoCommand photoCommand = new PhotoCommand(index, FilePath);
        photoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return photoCommand;
    }
    @Test
    public void equals() {
        PhotoCommand photoCommand = new PhotoCommand(INDEX_FIRST_PERSON,
                "/Users/ronaklakhotia/Desktop/Ronak.jpeg");

        PhotoCommand photoSecondCommand = new PhotoCommand(INDEX_SECOND_PERSON,
                "/Users/ronaklakhotia/Desktop/Ronak.jpeg");

        // same object -> returns true
        assertTrue(photoCommand.equals(photoCommand));

        // same values -> returns true
        PhotoCommand photoFirstCommandCopy = new PhotoCommand(INDEX_FIRST_PERSON,
                "/Users/ronaklakhotia/Desktop/Ronak.jpeg");

        assertTrue(photoCommand.equals(photoFirstCommandCopy));

        // different types -> returns false
        assertFalse(photoCommand.equals(1));

        // null -> returns false
        assertFalse(photoCommand.equals(null));

        // different person -> returns false
        assertFalse(photoCommand.equals(photoSecondCommand));
    }

}
