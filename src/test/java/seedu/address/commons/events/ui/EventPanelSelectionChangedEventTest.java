package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT1;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.testutil.EventBuilder;
import seedu.address.ui.event.EventCard;

//@@author yunpengn
public class EventPanelSelectionChangedEventTest {
    private final EventCard card = new EventCard(new EventBuilder().build(), 1);
    private final EventPanelSelectionChangedEvent event = new EventPanelSelectionChangedEvent(card);

    @Test
    @Ignore
    public void createEvent_toString_checkCorrectness() {
        assertEquals("EventPanelSelectionChangedEvent", event.toString());
    }
}
