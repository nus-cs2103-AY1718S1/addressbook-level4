package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.JumpToBrowserListRequestEvent;
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
        assertExecutionFailure("badargs", Messages.MESSAGE_INVALID_BROWSER_INDEX);
    }

    /**
     * Executes a {@code ChooseCommand} with the given {@code arguments},
     * and checks that {@code JumpToBrowserListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(String args) {
        ChooseCommand chooseCommand = new ChooseCommand(args);

        try {
            CommandResult commandResult = chooseCommand.execute();
            assertEquals(ChooseCommand.MESSAGE_SUCCESS + args,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Assert Execution Failed: ", ce);
        }

        JumpToBrowserListRequestEvent event =
                (JumpToBrowserListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(args, event.browserItem);
    }

    /**
     * Executes a {@code ChooseCommand} with the given {@code arguments},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
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
