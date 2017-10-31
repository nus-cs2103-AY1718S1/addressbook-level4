package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.testutil.EventBuilder;

//@@author a0107442n

public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {

        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 2);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, event, 2);

        // changes made to Event reflects on card
        guiRobot.interact(() -> {
            event.setTitle(BIRTHDAY.getTitle());
            event.setTimeslot(BIRTHDAY.getTimeslot());
            event.setDescription(BIRTHDAY.getDescription());
        });
        assertCardDisplay(eventCard, event, 2);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 0);

        // same event, same index -> returns true
        EventCard copy = new EventCard(event, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different event, same index -> returns false
        Event differentEvent = new EventBuilder().withTitle("differentTitle")
                .build();
        assertFalse(eventCard.equals(new EventCard(differentEvent, 0)));

        // same event, different index -> returns false
        assertFalse(eventCard.equals(new EventCard(event, 1)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code
     * expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, ReadOnlyEvent expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysEvent(expectedEvent, eventCardHandle);
    }
}
