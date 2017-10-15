package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UndoCommand;

public class KeyListenerTest extends AddressBookGuiTest {

    @Test
    public void executeKeyEventForNonCommandEvents() {
        // Check that command box is accessible with assigned key presses
        guiRobot.push(KeyCode.ENTER);
        assertTrue(getCommandBox().isFocused());
        guiRobot.push(KeyCode.A);
        assertTrue(getCommandBox().isFocused());
        // Remove focus
        guiRobot.push(KeyCode.ESCAPE);
        assertFalse(getCommandBox().isFocused());

        // Check that person list panel is accessible with assigned key presses
        assertTrue(getPersonListPanel().isFocused());
        guiRobot.push(KeyCode.ESCAPE);
        assertTrue(getPersonListPanel().isFocused());
        // Check scrolling
        guiRobot.push(KeyCode.UP);
        assertTrue(getPersonListPanel().isFocused());
        guiRobot.push(KeyCode.DOWN);
        assertTrue(getPersonListPanel().isFocused());
        // Remove focus
        guiRobot.push(KeyCode.ENTER);
        assertFalse(getPersonListPanel().isFocused());
    }

    @Test
    public void executeKeyEventForUndoCommand() {
        KeyCodeCombination undoKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Z");

        guiRobot.push(undoKeyCode);
        assertEquals(UndoCommand.MESSAGE_FAILURE, getResultDisplay().getText());

        getCommandBox().run("delete 1");
        guiRobot.push(undoKeyCode);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
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
}
