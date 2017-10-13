package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import seedu.address.commons.events.ui.ColorKeywordEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ColorKeywordCommand.DISABLE_COLOR;
import static seedu.address.logic.commands.ColorKeywordCommand.ENABLE_COLOR;
import static seedu.address.logic.commands.ColorKeywordCommand.MESSAGE_SUCCESS;

public class ColorKeywordCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_enableColorCommand_success() {
        CommandResult result = new ColorKeywordCommand("enable").execute();
        assertEquals(ENABLE_COLOR + MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ColorKeywordEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_disableColorCommand_success() {
        CommandResult result = new ColorKeywordCommand("disable").execute();
        assertEquals(DISABLE_COLOR + MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ColorKeywordEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

}
