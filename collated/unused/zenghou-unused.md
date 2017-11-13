# zenghou-unused
###### /java/seedu/address/logic/commands/AddCommandIntegrationTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     *  conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        Person validPerson = new PersonBuilder().build();

        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        assertCommandFailure(prepareCommand(validPerson, userCredsNotValidatedModel), userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        Model model = new ModelManager();
        Person validPerson = new PersonBuilder().build();

        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        Command cmd = new AddCommand(validPerson);
        cmd.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(cmd, userCredsNotValidatedModel, userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/ClearCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());
        assertCommandFailure(prepareCommand(userCredsNotValidatedModel), userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/DeleteCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        assertCommandFailure(prepareCommand(INDEX_FIRST_PERSON), userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/ExportCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());

        List<Index> indexes = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2));
        String filePath = "TestFile.xml";
        ExportCommand exportCommand = new ExportCommand(indexes, filePath);

        exportCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(exportCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/HistoryCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());

        HistoryCommand historyCommand = new HistoryCommand();

        historyCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(historyCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/ImportCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());

        String filePath = "SomeFile.xml";
        ImportCommand importCommand = new ImportCommand(filePath);

        importCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(importCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/ListCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        ListCommand listCommand = new ListCommand();

        listCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(listCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/RedoCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        RedoCommand redoCommand = new RedoCommand();

        redoCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(redoCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(TEST_REMARK));

        remarkCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(remarkCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/SearchCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        SearchCommand searchCommand = new SearchCommand(new DetailsContainKeyphrasePredicate("testing one"));

        searchCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(searchCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/SelectCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        SelectCommand selectCommand = new SelectCommand(INDEX_FIRST_PERSON);

        selectCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(selectCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/logic/commands/UndoCommandTest.java
``` java
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());

        deleteCommandOne.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(deleteCommandOne, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Fills up all the commandbox placeholder of this window.
     */
    void fillCommandBoxAndDisplayPanel() {
        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
    }
```
