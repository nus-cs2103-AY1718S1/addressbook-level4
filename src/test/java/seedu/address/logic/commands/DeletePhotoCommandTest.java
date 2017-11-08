package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author JasmineSee

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeletePhotoCommand}.
 */
public class DeletePhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String validFilePath = "./src/test/resources/photos/connectus_icon.png";

    @Test
    public void execute_validIndexPhotoExist_success() throws Exception {
        prepareUploadPhoto(INDEX_FIRST_PERSON, validFilePath);
        ReadOnlyPerson personToDeletePhoto = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePhotoCommand deletePhotoCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePhotoCommand.MESSAGE_DELETE_IMAGE_SUCCESS, personToDeletePhoto);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deletePhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexPhotoExist_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePhotoCommand deletePhotoCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deletePhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexPhotoNotExist_throwsCommandException() throws Exception {
        DeletePhotoCommand deletePhotoCommand = prepareCommand(INDEX_SECOND_PERSON);

        assertCommandFailure(deletePhotoCommand, model, DeletePhotoCommand.MESSAGE_DELETE_IMAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeletePhotoCommand firstCommand = new DeletePhotoCommand(INDEX_FIRST_PERSON);
        DeletePhotoCommand secondCommand = new DeletePhotoCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        DeletePhotoCommand firstCommandCopy = new DeletePhotoCommand(INDEX_FIRST_PERSON);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns a {@code DeletePhotoCommand} with the parameter {@code index}.
     */
    private DeletePhotoCommand prepareCommand(Index index) {
        DeletePhotoCommand deletePhotoCommand = new DeletePhotoCommand(index);
        deletePhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deletePhotoCommand;
    }

    /**
     * Preload photos to test data.
     */
    private void prepareUploadPhoto(Index index, String filePath) throws CommandException {
        UploadPhotoCommand uploadPhotoCommand = new UploadPhotoCommand(index,
                filePath);
        uploadPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        uploadPhotoCommand.execute();
    }

}
