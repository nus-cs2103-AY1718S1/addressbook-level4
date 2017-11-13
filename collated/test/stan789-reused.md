# stan789-reused
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model, false);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model, false);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
    /**
     * Returns a {@code EmailCommand} with parameters {@code index}.
     */
    private EmailCommandStub prepareCommand(Index index) {
        EmailCommandStub emailCommand = new EmailCommandStub(index);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return emailCommand;
    }

}

class EmailCommandStub extends EmailCommand {

    EmailCommandStub(Index index) {
        super(index);
    }

    @Override
    protected void desktopEmail(String email) throws IOException, URISyntaxException {
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
    /**
     * Generates a new {@code ExportCommand} which upon execution, sorts the AddressBook.
     */
    private ExportCommand prepareCommand(String fileLocation, String fileName, String extension) {
        ExportCommand command = new ExportCommand(fileLocation, fileName, extension);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    /**
     * Generates a new {@code ImportCommand} which upon execution, sorts the AddressBook.
     */
    private ImportCommand prepareCommand(Path fileLocation) {
        ImportCommand command = new ImportCommand(fileLocation);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook.
     */
    private SortCommand prepareCommand(String sortType) {
        SortCommand command = new SortCommand(sortType);
        model.setListDisplay();
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the AddressBook, used for when bin is displayed
     */
    private SortCommand prepareCommand_binMode(String sortType) {
        SortCommand command = new SortCommand(sortType);
        model.setBinDisplay();
        command.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new EmailCommand(INDEX_FIRST_PERSON));
    }
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
```
###### \java\systemtests\ImportCommandSystemTest.java
``` java
    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code String.format(MESSAGE_SUCCESS, count)},
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Integer count, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", String.format(MESSAGE_SUCCESS, count), expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
}
```
###### \java\systemtests\SortCommandSystemTest.java
``` java
    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code MESSAGE_SUCCESS},
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", MESSAGE_SUCCESS, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

```
###### \java\systemtests\SortCommandSystemTest.java
``` java
    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
