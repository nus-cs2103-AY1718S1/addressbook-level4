package seedu.address.logic.commands;
//@@author LeeYingZheng
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.FacebookCommand.SHOWING_FACEBOOK_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowFacebookRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class FacebookCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_facebook_success() {
        CommandResult result = new FacebookCommand().execute();
        assertEquals(SHOWING_FACEBOOK_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowFacebookRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
