//@@author chilipadiboy
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.ui.testutil.EventsCollectorRule;

public class ThemeCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_theme_change() {
        CommandResult result = new ThemeCommand("light").executeUndoableCommand();
        assertEquals(ThemeCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        CommandResult result2 = new ThemeCommand("dark").executeUndoableCommand();
        assertEquals(ThemeCommand.MESSAGE_SUCCESS, result2.feedbackToUser);
    }
}
