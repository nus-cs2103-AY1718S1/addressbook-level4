package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;

//@@author yunpengn
public class SwitchToContactsListEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new SwitchToContactsListEvent();
        assertEquals("SwitchToContactsListEvent", event.toString());
    }
}
