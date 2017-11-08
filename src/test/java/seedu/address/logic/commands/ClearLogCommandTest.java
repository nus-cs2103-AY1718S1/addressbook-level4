package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
//@@author danielweide
public class ClearLogCommandTest {

    @Test
    public void execute_clearConnectUsLogSuccess() {
        boolean expectedOutput = true;
        boolean testOutput = false;
        try {
            prepareCommand();
            Path f1 = Paths.get("ConnectUsLog.txt");
            byte[] file1 = Files.readAllBytes(f1); // if ConnectUsLog.txt exist means fail
        } catch (Exception e) {
            testOutput = true; //command worked as file will be gone
        }
        assertEquals(expectedOutput, testOutput);
    }

    /**
     * Calling out ClearLogCommand To Remove ConnectUsLog.txt file
     */
    private void prepareCommand() throws CommandException, IOException {
        ClearLogCommand clearLogCommand = new ClearLogCommand();
        clearLogCommand.execute(); //There would be no more ConnectUs.txt file after this
    }
}
