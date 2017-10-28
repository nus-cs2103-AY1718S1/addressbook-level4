package guitests;

import guitests.guihandles.CalendarWindowHandle;
import guitests.guihandles.EmailWindowHandle;
import org.junit.Test;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EmailCommand;


import static org.junit.Assert.assertFalse;


public class EmailWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openEmailWindow() {

        //using incorrect command
        runCommand(CalendarCommand.COMMAND_ALIAS);
        assertEmailWindowOpen();

        runCommand(ClearCommand.COMMAND_ALIAS);
        assertEmailWindowOpen();

        runCommand(EmailCommand.COMMAND_ALIAS);
        assertEmailWindowOpen();
    }

    /**
     * Asserts if email window is open.
     */


    private void assertEmailWindowOpen() {
        assertFalse(ERROR_MESSAGE, EmailWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

    }


}

