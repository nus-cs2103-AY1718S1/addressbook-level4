# mingruiii
###### /java/guitests/guihandles/CommandBoxHandle.java
``` java
    /**
     * Enters the given command in the Command Box, wait for the drop-down list to appear and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean inputAndEnter(String input) {
        click();
        guiRobot.interact(() -> getRootNode().setText(input));
        guiRobot.pauseForDropDownList();

        guiRobot.type(KeyCode.ENTER);

        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }
```
###### /java/guitests/GuiRobot.java
``` java
    private static final int PAUSE_FOR_DROP_DOWN_LIST_TO_APPEAR = 500;
```
###### /java/guitests/GuiRobot.java
``` java
    /**
     * Pauses execution for {@code PAUSE_FOR_DROP_DOWN_LIST_TO_APPEAR} milliseconds for the auto-completion drop-down
     * list to appear. This method will be disabled when the GUI tests are executed in headless mode to avoid
     * unnecessary delays.
     */
    public void pauseForDropDownList() {
        sleep(PAUSE_FOR_DROP_DOWN_LIST_TO_APPEAR);
    }
```
###### /java/guitests/KeyListenerTest.java
``` java
    @Test
    public void executeKeyEventForAddCommand() {
        KeyCodeCombination addCommandKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Equals");

        guiRobot.push(addCommandKeyCode);
        assertEquals(AddCommand.FORMAT, getCommandBox().getInput());

        guiRobot.push(KeyCode.A);
        assertEquals("add n/a p/PHONE_NUMBER e/EMAIL a/ADDRESS", getCommandBox().getInput());

        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.B);
        assertEquals("add n/a p/b e/EMAIL a/ADDRESS", getCommandBox().getInput());

        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.C);
        assertEquals("add n/a p/b e/c a/ADDRESS", getCommandBox().getInput());

        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.D);
        assertEquals("add n/a p/b e/c a/d", getCommandBox().getInput());

        KeyCodeCombination shiftTab = new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN);
        guiRobot.push(shiftTab);
        guiRobot.push(KeyCode.E);
        assertEquals("add n/a p/b e/e a/d", getCommandBox().getInput());

        guiRobot.push(shiftTab);
        guiRobot.push(KeyCode.F);
        assertEquals("add n/a p/f e/e a/d", getCommandBox().getInput());

        guiRobot.push(shiftTab);
        guiRobot.push(KeyCode.G);
        assertEquals("add n/g p/f e/e a/d", getCommandBox().getInput());

        guiRobot.push(shiftTab);
        guiRobot.push(KeyCode.H);
        assertEquals("add n/g p/f e/e a/h", getCommandBox().getInput());

        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.I);
        assertEquals("add n/i p/f e/e a/h", getCommandBox().getInput());

    }

    @Test
    public void executeKeyEventForEditCommand() {
        KeyCodeCombination editCommandKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+E");

        guiRobot.push(editCommandKeyCode);
        assertEquals(EditCommand.FORMAT, getCommandBox().getInput());

        guiRobot.push(KeyCode.A);
        assertEquals("edit a", getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForEmailCommand() {
        KeyCodeCombination emailCommandKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+M");

        guiRobot.push(emailCommandKeyCode);
        assertEquals(EmailCommand.FORMAT, getCommandBox().getInput());

        guiRobot.push(KeyCode.A);
        assertEquals("email a", getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForDeleteCommand() {
        KeyCodeCombination deleteCommandKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+D");

        guiRobot.push(deleteCommandKeyCode);
        assertEquals(DeleteCommand.FORMAT, getCommandBox().getInput());

        guiRobot.push(KeyCode.A);
        assertEquals("delete a", getCommandBox().getInput());

        deleteCommandKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Minus");
        guiRobot.push(deleteCommandKeyCode);
        assertEquals(DeleteCommand.FORMAT, getCommandBox().getInput());

        guiRobot.push(KeyCode.A);
        assertEquals("delete a", getCommandBox().getInput());

    }

    @Test
    public void executeKeyEventForFindCommand() {
        KeyCodeCombination findCommandKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+F");

        guiRobot.push(findCommandKeyCode);
        assertEquals(FindCommand.FORMAT, getCommandBox().getInput());

        guiRobot.push(KeyCode.A);
        assertEquals("find a", getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForRemarkCommand() {
        KeyCodeCombination remarkCommandKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+R");

        guiRobot.push(remarkCommandKeyCode);
        assertEquals(RemarkCommand.FORMAT, getCommandBox().getInput());

        guiRobot.push(KeyCode.A);
        assertEquals("remark a r/REMARK", getCommandBox().getInput());

        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.B);
        assertEquals("remark a r/b", getCommandBox().getInput());

        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.C);
        assertEquals("remark c r/b", getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForSelectCommand() {
        KeyCodeCombination selectCommandKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+S");

        guiRobot.push(selectCommandKeyCode);
        assertEquals(SelectCommand.FORMAT, getCommandBox().getInput());

        guiRobot.push(KeyCode.A);
        assertEquals("select a", getCommandBox().getInput());
    }
```
###### /java/seedu/address/logic/commands/EmailCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalRolodex;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalRolodex(), new UserPrefs());

    /*
    This test is disabled because the Desktop class is not supported on Ubuntu
    AppVeyor build succeeds, but Travis build fails because of the above reason
    This test can be enabled on local (Windows or MacOS)

    @Test
    public void executeOpenEmailSuccess() throws CommandException {
        EmailCommand command = new EmailCommand(INDEX_FIRST_PERSON, "Hello");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
    }
    */

    @Test
    public void executeInvalidPersonIndexFailure() throws CommandException {
        Index outOfBoundIndex = Index.fromOneBased(model.getLatestPersonList().size() + 1);
        EmailCommand command = new EmailCommand(outOfBoundIndex, "Hello");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
```
###### /java/seedu/address/logic/parser/CommandParserTestUtil.java
``` java
            EmailCommand.COMMAND_WORD_ABBREVIATIONS,
```
###### /java/seedu/address/logic/parser/EmailCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.lang.reflect.Method;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.ModelManager;

public class EmailCommandParserTest {
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parseSubjectAbsentSuccess() {
        assertParseSuccess(parser, "1", new EmailCommand(INDEX_FIRST_PERSON, ""));
    }

    @Test
    public void parseSubjectPresentSuccess() {
        assertParseSuccess(parser, "1 s/hello", new EmailCommand(INDEX_FIRST_PERSON, "hello"));
    }

    @Test
    public void parseSubjectPresentWithSpacingSuccess() {
        assertParseSuccess(parser, "1 s/hello world", new EmailCommand(INDEX_FIRST_PERSON, "hello%20world"));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidPersonIndexThrowsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
```
###### /java/seedu/address/logic/parser/RolodexParserTest.java
``` java
    @Test
    public void parseCommandEmail() throws Exception {
        String subject = "Hello world";
        String subjectParsed = "Hello%20world";
        EmailCommand command = (EmailCommand) parser.parseCommand(EmailCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_SUBJECT + subject);
        assertEquals(new EmailCommand(INDEX_FIRST_PERSON, subjectParsed), command);
    }
```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    @Test
    public void addCommandAutoComplete() {
        assertAutoComplete("a", AddCommand.COMMAND_WORD);
        assertInputHistory(KeyCode.TAB, AddCommand.FORMAT);
    }

    @Test
    public void selectCommandAutoComplete() {
        assertAutoComplete("s", SelectCommand.COMMAND_WORD);
        assertInputHistory(KeyCode.TAB, SelectCommand.FORMAT);
    }

    @Test
    public void findCommandAutoComplete() {
        assertAutoComplete("f", FindCommand.COMMAND_WORD);
        assertInputHistory(KeyCode.TAB, FindCommand.FORMAT);
    }

    @Test
    public void deleteCommandAutoComplete() {
        assertAutoComplete("d", DeleteCommand.COMMAND_WORD);
        assertInputHistory(KeyCode.TAB, DeleteCommand.FORMAT);
    }

    @Test
    public void editCommandAutoComplete() {
        assertAutoComplete("ed", EditCommand.COMMAND_WORD);
        assertInputHistory(KeyCode.TAB, EditCommand.FORMAT);
    }
```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    /**
     * Types in {@code input} and presses enter to select the first option from the drop-down list,
     * then checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertAutoComplete(String input, String expectedCommand) {
        commandBoxHandle.inputAndEnter(input);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }
```
