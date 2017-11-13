package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.TogglePanelEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ToggleCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void executeToggleSuccess() throws Exception {
        CommandResult result = new ToggleCommand().execute();
        assertEquals(ToggleCommand.MESSAGE_TOGGLE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof TogglePanelEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
