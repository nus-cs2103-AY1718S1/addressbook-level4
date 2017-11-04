package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.TabCommand.MESSAGE_SELECT_TAB_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.JumpToTabRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class TabCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();


    @Test
    public void execute_help_success() throws CommandException {
        CommandResult result = new TabCommand(INDEX_ONE).execute();
        assertEquals(MESSAGE_SELECT_TAB_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof JumpToTabRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
