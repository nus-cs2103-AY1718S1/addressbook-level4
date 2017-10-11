package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.ThemesWindowHandle;
import seedu.address.logic.commands.ThemeListCommand;

public class ThemesWindowTest extends AddressBookGuiTest {

    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openHelpWindow() {
        //use command box
        runCommand(ThemeListCommand.COMMAND_WORD);
        assertThemesWindowOpen();
    }

    /**
     * Asserts that the help window is open, and closes it after checking.
     */
    private void assertThemesWindowOpen() {
        assertTrue(ERROR_MESSAGE, ThemesWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new ThemesWindowHandle(guiRobot.getStage(ThemesWindowHandle.THEMES_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }
}
