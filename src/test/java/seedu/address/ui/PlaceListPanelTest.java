package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.address.testutil.TypicalPlaces.getTypicalPlaces;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPlace;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PlaceCardHandle;
import guitests.guihandles.PlaceListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.place.ReadOnlyPlace;

public class PlaceListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPlace> TYPICAL_PLACES =
            FXCollections.observableList(getTypicalPlaces());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PLACE);

    private PlaceListPanelHandle placeListPanelHandle;

    @Before
    public void setUp() {
        PlaceListPanel placeListPanel = new PlaceListPanel(TYPICAL_PLACES);
        uiPartRule.setUiPart(placeListPanel);

        placeListPanelHandle = new PlaceListPanelHandle(getChildNode(placeListPanel.getRoot(),
                PlaceListPanelHandle.PLACE_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PLACES.size(); i++) {
            placeListPanelHandle.navigateToCard(TYPICAL_PLACES.get(i));
            ReadOnlyPlace expectedPlace = TYPICAL_PLACES.get(i);
            PlaceCardHandle actualCard = placeListPanelHandle.getPlaceCardHandle(i);

            assertCardDisplaysPlace(expectedPlace, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PlaceCardHandle expectedCard = placeListPanelHandle.getPlaceCardHandle(INDEX_SECOND_PLACE.getZeroBased());
        PlaceCardHandle selectedCard = placeListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
