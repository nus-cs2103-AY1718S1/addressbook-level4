//@@author chilipadiboy
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.BirthdayAlarmCommand.SHOWING_REMINDERS_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowBirthdayAlarmRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class BirthdayAlarmCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_birthdayalarm_success() {
        CommandResult result = new BirthdayAlarmCommand().execute();
        assertEquals(SHOWING_REMINDERS_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowBirthdayAlarmRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
