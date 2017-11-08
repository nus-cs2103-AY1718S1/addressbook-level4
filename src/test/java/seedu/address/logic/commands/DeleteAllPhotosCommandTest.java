package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author JasmineSee

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteAllPhotosCommand}.
 */
public class DeleteAllPhotosCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() throws Exception {
        prepareUploadPhotos();

        DeleteAllPhotosCommand deletePhotoCommand = prepareCommand();

        String expectedMessage = String.format(DeleteAllPhotosCommand.MESSAGE_DELETE_ALL_IMAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deletePhotoCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code DeleteAllPhotosCommand}.
     */
    private DeleteAllPhotosCommand prepareCommand() {
        DeleteAllPhotosCommand deleteAllPhotosCommand = new DeleteAllPhotosCommand();
        deleteAllPhotosCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAllPhotosCommand;
    }

    /**
     * Preload photos to test data.
     */
    private void prepareUploadPhotos() throws CommandException {
        UploadPhotoCommand uploadPhotoCommand1 = new UploadPhotoCommand(INDEX_FIRST_PERSON,
                "./src/test/resources/photos/connectus_icon.png");
        UploadPhotoCommand uploadPhotoCommand2 = new UploadPhotoCommand(INDEX_SECOND_PERSON,
                "./src/test/resources/photos/connectus_icon.png");
        uploadPhotoCommand1.setData(model, new CommandHistory(), new UndoRedoStack());
        uploadPhotoCommand2.setData(model, new CommandHistory(), new UndoRedoStack());
        uploadPhotoCommand1.execute();
        uploadPhotoCommand2.execute();
    }

}
