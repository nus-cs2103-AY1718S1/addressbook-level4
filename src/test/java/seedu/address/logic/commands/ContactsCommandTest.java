package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ContactsCommand.SHOWING_CONTACT_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowContactsEvent;
import seedu.address.ui.testutil.EventsCollectorRule;
//@@author huyuanrong
public class ContactsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new ContactsCommand().execute();
        assertEquals(SHOWING_CONTACT_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowContactsEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
