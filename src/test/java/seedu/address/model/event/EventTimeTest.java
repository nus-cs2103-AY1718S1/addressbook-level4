package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.junit.Test;

public class EventTimeTest {

    @Test
    public void testEventTimeCreationSuccess() {
        LocalDateTime now = LocalDateTime.now();
        Duration d = Duration.ofMinutes(90);

        EventTime time1 = new EventTime(now, d);
        EventTime time2 = new EventTime(now, d);
        EventTime time3 = new EventTime(now, Duration.ofMinutes(10));
        EventTime time4 = new EventTime(now.plus(d), d);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        assertEquals(now.format(formatter), time1.toString());
        assertEquals(now, time1.getStart());
        assertEquals(now.plus(d), time1.getEnd());
        assertEquals(Objects.hash(now, d), time1.hashCode());

        assertTrue(time1.equals(time2));
        assertFalse(time1.equals(time3));
        assertFalse(time1.equals(time4));
    }

    @Test
    public void testEventTimeUpcoming() {

        EventTime futureEvent = new EventTime(LocalDateTime.now().plus(Duration.ofDays(3)),
                Duration.ofMinutes(10));
        EventTime pastEvent = new EventTime(LocalDateTime.now().minus(Duration.ofDays(3)),
                Duration.ofMinutes(10));

        assertTrue(futureEvent.isUpcoming());
        assertFalse(pastEvent.isUpcoming());


    }
}
