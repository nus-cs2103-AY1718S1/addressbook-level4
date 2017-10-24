package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.CalendarWindowHandle;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.HelpCommand;


public class CalendarWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openCalendarWindow() {

        //use command box
        runCommand(CalendarCommand.COMMAND_WORD);
        assertCalendarWindowOpen();

        //using alias
        runCommand(CalendarCommand.COMMAND_ALIAS);
        assertCalendarWindowOpen();

        //using incorrect command
        runCommand(HelpCommand.COMMAND_ALIAS);
        assertCalendarWindowNotOpen();


        runCommand(AddCommand.COMMAND_ALIAS);
        assertCalendarWindowNotOpen();
    }

    /**
     * Asserts that the help window is open, and closes it after checking.
     */
    private void assertCalendarWindowOpen() {
        assertTrue(ERROR_MESSAGE, CalendarWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new CalendarWindowHandle(guiRobot.getStage(CalendarWindowHandle.CALENDAR_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the help window isn't open.
     */
    private void assertCalendarWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, CalendarWindowHandle.isWindowPresent());
    }

}
