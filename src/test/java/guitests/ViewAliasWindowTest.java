package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.ViewAliasWindowHandle;
import seedu.address.logic.commands.ViewAliasCommand;

public class ViewAliasWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openViewAliasWindow() {
        //use menu button
        getMainMenu().openViewAliasWindowUsingMenu();
        assertViewAliasWindowOpen();

        //use accelerator
        getCommandBox().click();
        getMainMenu().openViewAliasWindowUsingAccelerator();
        assertViewAliasWindowOpen();

        getResultDisplay().click();
        getMainMenu().openViewAliasWindowUsingAccelerator();
        assertViewAliasWindowOpen();

        //use command box
        runCommand(ViewAliasCommand.COMMAND_WORD);
        assertViewAliasWindowOpen();
    }

    /**
     * Asserts that the view alias window is open, and closes it after checking.
     */
    private void assertViewAliasWindowOpen() {
        System.out.println(ViewAliasWindowHandle.isWindowPresent());
        assertTrue(ERROR_MESSAGE, ViewAliasWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new ViewAliasWindowHandle(guiRobot.getStage(ViewAliasWindowHandle.VIEWALIAS_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the view alias window isn't open.
     */
    private void assertViewAliasWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, ViewAliasWindowHandle.isWindowPresent());
    }

}
