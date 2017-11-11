package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalEvents.ZOUKOUT;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.EventsDetailsPanelHandle;
import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;

//@@author DarrenCzen
public class EventsDetailsPanelTest extends GuiUnitTest {
    private static final String MESSAGE_EMPTY_STRING = "";

    private EventPanelSelectionChangedEvent selectionChangedEventStub;

    private EventsDetailsPanel eventsDetailsPanel;
    private EventsDetailsPanelHandle eventsDetailsPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new EventPanelSelectionChangedEvent(new EventCard(ZOUKOUT, 0));

        guiRobot.interact(() -> eventsDetailsPanel = new EventsDetailsPanel());
        uiPartRule.setUiPart(eventsDetailsPanel);

        eventsDetailsPanelHandle = new EventsDetailsPanelHandle(eventsDetailsPanel.getRoot());
    }

    @Test
    public void testDisplay() throws Exception {
        //Assuming nothing has been clicked yet.
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getName());
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getDate());
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getDateField());
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getAddress());
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getAddressField());
        eventsDetailsPanelHandle.rememberSelectedEventDetails();

        //Selecting of ZoukOut EventCard.
        postNow(selectionChangedEventStub);
        assertTrue(eventsDetailsPanelHandle.isSelectedEventChanged());
        eventsDetailsPanelHandle.rememberSelectedEventDetails();
    }

    @Test
    public void testEquals() {
        EventsDetailsPanel eventsDetailsPanelX = new EventsDetailsPanel();
        EventsDetailsPanel eventsDetailsPanelY = new EventsDetailsPanel();

        //Both panels are initially blank
        assertTrue(eventsDetailsPanelX.equals(eventsDetailsPanelY));

        //Panel X is loaded with event info while Panel Y is not
        eventsDetailsPanelX.loadEventInfo(ZOUKOUT);
        assertFalse(eventsDetailsPanelX.equals(eventsDetailsPanelY));

        //Panel Y is loaded with same event info
        eventsDetailsPanelY.loadEventInfo(ZOUKOUT);
        assertTrue(eventsDetailsPanelX.equals(eventsDetailsPanelY));
        assertTrue(eventsDetailsPanelX.equals(eventsDetailsPanelX));
        assertFalse(eventsDetailsPanelX.equals(""));
    }
}
