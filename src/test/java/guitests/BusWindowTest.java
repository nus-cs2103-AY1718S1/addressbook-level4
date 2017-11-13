//@@author jshoung

package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.BusWindowHandle;
import seedu.address.logic.commands.BusCommand;

public class BusWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openBusWindow() {
        //use command box
        runCommand(BusCommand.COMMAND_WORD);
        assertBusWindowOpen();
    }

    /**
     * Asserts that the bus window is open, and closes it after checking.
     */
    private void assertBusWindowOpen() {
        assertTrue(ERROR_MESSAGE, BusWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new BusWindowHandle(guiRobot.getStage(BusWindowHandle.BUS_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the bus window isn't open. For future use.
     */
    private void assertBusWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, BusWindowHandle.isWindowPresent());
    }

}
