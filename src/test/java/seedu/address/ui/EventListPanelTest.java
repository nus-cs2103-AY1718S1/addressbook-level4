package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;
import static seedu.address.ui.testutil.GuiTestAssert.assertEventCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.event.ReadOnlyEvent;

// @@author HouDenghao
public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyEvent> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_EVENT);

    private EventListPanelHandle eventListPanelHandle;

    @Before
    public void setUp() {
        EventListPanel eventListPanel = new EventListPanel(TYPICAL_EVENTS);
        uiPartRule.setUiPart(eventListPanel);

        eventListPanelHandle = new EventListPanelHandle(getChildNode(eventListPanel.getRoot(),
                EventListPanelHandle.EVENT_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_EVENTS.size(); i++) {
            eventListPanelHandle.navigateToCard(TYPICAL_EVENTS.get(i));
            ReadOnlyEvent expectedPerson = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getEventCardHandle(i);

            assertCardDisplaysEvent(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        EventCardHandle expectedCard = eventListPanelHandle.getEventCardHandle(INDEX_SECOND_EVENT.getZeroBased());
        EventCardHandle selectedCard = eventListPanelHandle.getHandleToSelectedCard();
        assertEventCardEquals(expectedCard, selectedCard);
    }
}
