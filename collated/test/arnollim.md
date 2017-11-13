# arnollim
###### /java/seedu/address/logic/commands/PrintCommandTest.java
``` java
/**
 * Contains Testcases for  {@code PrintCommand}.
 */
public class PrintCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_print_success () {
        String fileName = "fileName";
        PrintCommand printCommand = prepareCommand(fileName);

        String expectedMessage = String.format(MESSAGE_SUCCESS, fileName);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(printCommand, model, expectedMessage, expectedModel);

    }


    private PrintCommand prepareCommand(String fileName) {
        PrintCommand printCommand = new PrintCommand(fileName);
        printCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return printCommand;
    }
}
```
###### /java/seedu/address/logic/commands/RedoCommandTest.java
``` java
        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        String lastCommand = undoRedoStack.peekRedo().toString();
        Command previousCommand = addressBookParser.parseCommand(lastCommand);
        String previousCommandString = previousCommand.toString();
        String expectedResultMessage = RedoCommand.parseRedoCommand(previousCommandString);
        assertCommandSuccess(redoCommand, model, expectedResultMessage, expectedModel);

        // single command in redoStack
        deleteFirstPerson(expectedModel);
        lastCommand = undoRedoStack.peekRedo().toString();
        previousCommand = addressBookParser.parseCommand(lastCommand);
        previousCommandString = previousCommand.toString();
        expectedResultMessage = RedoCommand.parseRedoCommand(previousCommandString);
        assertCommandSuccess(redoCommand, model, expectedResultMessage, expectedModel);
```
###### /java/seedu/address/logic/commands/UndoCommandTest.java
``` java
        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        deleteFirstPerson(expectedModel);
        String lastCommand = undoRedoStack.peekUndo().toString();
        Command previousCommand = addressBookParser.parseCommand(lastCommand);
        String previousCommandString = previousCommand.toString();
        String expectedResultMessage = UndoCommand.parseUndoCommand(previousCommandString);
        assertCommandSuccess(undoCommand, model, expectedResultMessage, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        lastCommand = undoRedoStack.peekUndo().toString();
        previousCommand = addressBookParser.parseCommand(lastCommand);
        previousCommandString = previousCommand.toString();
        expectedResultMessage = UndoCommand.parseUndoCommand(previousCommandString);
        assertCommandSuccess(undoCommand, model, expectedResultMessage, expectedModel);
```
###### /java/seedu/address/logic/commands/WhyCommandTest.java
``` java
/**
 * Contains Testcases for  {@code WhyCommand}.
 */
public class WhyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_why_success() {
        showFirstPersonOnly(model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        WhyCommand whyCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(whyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private WhyCommand prepareCommand(Index index) {
        WhyCommand whyCommand = new WhyCommand(index);
        whyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return whyCommand;
    }

}
```
###### /java/seedu/address/logic/parser/WhyCommandParserTest.java
``` java
/**
 * WhyCommandParserTest tests the validity of the indices provided to WhyCommand
 */
public class WhyCommandParserTest {

    private WhyCommandParser parser = new WhyCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidIndex = "abc";
        String feedbackToUser = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidIndex, feedbackToUser);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String invalidIndex = "0";
        String feedbackToUser = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WhyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidIndex, feedbackToUser);
    }

}
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: undo adding Amy to the list -> Amy deleted */
        Command previousCommand = addressBookParser.parseCommand(command);
        String previousCommandString = previousCommand.toString();
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.parseUndoCommand(previousCommandString);
        assertCommandSuccess(command, model, expectedResultMessage);
```
###### /java/systemtests/ClearCommandSystemTest.java
``` java
        /* Case: undo clearing address book -> original address book restored */
        String lastCommand = "   " + ClearCommand.COMMAND_WORD + " ab12   ";
        Command previousCommand = addressBookParser.parseCommand(lastCommand);
        String previousCommandString = previousCommand.toString();
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.parseUndoCommand(previousCommandString);
        assertCommandSuccess(command,  expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();
```
###### /java/systemtests/DeleteCommandSystemTest.java
``` java
        /* Case: undo deleting the last person in the list -> last person restored */
        String lastCommand = DeleteCommand.COMMAND_WORD + " " + lastPersonIndex.getOneBased();
        Command previousCommand = addressBookParser.parseCommand(lastCommand);
        String previousCommandString = previousCommand.toString();
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.parseUndoCommand(previousCommandString);
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);
```
###### /java/systemtests/EditCommandSystemTest.java
``` java
        /* Case: undo editing the last person in the list -> last person restored */
        Command previousCommand = addressBookParser.parseCommand(command);
        String previousCommandString = previousCommand.toString();
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.parseUndoCommand(previousCommandString);
        assertCommandSuccess(command, model, expectedResultMessage);
```
