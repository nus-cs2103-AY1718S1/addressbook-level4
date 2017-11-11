package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TestUtil.getAbsoluteFilePathInSandboxFolder;

import java.io.File;

import org.junit.Test;

//@@author chrisboo
/**
 * Contains integration tests (interaction with the Model) for {@code OpenCommand}.
 */
public class OpenCommandTest {
    @Test
    public void equals() {
        File firstFile = new File(getAbsoluteFilePathInSandboxFolder("sampleData.xml"));
        File secondFile = new File(getAbsoluteFilePathInSandboxFolder("sampleData2.xml"));

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
}
//@@author
