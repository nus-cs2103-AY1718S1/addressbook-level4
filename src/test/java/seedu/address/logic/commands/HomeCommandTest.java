package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.HomeCommand.MESSAGE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeInformationPanelRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

// @@author johnweikangong
public class HomeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new HomeCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeInformationPanelRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
