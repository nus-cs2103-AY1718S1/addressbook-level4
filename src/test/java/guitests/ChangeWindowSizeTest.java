package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.ChangeWindowSizeCommand;

//@@author vivekscl
public class ChangeWindowSizeTest extends  AddressBookGuiTest {

    @Test
    public void openWindow() {

        //use menu button
        getMainMenu().clickOnWindowSizesUsingMenu(ChangeWindowSizeCommand.SMALL_WINDOW_SIZE_PARAM);
        assertChangeWindowSizeByClickingSuccess(ChangeWindowSizeCommand.SMALL_WINDOW_SIZE_PARAM);

        getMainMenu().clickOnWindowSizesUsingMenu(ChangeWindowSizeCommand.MEDIUM_WINDOW_SIZE_PARAM);
        assertChangeWindowSizeByClickingSuccess(ChangeWindowSizeCommand.MEDIUM_WINDOW_SIZE_PARAM);

        getMainMenu().clickOnWindowSizesUsingMenu(ChangeWindowSizeCommand.BIG_WINDOW_SIZE_PARAM);
        assertChangeWindowSizeByClickingSuccess(ChangeWindowSizeCommand.BIG_WINDOW_SIZE_PARAM);

        //use command box
        runCommand(ChangeWindowSizeCommand.COMMAND_WORD + " " + ChangeWindowSizeCommand.SMALL_WINDOW_SIZE_PARAM);
        assertChangeToSmallWindowSizeByCommandWordSuccess();
    }

    /**
     * Asserts that typed out command to change the window size is a success.
     */
    private void assertChangeToSmallWindowSizeByCommandWordSuccess() {
        assertEquals(ChangeWindowSizeCommand.MESSAGE_SUCCESS + ChangeWindowSizeCommand.SMALL_WIDTH + " x "
            + ChangeWindowSizeCommand.SMALL_HEIGHT, getResultDisplay().getText());
        guiRobot.pauseForHuman();
    }

    /**
     * Asserts that command to change the window size is a success.
     */
    private void assertChangeWindowSizeByClickingSuccess(String windowSize) {
        switch(windowSize) {
        case ChangeWindowSizeCommand.SMALL_WINDOW_SIZE_PARAM:
            assertTrue(ChangeWindowSizeCommand.SMALL_WIDTH == getCurrentWindowWidth());
            assertTrue(ChangeWindowSizeCommand.SMALL_HEIGHT == getCurrentWindowHeight());
            break;
        case ChangeWindowSizeCommand.MEDIUM_WINDOW_SIZE_PARAM:
            assertTrue(ChangeWindowSizeCommand.MEDIUM_WIDTH == getCurrentWindowWidth());
            assertTrue(ChangeWindowSizeCommand.MEDIUM_HEIGHT == getCurrentWindowHeight());
            break;
        case ChangeWindowSizeCommand.BIG_WINDOW_SIZE_PARAM:
            assertTrue(ChangeWindowSizeCommand.BIG_WIDTH == getCurrentWindowWidth());
            assertTrue(ChangeWindowSizeCommand.BIG_HEIGHT == getCurrentWindowHeight());
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
