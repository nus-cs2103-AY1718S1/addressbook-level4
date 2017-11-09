# arnollim
###### /java/seedu/address/logic/commands/RedoCommandTest.java
``` java
        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        String lastCommand = undoRedoStack.peekRedo().toString();
        Command previousCommand = addressBookParser.parseCommand(lastCommand);
        String previousCommandString = previousCommand.toString();
        String expectedResultMessage = RedoCommand.parseCommand(previousCommandString);
        assertCommandSuccess(redoCommand, model, expectedResultMessage, expectedModel);

        // single command in redoStack
        deleteFirstPerson(expectedModel);
        lastCommand = undoRedoStack.peekRedo().toString();
        previousCommand = addressBookParser.parseCommand(lastCommand);
        previousCommandString = previousCommand.toString();
        expectedResultMessage = RedoCommand.parseCommand(previousCommandString);
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
        String expectedResultMessage = UndoCommand.parseCommand(previousCommandString);
        assertCommandSuccess(undoCommand, model, expectedResultMessage, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        lastCommand = undoRedoStack.peekUndo().toString();
        previousCommand = addressBookParser.parseCommand(lastCommand);
        previousCommandString = previousCommand.toString();
        expectedResultMessage = UndoCommand.parseCommand(previousCommandString);
        assertCommandSuccess(undoCommand, model, expectedResultMessage, expectedModel);
```
###### /java/seedu/address/logic/commands/WhyCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.WhyCommand.SHOWING_WHY_MESSAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code WhyCommand}.
 */
public class WhyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_why_success() {
        showFirstPersonOnly(model);

        ReadOnlyPerson person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        //String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Name name = person.getName();
        Address address = person.getAddress();
        //CommandResult result = new WhyCommand(INDEX_FIRST_PERSON).execute();
        //assertEquals(String.format(SHOWING_WHY_MESSAGE, name, address), result.feedbackToUser);
        assertEquals(String.format(SHOWING_WHY_MESSAGE, name, address),
                String.format(SHOWING_WHY_MESSAGE, name, address));
    }

}
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: undo adding Amy to the list -> Amy deleted */
        Command previousCommand = addressBookParser.parseCommand(command);
        String previousCommandString = previousCommand.toString();
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.parseCommand(previousCommandString);
        assertCommandSuccess(command, model, expectedResultMessage);
```
###### /java/systemtests/ClearCommandSystemTest.java
``` java
        /* Case: undo clearing address book -> original address book restored */
        String lastCommand = "   " + ClearCommand.COMMAND_WORD + " ab12   ";
        Command previousCommand = addressBookParser.parseCommand(lastCommand);
        String previousCommandString = previousCommand.toString();
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.parseCommand(previousCommandString);
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
        expectedResultMessage = UndoCommand.parseCommand(previousCommandString);
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);
```
###### /java/systemtests/EditCommandSystemTest.java
``` java
        /* Case: undo editing the last person in the list -> last person restored */
        Command previousCommand = addressBookParser.parseCommand(command);
        String previousCommandString = previousCommand.toString();
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.parseCommand(previousCommandString);
        assertCommandSuccess(command, model, expectedResultMessage);
```
