package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalEvents.EVENT1;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.model.event.Event;
import seedu.address.model.property.PropertyManager;

//@@author junyango
public class ReminderTest {
    private static Event event;
    private static String message;

    @BeforeClass
    public static void setUp() throws Exception {
        PropertyManager.initializePropertyManager();

        event = new Event(EVENT1);
        message = "You have an event";

    }
    @Test
    public void createReminder_preDefinedFieldsPresent_checkCorrectness() throws Exception {
        Reminder reminder = new Reminder(event, message);
        assertNotNull(reminder);

        assertEquals(event, reminder.getEvent());
        assertEquals(message, reminder.getMessage());

    }

    @Test
    public void setMessage_test_checkCorrectness() {
        Reminder reminder = new Reminder(event, message);
        assertNotNull(reminder);

        reminder.setMessage("test");
        assertEquals("test", reminder.getMessage());
        assertEquals("Message: test", reminder.getAsText());
    }

    @Test
    public void equal_twoSameTag_checkCorrectness() throws Exception {
        Reminder reminder1 = new Reminder((Event) EVENT1, EVENT1.getName().toString());
        Reminder reminder2 = new Reminder((Event) EVENT1, EVENT1.getName().toString());

        assertEquals(reminder1, reminder2);
    }

}
