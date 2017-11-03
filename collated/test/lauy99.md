# lauy99
###### \java\seedu\address\logic\commands\ExpireCommandTest.java
``` java
public class ExpireCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws IllegalValueException {
        final ExpireCommand standardCommand = new ExpireCommand(INDEX_FIRST_PERSON, new ExpiryDate(VALID_EXPIRE_AMY));

        // same value -> returns true
        ExpireCommand commandWithSameValues = new ExpireCommand(INDEX_FIRST_PERSON, new ExpiryDate(VALID_EXPIRE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ExpireCommand(INDEX_SECOND_PERSON, new ExpiryDate(VALID_EXPIRE_AMY))));

        // different dateString -> returns false
        assertFalse(standardCommand.equals(new ExpireCommand(INDEX_FIRST_PERSON, new ExpiryDate(VALID_EXPIRE_BOB))));
    }

    /**
     * Returns an {@code ExpireCommand} with parameters {@code index} and {@code dateString}
     */
    private ExpireCommand prepareCommand(Index index, String dateString) throws IllegalValueException {
        ExpireCommand expireCommand = new ExpireCommand(index, new ExpiryDate(dateString));
        expireCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return expireCommand;
    }

}
```
###### \java\seedu\address\logic\parser\ExpireCommandParserTest.java
``` java
public class ExpireCommandParserTest {

    private ExpireCommandParser parser = new ExpireCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String dateString = "2011-01-01";

        // have date
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_EXPIRE.toString() + " " + dateString;
        ExpireCommand expectCommand = new ExpireCommand(INDEX_FIRST_PERSON, new ExpiryDate(dateString));

        assertParseSuccess(parser, userInput, expectCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpireCommand.MESSAGE_USAGE);

        assertParseFailure(parser, ExpireCommand.COMMAND_WORD, expectedMessage);
    }
}
```
