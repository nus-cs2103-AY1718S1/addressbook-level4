package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;

public class MainWindowTest extends AddressBookGuiTest {

    @Test
    public void handleKeyListeners() {
        // Check that command box is accessible with assigned key presses
        guiRobot.push(KeyCode.ENTER);
        assertTrue(mainWindowHandle.getCommandBox().isFocused());
        guiRobot.push(KeyCode.A);
        assertTrue(mainWindowHandle.getCommandBox().isFocused());
        // Remove focus
        guiRobot.push(KeyCode.RIGHT);
        assertFalse(mainWindowHandle.getCommandBox().isFocused());

        // Check that result display panel is accessible with assigned key presses
        assertTrue(mainWindowHandle.getResultDisplay().isFocused());
        // Check scrolling
        guiRobot.push(KeyCode.UP);
        assertTrue(mainWindowHandle.getResultDisplay().isFocused());
        guiRobot.push(KeyCode.DOWN);
        assertTrue(mainWindowHandle.getResultDisplay().isFocused());
        // Remove focus
        guiRobot.push(KeyCode.LEFT);
        assertFalse(mainWindowHandle.getResultDisplay().isFocused());

        // Check that person list panel is accessible with assigned key presses
        assertTrue(mainWindowHandle.getPersonListPanel().isFocused());
        guiRobot.push(KeyCode.ESCAPE);
        assertTrue(mainWindowHandle.getPersonListPanel().isFocused());
        // Check scrolling
        guiRobot.push(KeyCode.UP);
        assertTrue(mainWindowHandle.getPersonListPanel().isFocused());
        guiRobot.push(KeyCode.DOWN);
        assertTrue(mainWindowHandle.getPersonListPanel().isFocused());
        // Remove focus
        guiRobot.push(KeyCode.ENTER);
        assertFalse(mainWindowHandle.getPersonListPanel().isFocused());
    }
}
