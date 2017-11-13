# frozventus-reused
###### \java\seedu\address\logic\commands\BinDeleteCommandTest.java
``` java
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BinDeleteCommand binDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(binDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### \java\seedu\address\logic\commands\BinDeleteCommandTest.java
``` java
    @Test
    public void equals() {
        BinDeleteCommand binDeleteFirstCommand = new BinDeleteCommand(INDEX_FIRST_PERSON);
        BinDeleteCommand binDeleteSecondCommand = new BinDeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(binDeleteFirstCommand.equals(binDeleteFirstCommand));

        // same values -> returns true
        BinDeleteCommand deleteFirstCommandCopy = new BinDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(binDeleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(binDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(binDeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(binDeleteFirstCommand.equals(binDeleteSecondCommand));
    }

    /**
     * Returns a {@code BinDeleteCommand} with the parameter {@code index}.
     */
    private BinDeleteCommand prepareCommand(Index index) {
        BinDeleteCommand binDeleteCommand = new BinDeleteCommand(index);
        binDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return binDeleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\RestoreCommandTest.java
``` java
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RestoreCommand restoreCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### \java\seedu\address\logic\commands\RestoreCommandTest.java
``` java
    @Test
    public void equals() {
        RestoreCommand restoreFirstCommand = new RestoreCommand(INDEX_FIRST_PERSON);
        RestoreCommand restoreSecondCommand = new RestoreCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(restoreFirstCommand.equals(restoreFirstCommand));

        // same values -> returns true
        RestoreCommand restoreFirstCommandCopy = new RestoreCommand(INDEX_FIRST_PERSON);
        assertTrue(restoreFirstCommand.equals(restoreFirstCommandCopy));

        // different types -> returns false
        assertFalse(restoreFirstCommand.equals(1));

        // null -> returns false
        assertFalse(restoreFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(restoreFirstCommand.equals(restoreSecondCommand));
    }

    /**
     * Returns a {@code RestoreCommand} with the parameter {@code index}.
     */
    private RestoreCommand prepareCommand(Index index) {
        RestoreCommand restoreCommand = new RestoreCommand(index);
        restoreCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
        return restoreCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\parser\BinDeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsBinDeleteCommand() {
        assertParseSuccess(parser, "1", new BinDeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinDeleteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RestoreCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsRestoreCommand() {
        assertParseSuccess(parser, "1", new RestoreCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE));
    }
}
```
