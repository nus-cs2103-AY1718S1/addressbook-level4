package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.UserProfileWindowHandle;

//@@author bladerail
public class UserProfileWindowTest extends AddressBookGuiTest {

    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openUserProfileWindow() {
        //use accelerator to open and close as per normal
        getCommandBox().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseDefault();

        getResultDisplay().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseDefault();

        getPersonListPanel().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseDefault();

        getBrowserPanel().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowNotOpen();

        //use menu button
        getMainMenu().openUserProfileWindowUsingMenu();
        assertUserProfileWindowOpenThenCloseDefault();
    }

    @Test
    public void closeUserProfileWindow() {
        //use accelerator to open and close with accelerator
        getCommandBox().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseAccelerator();

        getResultDisplay().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseAccelerator();

        getPersonListPanel().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseAccelerator();

        getBrowserPanel().click();
        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowNotOpen();

        //use menu button to open, close with accelerator
        getMainMenu().openUserProfileWindowUsingMenu();
        assertUserProfileWindowOpenThenCloseAccelerator();
    }

    @Test
    public void closeUserProfileWindowByClickingOk() {
        getMainMenu().openUserProfileWindowUsingMenu();
        assertUserProfileWindowOpenThenCloseOk();
        assertUserProfileWindowNotOpen();

        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseOk();
        assertUserProfileWindowNotOpen();
    }

    @Test
    public void closeUserProfileWindowByClickingCancel() {
        getMainMenu().openUserProfileWindowUsingMenu();
        assertUserProfileWindowOpenThenCloseCancel();
        assertUserProfileWindowNotOpen();

        getMainMenu().openUserProfileWindowUsingAccelerator();
        assertUserProfileWindowOpenThenCloseCancel();
        assertUserProfileWindowNotOpen();
    }

    /**
     * Asserts that the help window is open, then closes it using the default close method
     */
    private void assertUserProfileWindowOpenThenCloseDefault() {
        assertTrue(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new UserProfileWindowHandle(
                guiRobot.getStage(UserProfileWindowHandle.USERPROFILE_WINDOW_TITLE)
        ).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the UserProfile window is open, then closes it using the accelerator
     */
    private void assertUserProfileWindowOpenThenCloseAccelerator() {
        assertTrue(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());

        new UserProfileWindowHandle(
                guiRobot.getStage(UserProfileWindowHandle.USERPROFILE_WINDOW_TITLE)
        ).closeUserProfileWindowUsingCancelAccelerator();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the help window is open, then closes it using the ok button
     */
    private void assertUserProfileWindowOpenThenCloseOk() {
        assertTrue(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new UserProfileWindowHandle(
                guiRobot.getStage(UserProfileWindowHandle.USERPROFILE_WINDOW_TITLE)
        ).clickOk();
        mainWindowHandle.focus();
    }


    /**
     * Asserts that the help window is open, then closes it using the ok button
     */
    private void assertUserProfileWindowOpenThenCloseCancel() {
        assertTrue(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new UserProfileWindowHandle(
                guiRobot.getStage(UserProfileWindowHandle.USERPROFILE_WINDOW_TITLE)
        ).clickCancel();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the UserProfile window isn't open.
     */
    private void assertUserProfileWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, UserProfileWindowHandle.isWindowPresent());
    }
}
