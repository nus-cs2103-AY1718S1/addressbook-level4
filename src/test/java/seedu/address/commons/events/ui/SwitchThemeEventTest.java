package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;

//@@author junyango
public class SwitchThemeEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new SwitchThemeEvent();
        assertEquals("SwitchThemeEvent", event.toString());
    }
}
