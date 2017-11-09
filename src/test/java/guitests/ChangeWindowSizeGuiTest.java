package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.ChangeWindowSizeCommand;
import seedu.address.model.windowsize.WindowSize;

//@@author vivekscl
public class ChangeWindowSizeGuiTest extends  AddressBookGuiTest {

    @Test
    public void openWindow() {

        //use menu button
        getMainMenu().clickOnWindowSizesUsingMenu(WindowSize.SMALL_WINDOW_SIZE_INPUT);
        assertChangeWindowSizeByClickingSuccess(WindowSize.SMALL_WINDOW_SIZE_INPUT);

        getMainMenu().clickOnWindowSizesUsingMenu(WindowSize.MEDIUM_WINDOW_SIZE_INPUT);
        assertChangeWindowSizeByClickingSuccess(WindowSize.MEDIUM_WINDOW_SIZE_INPUT);

        getMainMenu().clickOnWindowSizesUsingMenu(WindowSize.BIG_WINDOW_SIZE_INPUT);
        assertChangeWindowSizeByClickingSuccess(WindowSize.BIG_WINDOW_SIZE_INPUT);

        //use command box
        runCommand(ChangeWindowSizeCommand.COMMAND_WORD + " " + WindowSize.SMALL_WINDOW_SIZE_INPUT);
        assertChangeToSmallWindowSizeByCommandWordSuccess();
    }

    /**
     * Asserts that typed out command to change the window size is a success.
     */
    private void assertChangeToSmallWindowSizeByCommandWordSuccess() {
        assertEquals(ChangeWindowSizeCommand.MESSAGE_SUCCESS + WindowSize.SMALL_WIDTH + " x "
                + WindowSize.SMALL_HEIGHT, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }

    /**
     * Asserts that command to change the window size is a success.
     */
    private void assertChangeWindowSizeByClickingSuccess(String windowSize) {
        switch(windowSize) {
        case WindowSize.SMALL_WINDOW_SIZE_INPUT:
            assertTrue(WindowSize.SMALL_WIDTH == getCurrentWindowWidth());
            assertTrue(WindowSize.SMALL_HEIGHT == getCurrentWindowHeight());
            break;
        case WindowSize.MEDIUM_WINDOW_SIZE_INPUT:
            assertTrue(WindowSize.MEDIUM_WIDTH == getCurrentWindowWidth());
            assertTrue(WindowSize.MEDIUM_HEIGHT == getCurrentWindowHeight());
            break;
        case WindowSize.BIG_WINDOW_SIZE_INPUT:
            assertTrue(WindowSize.BIG_WIDTH == getCurrentWindowWidth());
            assertTrue(WindowSize.BIG_HEIGHT == getCurrentWindowHeight());
            break;
        default:
            assert false : "Invalid window size provided";
            break;
        }

        guiRobot.pauseForHuman();
    }

    private double getCurrentWindowWidth() {
        return stage.getWidth();
    }

    private double getCurrentWindowHeight() {
        return stage.getHeight();
    }


}
