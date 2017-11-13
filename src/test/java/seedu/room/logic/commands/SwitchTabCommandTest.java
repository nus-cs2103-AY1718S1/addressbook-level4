package seedu.room.logic.commands;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.room.logic.commands.SwitchTabCommand.MESSAGE_SWITCH_TAB_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.room.commons.events.ui.SwitchTabRequestEvent;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.ui.testutil.EventsCollectorRule;

//@@author sushinoya
public class SwitchTabCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_switchtab_success() {
        try {
            CommandResult result = new SwitchTabCommand(1).execute();
            assertEquals(String.format(MESSAGE_SWITCH_TAB_SUCCESS, "Residents"), result.feedbackToUser);
            assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof SwitchTabRequestEvent);
            assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
        } catch (CommandException ce) {
            fail("This should never be called");
        }
    }

    @Test
    public void execute_switchtab_failure() {
        try {
            CommandResult result = new SwitchTabCommand(5).execute();
            fail("This should never be called");
        } catch (CommandException ce) {
            assertEquals(SwitchTabCommand.MESSAGE_USAGE, ce.getMessage());
        }
    }
}
