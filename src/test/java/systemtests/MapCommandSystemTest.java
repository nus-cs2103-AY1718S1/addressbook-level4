package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.HelpWindowHandle;
import guitests.guihandles.MapWindowHandle;

import seedu.address.logic.commands.MapCommand;
import seedu.address.ui.MapWindow;

//@@author liliwei25
public class MapCommandSystemTest extends AddressBookSystemTest {
    public static final String VALID_INDEX = " 1";
    public static final String INVALID_INDEX = " 0";
    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openHelpWindow() {
        //use command box
        executeCommand(MapCommand.COMMAND_WORD);
        assertMapWindowNotOpen();

        executeCommand(MapCommand.COMMAND_WORD + VALID_INDEX);
        assertMapWindowOpen();

        executeCommand(MapCommand.COMMAND_WORD + INVALID_INDEX);
        assertMapWindowNotOpen();
    }

    /**
     * Asserts that the map window is open, and closes it after checking.
     */
    private void assertMapWindowOpen() {
        assertTrue(MapWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new MapWindowHandle(guiRobot.getStage(MapWindow.TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the map window isn't open.
     */
    private void assertMapWindowNotOpen() {
        assertFalse(HelpWindowHandle.isWindowPresent());
    }
}
