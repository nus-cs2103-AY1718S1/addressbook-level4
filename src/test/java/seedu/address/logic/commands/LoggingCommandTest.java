package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
//@@author danielweide
public class LoggingCommandTest {

    @Test
    public void execute_checkIfLoggingExist() {
        boolean expectedOutput = true;
        boolean testOutput = true;
        try {
            prepareStartUpCommand();
            Path f1 = Paths.get("ConnectUsLog.txt");
            byte[] file1 = Files.readAllBytes(f1); // if ConnectUsLog.txt exist means pass
        } catch (Exception e) {
            testOutput = false; //if ConnectUsLog.txt does not exist means fail
        }
        assertEquals(expectedOutput, testOutput);
    }
    @Test
    public void execute_checkIfLoggingIsDone() {
        boolean expectedOutput = true;
        boolean testOutput;
        try {
            prepareKeepLogCommand();
            Path f2 = Paths.get("ConnectUsLog.txt");
            byte[] file1 = Files.readAllBytes(f2); // if ConnectUsLog.txt exist means pass
            testOutput = true;
        } catch (Exception e) {
            testOutput = false; // if ConnectUsLog.txt is not found it will fail
        }
        assertEquals(expectedOutput, testOutput);
    }
    /**
     * Calling out Method that will log into ConnectUsLog.txt when application is running
     */
    private void prepareStartUpCommand() throws CommandException, IOException {
        LoggingCommand loggingCommand = new LoggingCommand();
        ClearLogCommand clearLogCommand = new ClearLogCommand();
        clearLogCommand.execute(); //remove ConnectUs.txt file
        loggingCommand.startUpLog(); //create new ConnectUs.txt file
    }
    /**
     * Calling out Method that will log into ConnectUsLog.txt when action is executed
     */
    private void prepareKeepLogCommand() throws CommandException, IOException {
        LoggingCommand loggingCommand = new LoggingCommand();
        ClearLogCommand clearLogCommand = new ClearLogCommand();
        clearLogCommand.execute(); //remove ConnectUs.txt file
        loggingCommand.keepLog("test", "test"); //create new ConnectUs.txt file
    }
}
