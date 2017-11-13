package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.commands.OpenRolodexCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

public class KeyListenerTest extends RolodexGuiTest {

    @Test
    public void executeKeyEventForFocusOnCommandBox() {
        guiRobot.push(KeyCode.ENTER);
        assertTrue(getCommandBox().isFocused());
        guiRobot.push(KeyCode.A);
        assertTrue(getCommandBox().isFocused());

        guiRobot.push(KeyCode.ESCAPE);
        assertFalse(getCommandBox().isFocused());
    }

    @Test
    public void executeKeyEventForFocusOnPersonListPanel() {
        KeyCodeCombination focusKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Left");

        guiRobot.push(focusKeyCode);
        assertTrue(getPersonListPanel().isFocused());

        guiRobot.push(KeyCode.ENTER);
        assertFalse(getPersonListPanel().isFocused());

        guiRobot.push(KeyCode.ESCAPE);
        assertTrue(getPersonListPanel().isFocused());

        // Check scrolling
        guiRobot.push(KeyCode.UP);
        assertTrue(getPersonListPanel().isFocused());
        guiRobot.push(KeyCode.DOWN);
        assertTrue(getPersonListPanel().isFocused());
    }

    @Test
    public void executeKeyEventForFocusOnResultDisplayPanel() {
        KeyCodeCombination focusKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Right");
        guiRobot.push(focusKeyCode);
        assertTrue(getResultDisplay().isFocused());

        guiRobot.push(KeyCode.ENTER);
        assertFalse(getPersonListPanel().isFocused());
    }

    @Test
    public void executeKeyEventForOpenRolodexCommand() {
        KeyCodeCombination openKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+O");

        guiRobot.push(openKeyCode);
        assertEquals(OpenRolodexCommand.FORMAT, getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForNewCommand() {
        KeyCodeCombination newKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+N");

        guiRobot.push(newKeyCode);
        assertEquals(NewRolodexCommand.FORMAT, getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForUndoCommand() {
        KeyCodeCombination undoKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Z");

        // Empty undo stack
        guiRobot.push(undoKeyCode);
        assertEquals(UndoCommand.MESSAGE_FAILURE, getResultDisplay().getText());

        getCommandBox().run("delete 1");
        guiRobot.push(undoKeyCode);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
    }

    @Test
    public void executeKeyEventForRedoCommand() {
        KeyCodeCombination redoKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Y");

        // Empty redo stack
        guiRobot.push(redoKeyCode);
        assertEquals(RedoCommand.MESSAGE_FAILURE, getResultDisplay().getText());

        getCommandBox().run("delete 1");
        getCommandBox().run("undo");

        guiRobot.push(redoKeyCode);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
    }

    @Test
    public void executeKeyEventForClearCommand() {
        KeyCodeCombination clearKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Shift+C");

        guiRobot.push(clearKeyCode);
        assertEquals(ClearCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
    }

    @Test
    public void executeKeyEventForListCommand() {
        KeyCodeCombination listKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+L");

        guiRobot.push(listKeyCode);
        assertEquals(ListCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
    }

    @Test
    public void executeKeyEventForHistoryCommand() {
        KeyCodeCombination viewHistoryKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+H");

        guiRobot.push(viewHistoryKeyCode);
        assertEquals(HistoryCommand.MESSAGE_NO_HISTORY, getResultDisplay().getText());

        String command1 = "clear";
        getCommandBox().run(command1);
        guiRobot.push(viewHistoryKeyCode);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command1, "history"));

        assertEquals(expectedMessage, getResultDisplay().getText());
    }

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


}
