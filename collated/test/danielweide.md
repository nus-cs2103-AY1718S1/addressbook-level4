# danielweide
###### \java\seedu\address\logic\commands\ClearLogCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\commands\LoggingCommandTest.java
``` java
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
```
