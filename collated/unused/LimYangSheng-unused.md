# LimYangSheng-unused
###### \AddressBookParser.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    /**
     * Parses user permission input for verification
     *
     * @param userInput full user input string
     * @return permission based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public boolean parsePermission(String userInput) throws ParseException {
        final Matcher matcher = BASIC_PERMISSION_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String permission = matcher.group("permission");
        switch(permission) {
        case "yes": case "y":
            return true;
        case "no": case "n":
            return false;
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

```
###### \AddressBookParserTest.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    @Test
    public void parsePermission_userAllows() throws Exception {
        assertTrue(parser.parsePermission("yes"));
        assertTrue(parser.parsePermission("y"));
    }

    @Test
    public void parsePermission_userDoNotAllow() throws Exception {
        assertFalse(parser.parsePermission("no"));
        assertFalse(parser.parsePermission("n"));
    }

    @Test
    public void parsePermission_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parsePermission("");
    }

    @Test
    public void parsePermission_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parsePermission("unknownCommand");
    }

```
###### \CommandBox.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        if (addressBookReplyFlag) {
            handlePermissionCommandInputChanged();
            addressBookReplyFlag = false;
        } else {
            handleCommonCommandInputChanged();
        }
    }

    /**
     * Executes the previous command according to the user's given permission.
     */
    private void handlePermissionCommandInputChanged() {
        try {
            CommandResult commandResult = logic.executeAfterUserPermission(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

```
###### \CommandBox.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    /**
     * Handles the event when Address Book requires user's confirmation to proceed with a command.
     */
    @Subscribe
    public void handleRequestingUserPermissionEvent(RequestingUserPermissionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        addressBookReplyFlag = true;
    }
```
###### \CommandBoxTest.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    @Test
    public void commandBox_successfulCommand_forHandlePermissionCommandInputChanged() {
        assertBehaviorForSuccessfulPermissionCommand();
        EventsCenter.getInstance().post(new RequestingUserPermissionEvent());
        assertBehaviorForPermissionInput();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_unsuccessfulCommand_forHandlePermissionCommandInputChangedParseException() {
        assertBehaviorForSuccessfulPermissionCommand();
        EventsCenter.getInstance().post(new RequestingUserPermissionEvent());
        assertBehaviorForFailedCommand();
    }

```
###### \CommandBoxTest.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommonCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS_COMMON);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a Permission command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulPermissionCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS_PERMISSION);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

```
###### \Logic.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    /**
     * Executes the command that requires a user's reply and returns the result.
     * @param permissionText The permission as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult executeAfterUserPermission(String permissionText) throws CommandException, ParseException;

```
###### \LogicManager.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    @Override
    public CommandResult executeAfterUserPermission(String permissionText) throws CommandException, ParseException {
        logger.info("----------------[USER INPUT][" + permissionText + "]");
        try {
            boolean userPermission = addressBookParser.parsePermission(permissionText);
            assert(previousCommand instanceof PermissionCommand);
            PermissionCommand command = (PermissionCommand) previousCommand;
            CommandResult result = command.executeAfterUserPermission(userPermission);
            return result;
        } finally {
            history.add(permissionText);
        }
    }

```
###### \LogicManagerTest.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    @Test
    public void executeAfterUserPermission_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND, "permission");
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void executeAfterUserPermission_validCommand_success() {
        String permission = "no";
        assertCommandSuccess(RestoreBackupCommand.COMMAND_WORD,
                RestoreBackupCommand.MESSAGE_NO_BACKUP_FILE, model, "common");
        assertCommandSuccess(permission, RestoreBackupCommand.MESSAGE_FAILURE, model, "permission");
        assertHistoryCorrect(permission, RestoreBackupCommand.COMMAND_WORD);
    }

```
###### \LogicManagerTest.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel, String commandType) {
        if (commandType.equals("common")) {
            executeCommonCommand(expectedException, inputCommand, expectedMessage, expectedModel);
        } else { //if commandType.equals("permission")
            executePermissionCommandAfterUserPermission(expectedException, inputCommand,
                    expectedMessage, expectedModel);
        }
    }

    /**
     * Executes the previous command after user enters permission
     * and check for correct exception thrown and correct feedback returned.
     */
    private void executePermissionCommandAfterUserPermission(Class<?> expectedException, String inputCommand,
                                                             String expectedMessage, Model expectedModel) {
        try {
            CommandResult result = logic.executeAfterUserPermission(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
        assertEquals(expectedModel, model);
    }

```
###### \PermissionCommand.java
``` java
// Code was not used as requesting user permission before execution was not advised for CLI applications.
/**
 * Represents a command that requires user's permission before proceeding.
 */
public abstract class PermissionCommand extends Command {
    /**
     * Executes the command that requires a user's reply and returns the result.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult executeAfterUserPermission(boolean userPermission) throws CommandException;
}
```
###### \RequestingUserPermissionEvent.java
``` java
// Code was not used as requesting user permission before execution was not advised for CLI applications.
/**
 * Indicates a request to accept user permission before execution of command
 */
public class RequestingUserPermissionEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \RestoreBackupCommand.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    @Override
    public CommandResult executeAfterUserPermission(boolean userPermission) throws CommandException {
        if (userPermission) {
            RestoreBackupDataEvent event = new RestoreBackupDataEvent();
            EventsCenter.getInstance().post(event);
            ReadOnlyAddressBook backupAddressBookData = event.getAddressBookData();
            model.resetData(backupAddressBookData);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_FAILURE));
        }
    }
```
###### \RestoreBackupCommandTest.java
``` java
    // Code was not used as requesting user permission before execution was not advised for CLI applications.
    @Test
    public void executeAfterUserPermission_restoreBackup_successful() throws Exception {
        storageManager.saveAddressBook(getTypicalAddressBook(), backupFilePath);
        assertTrue(model.getFilteredPersonList().size() == 0);
        ReadOnlyAddressBook expectedAddressBook = getTypicalAddressBook();
        CommandResult result = restoreBackupCommand.executeAfterUserPermission(true);
        assertEquals(result.feedbackToUser, restoreBackupCommand.MESSAGE_SUCCESS);
        ReadOnlyAddressBook retrieved = model.getAddressBook();
        assertEquals(expectedAddressBook, retrieved);
    }

    @Test
    public void executeAfterUserPermission_restoreBackup_unsuccessful() throws Exception {
        storageManager.saveAddressBook(getTypicalAddressBook(), backupFilePath);
        assertTrue(model.getFilteredPersonList().size() == 0);
        ReadOnlyAddressBook expectedAddressBook = new AddressBook();
        CommandResult result = restoreBackupCommand.executeAfterUserPermission(false);
        assertEquals(result.feedbackToUser, restoreBackupCommand.MESSAGE_FAILURE);
        ReadOnlyAddressBook retrieved = model.getAddressBook();
        assertEquals(expectedAddressBook, retrieved);
    }

```
