package guitests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import guitests.guihandles.CalendarWindowHandle;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.HelpCommand;

//@@author jin-ting
public class CalendarWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openCalendarWindow() {

        //using incorrect command
        runCommand(HelpCommand.COMMAND_ALIAS);
        assertCalendarWindowOpen();


        runCommand(AddCommand.COMMAND_ALIAS);
        assertCalendarWindowOpen();

        runCommand(CalendarCommand.COMMAND_ALIAS);
        assertCalendarWindowOpen();

    }


    /**
     * Asserts if calendar window is open.
     */


    private void assertCalendarWindowOpen() {

        assertFalse(ERROR_MESSAGE, CalendarWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();


    }

}

