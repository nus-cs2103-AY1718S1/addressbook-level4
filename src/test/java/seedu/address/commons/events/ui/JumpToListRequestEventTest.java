package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

public class JumpToListRequestEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new JumpToListRequestEvent(Index.fromOneBased(5));
        assertEquals("JumpToListRequestEvent", event.toString());
    }
}
