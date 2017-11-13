package seedu.address.logic.commands;

//@@author jeffreygohkw
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.OpenCommand.OPEN_COMMAND_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.OpenRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class OpenCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_exit_success() {
        CommandResult result = new OpenCommand().execute();
        assertEquals(OPEN_COMMAND_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof OpenRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
