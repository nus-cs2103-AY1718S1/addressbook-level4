package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HelpOverlayTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openHelpOverlay() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayOpen();
        getMainMenu().closeHelpOverlayUsingAccelerator();

        getSortMenu().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayOpen();
        getCommandBox().click();
        getMainMenu().closeHelpOverlayUsingAccelerator();

        getSearchField().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayOpen();
        getMainMenu().closeHelpOverlayUsingAccelerator();

        getResultDisplay().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayOpen();
        getMainMenu().closeHelpOverlayUsingAccelerator();

        getPersonListPanel().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayNotOpen();

        getBrowserPanel().click();
        getMainMenu().openHelpOverlayUsingAccelerator();
        assertHelpOverlayNotOpen();
    }


    /**
     * Asserts that the help overlay is open, and closes it after checking.
     */
    private void assertHelpOverlayOpen() {
        assertTrue(ERROR_MESSAGE, mainWindowHandle.getHelpOverlay().isOverlayPresent());
        guiRobot.pauseForHuman();
    }

    /**
     * Asserts that the help overlay is open, and closes it after checking.
     */
    private void assertHelpOverlayNotOpen() {
        assertFalse(ERROR_MESSAGE, mainWindowHandle.getHelpOverlay().isOverlayPresent());
        guiRobot.pauseForHuman();
    }
}
