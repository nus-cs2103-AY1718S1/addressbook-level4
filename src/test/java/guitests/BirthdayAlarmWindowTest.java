//@@author chilipadiboy
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.BirthdayAlarmWindowHandle;
import seedu.address.logic.commands.BirthdayAlarmCommand;




public class BirthdayAlarmWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
        + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
        + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
        + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openBirthdayAlarmWindow() {
        //use menu button
        getMainMenu().openBirthdayAlarmWindowUsingMenu();
        assertBirthdayAlarmWindowOpen();

        //use command box
        runCommand(BirthdayAlarmCommand.COMMAND_WORD);
        assertBirthdayAlarmWindowOpen();
    }

    /**
     * Asserts that the Birthday Alarm window is open, and closes it after checking.
     */
    private void assertBirthdayAlarmWindowOpen() {
        assertTrue(ERROR_MESSAGE, BirthdayAlarmWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new BirthdayAlarmWindowHandle(guiRobot.getStage(BirthdayAlarmWindowHandle.BIRTHDAYALARM_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }
}
