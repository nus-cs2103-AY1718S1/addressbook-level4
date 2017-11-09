package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.ProfilePicturesFolder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jaivigneshvenugopal
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SetPathCommand}.
 */
public class SetPathCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_setPath_success() throws Exception {
        String path = "C:/Users/acer/Desktop/SE/profilepic/";
        SetPathCommand setPathCommand = prepareSetPathCommand(path);
        setPathCommand.execute();

        assertTrue(ProfilePicturesFolder.getPath().equals(path));
    }

    @Test
    public void execute_setPathBackSlashReplacedToForwardSlash_success() throws Exception {
        String path = "C:\\Users\\acer\\Desktop\\SE\\profilepic";
        String expectedPath = "C:/Users/acer/Desktop/SE/profilepic";

        SetPathCommand setPathCommand = prepareSetPathCommand(path);
        setPathCommand.execute();

        assertTrue(ProfilePicturesFolder.getPath().equals(expectedPath));
    }

    /**
     * Returns a {@code SetPathCommand} with no parameters.
     */
    private SetPathCommand prepareSetPathCommand(String path) {
        SetPathCommand setPathCommand = new SetPathCommand(path);
        setPathCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setPathCommand;
    }
}
