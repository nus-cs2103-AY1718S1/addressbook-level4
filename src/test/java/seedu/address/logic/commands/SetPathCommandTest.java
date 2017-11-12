package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.ProfilePicturesFolder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.UndoRedoStack;

//@@author jaivigneshvenugopal
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SetPathCommand}.
 */
public class SetPathCommandTest extends CommandTest {

    @Test
    public void execute_setPath_success() throws Exception {
        String path = "src/main/";
        SetPathCommand setPathCommand = prepareSetPathCommand(path);
        setPathCommand.execute();

        assertTrue(ProfilePicturesFolder.getPath().equals(path));
    }

    @Test
    public void execute_setPathMissingForwardSlashGetsConcatenated_success() throws Exception {
        String path = "src/main";
        String expectedPath = "src/main/";

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
