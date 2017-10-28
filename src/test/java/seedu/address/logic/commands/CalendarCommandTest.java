package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CalendarCommand.MESSAGE_DISPLAY_CALENDAR_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code LocateCommand}.
 */
public class CalendarCommandTest {


    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();


    @Test
    public void assertExecutionSuccess() throws CommandException {
        CalendarCommand command = new CalendarCommand();

        try {
            CommandResult commandResult = command.execute();
            assertEquals(MESSAGE_DISPLAY_CALENDAR_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

    }

}


