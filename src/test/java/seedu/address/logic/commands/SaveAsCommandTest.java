package seedu.address.logic.commands;

//@@author jeffreygohkw
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.SaveAsCommand.SAVE_AS_COMMAND_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.SaveAsRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SaveAsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_exit_success() {
        CommandResult result = new SaveAsCommand().execute();
        assertEquals(SAVE_AS_COMMAND_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof SaveAsRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
