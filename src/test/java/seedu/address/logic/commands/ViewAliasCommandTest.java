package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ViewAliasCommand.MESSAGE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewAliasCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_viewalias_success() {
        CommandResult result = new ViewAliasCommand().executeUndoableCommand();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
    }
}
