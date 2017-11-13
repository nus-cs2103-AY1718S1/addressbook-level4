package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.AboutWindowHandle;
import seedu.address.logic.commands.AboutCommand;

//@@author yewshengkai
public class AboutWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openAboutWindow() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openAboutWindowUsingAccelerator();
        assertAboutWindowOpen();

        getResultDisplay().click();
        getMainMenu().openAboutWindowUsingAccelerator();
        assertAboutWindowOpen();

        getPersonListPanel().click();
        getMainMenu().openAboutWindowUsingAccelerator();
        assertAboutWindowOpen();

        getBrowserPanel().click();
        getMainMenu().openAboutWindowUsingAccelerator();
        assertAboutWindowNotOpen();

        //use menu button
        getMainMenu().openAboutWindowUsingMenu();
        assertAboutWindowOpen();

        //use command box
        runCommand(AboutCommand.COMMAND_WORD);
        assertAboutWindowOpen();
    }

    /**
     * Asserts that the about window is open, and closes it after checking.
     */
    private void assertAboutWindowOpen() {
        assertTrue(ERROR_MESSAGE, AboutWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new AboutWindowHandle(guiRobot.getStage(AboutWindowHandle.ABOUT_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the about window isn't open.
     */
    private void assertAboutWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, AboutWindowHandle.isWindowPresent());
    }

}
