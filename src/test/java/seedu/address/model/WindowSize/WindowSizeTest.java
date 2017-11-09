package seedu.address.model.WindowSize;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.windowsize.WindowSize;

//@@author vivekscl
public class WindowSizeTest {

    @Test
    public void isValidWindowSize() {
        // invalid window sizes
        assertFalse(WindowSize.isValidWindowSize("")); // empty string
        assertFalse(WindowSize.isValidWindowSize(" ")); // spaces only
        assertFalse(WindowSize.isValidWindowSize(CommandTestUtil.INVALID_WINDOW_SIZE_INPUT)); // invalid window size

        // valid window sizes
        assertTrue(WindowSize.isValidWindowSize(WindowSize.SMALL_WINDOW_SIZE_INPUT));
        assertTrue(WindowSize.isValidWindowSize(WindowSize.MEDIUM_WINDOW_SIZE_INPUT));
        assertTrue(WindowSize.isValidWindowSize(WindowSize.BIG_WINDOW_SIZE_INPUT));
    }
}
