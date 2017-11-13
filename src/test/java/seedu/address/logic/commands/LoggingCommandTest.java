package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
//@@author danielweide
public class LoggingCommandTest {
    /**
     * Calls Method that will log into ConnectUsLog.txt when application is running
     */
    @Before
    public void prepareStartUpCommand() throws CommandException, IOException {
        LoggingCommand loggingCommand = new LoggingCommand();
        ClearLogCommand clearLogCommand = new ClearLogCommand();
        clearLogCommand.execute(); //remove ConnectUs.txt file
        loggingCommand.startUpLog(); //create new ConnectUs.txt file
    }
    @Test
    public void execute_checkIfLoggingExist() {

        assertTrue(new File("ConnectUsLog.txt").isFile());

    }
    @Test
    public void execute_checkIfLoggingIsDone() {
        int numOfLines = 0;
        int expectedLines = 1;
        try {
            numOfLines = countLines("ConnectUsLog.txt");
        } catch (Exception e) {
            numOfLines = -1;
        }
        assertEquals(expectedLines, numOfLines);
    }
    /**
     * Count Number of Lines in txt file
     */
    private static int countLines(String filename) throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] reading = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = inputStream.read(reading)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; i++) {
                    if (reading[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            inputStream.close();
        }
    }
}
