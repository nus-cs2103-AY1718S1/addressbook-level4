package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
//@@author danielweide
/**
 * Test for ClearLogCommand by deleting ConnectUsLog.txt file
 */
public class ClearLogCommandTest {
    @Before
    public void prepareCommand() throws CommandException, IOException {
        ClearLogCommand clearLogCommand = new ClearLogCommand();
        clearLogCommand.execute(); //There would be no more ConnectUs.txt file after this
    }
    @Test
    public void execute_clearConnectUsLogSuccess() {
        assertFalse(new File("ConnectUsLog.txt").isFile());
    }
}
