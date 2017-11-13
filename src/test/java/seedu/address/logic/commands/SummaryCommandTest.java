//@@author jshoung
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.SummaryCommand.SHOWING_SUMMARY_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowSummaryRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SummaryCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_summary_success() {
        CommandResult result = new SummaryCommand().execute();
        assertEquals(SHOWING_SUMMARY_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowSummaryRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
    }
}
