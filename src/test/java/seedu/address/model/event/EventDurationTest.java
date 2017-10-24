package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.junit.Test;

public class EventDurationTest {

    private EventDuration eventDuration1 = new EventDuration(Duration.ofMinutes(90));
    private EventDuration eventDuration2 = new EventDuration(Duration.ofMinutes(90));
    private EventDuration eventDuration3 = new EventDuration(Duration.ofMinutes(120));
    private EventDuration eventDuration4 = new EventDuration(Duration.ofMinutes(30));
    private EventDuration eventDuration5 = new EventDuration(Duration.ofSeconds(30));

    @Test
    public void testEventDurationEquals() {
        //90min = 90min
        assertTrue(eventDuration1.equals(eventDuration2));

        //90min != 120min
        assertFalse(eventDuration1.equals(eventDuration3));

        //Checks if getEventDuration returns correct Duration value
        assertEquals(eventDuration1.getDuration(), Duration.ofMinutes(90));
    }

    @Test
    public void testEventDurationToString() {
        //90min = 1hr30min
        String output = eventDuration1.toString();
        assertEquals("1hr30min", output);

        //120min = 2hr
        output = eventDuration3.toString();
        assertEquals("2hr", output);

        //30min = 30min
        output = eventDuration4.toString();
        assertEquals("30min", output);

        //30sec = 0min
        output = eventDuration5.toString();
        assertEquals("0min", output);

    }

    @Test
    public void testEventDurationHashcode() {
        assertEquals(eventDuration1.hashCode(), Duration.ofMinutes(90).hashCode());
    }
}
