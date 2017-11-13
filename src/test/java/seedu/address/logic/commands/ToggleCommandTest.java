package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ToggleCommand.MESSAGE_TOGGLE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.BrowserAndRemindersPanelToggleEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author justinpoh
public class ToggleCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_toggle_success() {
        CommandResult result = new ToggleCommand().execute();
        assertEquals(MESSAGE_TOGGLE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BrowserAndRemindersPanelToggleEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
