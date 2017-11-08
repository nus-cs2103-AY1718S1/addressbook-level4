package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author fongwz
public class ChooseCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validArgs_success() {
        assertExecutionSuccess("linkedin");
        assertExecutionSuccess("meeting");
    }

    @Test
    public void execute_invalidArgs_failure() {
        assertExecutionFailure("gibberish", Messages.MESSAGE_INVALID_BROWSER_INDEX);
    }

    private void assertExecutionSuccess(String args) {
        ChooseCommand chooseCommand = new ChooseCommand(args);

        try {
            CommandResult commandResult = chooseCommand.execute();
            assertEquals(ChooseCommand.MESSAGE_SUCCESS + args,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Assert Execution Failed: ", ce);
        }
    }

    private void assertExecutionFailure(String args, String expectedMessage) {
        ChooseCommand chooseCommand = new ChooseCommand(args);

        try {
            chooseCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
