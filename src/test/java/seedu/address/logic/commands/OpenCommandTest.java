package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TestUtil.getFilePathInDataFolder;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author chrisboo
/**
 * Contains integration tests (interaction with the Model) for {@code OpenCommand}.
 */
public class OpenCommandTest {

    @Test
    public void execute_fileExists_success() {
        assertExecutionSuccess(getFilePathInDataFolder("sampleData.xml"));
    }

    @Test
    public void execute_fileNotExist_failure() {
        assertExecutionFailure(
            getFilePathInDataFolder("nonExistentFile.xml"), Messages.MESSAGE_INVALID_FILE_PATH);
    }

    @Test
    public void equals() {
        File firstFile = new File(getFilePathInDataFolder("sampleData.xml"));
        File secondFile = new File(getFilePathInDataFolder("sampleData2.xml"));

        OpenCommand openFirstCommand = new OpenCommand(firstFile);
        OpenCommand openSecondCommand = new OpenCommand(secondFile);

        // same object -> returns true
        assertTrue(openFirstCommand.equals(openFirstCommand));

        // same values -> returns true
        OpenCommand openFirstCommandCopy = new OpenCommand(firstFile);
        assertTrue(openFirstCommand.equals(openFirstCommandCopy));

        // different types -> returns false
        assertFalse(openFirstCommand.equals(1));

        // null -> returns false
        assertFalse(openFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(openFirstCommand.equals(openSecondCommand));
    }

    /**
     * Executes a {@code OpenCommand} with the given {@code filePath}.
     */
    private void assertExecutionSuccess(String filePath) {
        OpenCommand openCommand = new OpenCommand(new File(filePath));

        try {
            CommandResult commandResult = openCommand.execute();
            assertEquals(String.format(OpenCommand.MESSAGE_OPEN_DEATHNOTE_SUCCESS, filePath),
                commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    /**
     *
     * Executes a {@code OpenCommand} with the given {@code filePath}, and checks that {@code CommandException} is
     * thrown with {@code expectedMessage}.
     */
    private void assertExecutionFailure(String filePath, String expectedMessage) {
        OpenCommand openCommand = new OpenCommand(new File(filePath));

        try {
            openCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), expectedMessage);
        }
    }
}
//@@author
