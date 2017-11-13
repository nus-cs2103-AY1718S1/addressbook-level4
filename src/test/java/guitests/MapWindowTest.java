//@@author jshoung

package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.MapWindowHandle;
import seedu.address.logic.commands.MapCommand;

public class MapWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openMapWindow() {
        //use command box
        runCommand(MapCommand.COMMAND_WORD);
        assertMapWindowOpen();
    }

    /**
     * Asserts that the map window is open, and closes it after checking.
     */
    private void assertMapWindowOpen() {
        assertTrue(ERROR_MESSAGE, MapWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new MapWindowHandle(guiRobot.getStage(MapWindowHandle.MAP_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the map window isn't open. For future use.
     */
    private void assertMapWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, MapWindowHandle.isWindowPresent());
    }

}
