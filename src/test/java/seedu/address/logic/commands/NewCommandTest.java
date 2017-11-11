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
 * Contains integration tests (interaction with the Model) for {@code NewCommand}.
 */
public class NewCommandTest {

    @Test
    public void execute_fileNotExist_success() {
        assertExecutionSuccess(getFilePathInDataFolder("nonExistentFile.xml"));
    }

    @Test
    public void execute_fileExists_failure() {
        assertExecutionFailure(getFilePathInDataFolder("sampleData.xml"), Messages.MESSAGE_EXISTING_FILE);
    }

    @Test
    public void equals() {
        File firstFile = new File(getFilePathInDataFolder("sampleData.xml"));
        File secondFile = new File(getFilePathInDataFolder("sampleData2.xml"));

        NewCommand newFirstCommand = new NewCommand(firstFile);
        NewCommand newSecondCommand = new NewCommand(secondFile);

        // same object -> returns true
        assertTrue(newFirstCommand.equals(newFirstCommand));

        // same values -> returns true
        NewCommand newFirstCommandCopy = new NewCommand(firstFile);
        assertTrue(newFirstCommand.equals(newFirstCommandCopy));

        // different types -> returns false
        assertFalse(newFirstCommand.equals(1));

        // null -> returns false
        assertFalse(newFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(newFirstCommand.equals(newSecondCommand));
    }

    /**
     * Executes a {@code NewCommand} with the given {@code filePath}.
     */
    private void assertExecutionSuccess(String filePath) {
        NewCommand newCommand = new NewCommand(new File(filePath));

        try {
            CommandResult commandResult = newCommand.execute();
            assertEquals(String.format(NewCommand.MESSAGE_OPEN_DEATHNOTE_SUCCESS, filePath),
                commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    /**
     *
     * Executes a {@code NewCommand} with the given {@code filePath}, and checks that {@code CommandException} is
     * thrown with {@code expectedMessage}.
     */
    private void assertExecutionFailure(String filePath, String expectedMessage) {
        NewCommand newCommand = new NewCommand(new File(filePath));

        try {
            newCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), expectedMessage);
        }
    }
}
//@@author
