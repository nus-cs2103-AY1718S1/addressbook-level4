package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.testutil.EventBuilder;

// @@author HouDenghao
public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no change made to Event
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 1);
        uiPartRule.setUiPart(eventCard);
        assertEventCardDisplay(eventCard, event, 1);

        // changes made to Event reflects on card
        guiRobot.interact(() -> {
            event.setEventName(FIRST.getEventName());
            event.setEventDescription(FIRST.getDescription());
            event.setETime(FIRST.getEventTime());
        });
        assertEventCardDisplay(eventCard, event, 1);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 1);

        // same event, same index -> returns true
        EventCard copy = new EventCard(event, 1);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard == null);

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different events, same index -> returns false
        Event differentEvent = new EventBuilder().withName("differentName").build();
        assertFalse(eventCard.equals(new EventCard(differentEvent, 1)));

        // same event, different indexes -> returns false
        assertFalse(eventCard.equals(new EventCard(event, 2)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedEvent} correctly
     * and matches {@code expectedId}.
     */
    private void assertEventCardDisplay(EventCard eventCard, ReadOnlyEvent expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysEvent(expectedEvent, eventCardHandle);
    }
}
