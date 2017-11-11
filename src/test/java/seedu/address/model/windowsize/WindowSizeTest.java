package seedu.address.model.windowsize;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CommandTestUtil;

//@@author vivekscl
public class WindowSizeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void getUserDefinedWindowWidth_validWidth_validResult() {
        assertTrue(WindowSize.SMALL_WIDTH
                == WindowSize.getUserDefinedWindowWidth(WindowSize.SMALL_WINDOW_SIZE_INPUT));
        assertTrue(WindowSize.MEDIUM_WIDTH
                == WindowSize.getUserDefinedWindowWidth(WindowSize.MEDIUM_WINDOW_SIZE_INPUT));
        assertTrue(WindowSize.BIG_WIDTH
                == WindowSize.getUserDefinedWindowWidth(WindowSize.BIG_WINDOW_SIZE_INPUT));
    }

    @Test
    public void getUserDefinedWindowHeight_validHeight_validResult() {
        assertTrue(WindowSize.SMALL_HEIGHT
                == WindowSize.getUserDefinedWindowHeight(WindowSize.SMALL_WINDOW_SIZE_INPUT));
        assertTrue(WindowSize.MEDIUM_HEIGHT
                == WindowSize.getUserDefinedWindowHeight(WindowSize.MEDIUM_WINDOW_SIZE_INPUT));
        assertTrue(WindowSize.BIG_HEIGHT
                == WindowSize.getUserDefinedWindowHeight(WindowSize.BIG_WINDOW_SIZE_INPUT));
    }

    @Test
    public void getUserDefinedWindowWidth_invalidWidth_invalidResult() {
        thrown.expect(AssertionError.class);
        WindowSize.getUserDefinedWindowWidth("");

    }

    @Test
    public void getUserDefinedWindowHeight_invalidHeight_invalidResult() {
        thrown.expect(AssertionError.class);
        WindowSize.getUserDefinedWindowHeight("");
    }
}
