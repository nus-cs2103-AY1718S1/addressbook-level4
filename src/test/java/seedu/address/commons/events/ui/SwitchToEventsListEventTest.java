package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;

//@@author yunpengn
public class SwitchToEventsListEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new SwitchToEventsListEvent();
        assertEquals("SwitchToEventsListEvent", event.toString());
    }
}
