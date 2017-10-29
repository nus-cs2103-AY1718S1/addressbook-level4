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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UploadPhotoCommand}.
 */
public class UploadPhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws Exception {
        ReadOnlyPerson personToUploadPhoto = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON,
                ".\\src\\test\\resources\\photos\\connectus_icon.png");

        String expectedMessage = String.format(UploadPhotoCommand.MESSAGE_UPLOAD_IMAGE_SUCCESS, personToUploadPhoto);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(uploadPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexValidFile_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(outOfBoundIndex,
                ".\\src\\test\\resources\\photos\\connectus_icon.png");

        assertCommandFailure(uploadPhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexInvalidFile_throwsCommandException() throws Exception {
        ReadOnlyPerson personToUploadPhoto = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON,
                ".\\src\\test\\resources\\photos\\default.jpeg");

        assertCommandFailure(uploadPhotoCommand, model, UploadPhotoCommand.MESSAGE_UPLOAD_IMAGE_FALURE);
    }

    @Test
    public void equals() {
        UploadPhotoCommand firstCommand = new UploadPhotoCommand(INDEX_FIRST_PERSON, "");
        UploadPhotoCommand secondCommand = new UploadPhotoCommand(INDEX_SECOND_PERSON, "");

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        UploadPhotoCommand firstCommandCopy = new UploadPhotoCommand(INDEX_FIRST_PERSON, "");
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns a {@code UploadPhotoCommand} with the parameter {@code index}.
     */
    private UploadPhotoCommand prepareCommand(Index index, String filePath) {
        UploadPhotoCommand uploadPhotoCommand = new UploadPhotoCommand(index, filePath);
        uploadPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return uploadPhotoCommand;
    }

}
