package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.SwitchThemeCommand.MESSAGE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.SwitchThemeEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author junyango

public class SwitchThemeTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_switch_success() {
        CommandResult result = new SwitchThemeCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof SwitchThemeEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}



