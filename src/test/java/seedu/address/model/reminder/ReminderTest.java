package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.testutil.TypicalEvents;

public class ReminderTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void equal_twoSameTag_checkCorrectness() throws Exception {
        Reminder reminder1 = new Reminder((Event) TypicalEvents.EVENT1, TypicalEvents.EVENT1.getName().toString());
        Reminder reminder2 = new Reminder((Event) TypicalEvents.EVENT1, TypicalEvents.EVENT1.getName().toString());

        assertEquals(reminder1, reminder2);
    }

}
