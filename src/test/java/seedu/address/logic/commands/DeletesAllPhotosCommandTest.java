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
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeletesAllPhotosCommand}.
 */
public class DeletesAllPhotosCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() throws Exception {
        prepareUploadPhotos();

        DeletesAllPhotosCommand deletesPhotoCommand = prepareCommand();

        String expectedMessage = String.format(DeletesAllPhotosCommand.MESSAGE_DELETES_ALL_IMAGES_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deletesPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_success_noPhotosToDelete() throws Exception {

        DeletesAllPhotosCommand deletesPhotoCommand = prepareCommand();

        String expectedMessage = String.format(DeletesAllPhotosCommand.MESSAGE_DELETES_ALL_IMAGES_FAILURE);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deletesPhotoCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code DeletesAllPhotosCommand}.
     */
    private DeletesAllPhotosCommand prepareCommand() {
        DeletesAllPhotosCommand deletesAllPhotosCommand = new DeletesAllPhotosCommand();
        deletesAllPhotosCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deletesAllPhotosCommand;
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
