package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.ModelManager.deliveredPredicate;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PARCEL;
import static seedu.address.testutil.TypicalParcels.getTypicalParcels;
import static seedu.address.ui.ParcelListPanel.INDEX_FIRST_TAB;
import static seedu.address.ui.ParcelListPanel.INDEX_SECOND_TAB;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysParcel;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ParcelCardHandle;
import guitests.guihandles.ParcelListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.Status;

public class ParcelListPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyParcel> TYPICAL_DELIVERED_PARCELS =
            FXCollections.observableList(getTypicalParcels().stream().filter(deliveredPredicate)
                    .collect(Collectors.toList()));
    private static final ObservableList<ReadOnlyParcel> TYPICAL_UNDELIVERED_PARCELS =
            FXCollections.observableList(getTypicalParcels().stream().filter(deliveredPredicate.negate())
                    .collect(Collectors.toList()));


    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PARCEL);
    private static final JumpToTabRequestEvent JUMP_TO_SECOND_TAB_EVENT = new JumpToTabRequestEvent(INDEX_SECOND_TAB);
    private static final JumpToTabRequestEvent JUMP_TO_FIRST_TAB_EVENT = new JumpToTabRequestEvent(INDEX_FIRST_TAB);

    private ParcelListPanelHandle parcelListPanelHandle;
    private ParcelListPanel parcelListPanel;

    @Before
    public void setUp() {
        parcelListPanel = new ParcelListPanel(TYPICAL_UNDELIVERED_PARCELS, TYPICAL_DELIVERED_PARCELS);
        uiPartRule.setUiPart(parcelListPanel);

        parcelListPanelHandle = new ParcelListPanelHandle(getChildNode(parcelListPanel.getRoot(),
                ParcelListPanelHandle.UNDELIVERED_PARCEL_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        postNow(JUMP_TO_SECOND_TAB_EVENT);
        guiRobot.pauseForHuman();

        parcelListPanelHandle = new ParcelListPanelHandle(getChildNode(parcelListPanel.getRoot(),
                ParcelListPanelHandle.DELIVERED_PARCEL_LIST_VIEW_ID));
        for (int i = 0; i < TYPICAL_DELIVERED_PARCELS.size(); i++) {
            parcelListPanelHandle.navigateToCard(TYPICAL_DELIVERED_PARCELS.get(i));
            ReadOnlyParcel expectedParcel = TYPICAL_DELIVERED_PARCELS.get(i);
            ParcelCardHandle actualCard = parcelListPanelHandle.getParcelCardHandle(i);

            assertCardDisplaysParcel(expectedParcel, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }

        postNow(JUMP_TO_FIRST_TAB_EVENT);
        guiRobot.pauseForHuman();

        parcelListPanelHandle = new ParcelListPanelHandle(getChildNode(parcelListPanel.getRoot(),
                ParcelListPanelHandle.UNDELIVERED_PARCEL_LIST_VIEW_ID));
        for (int i = 0; i < TYPICAL_UNDELIVERED_PARCELS.size(); i++) {
            parcelListPanelHandle.navigateToCard(TYPICAL_UNDELIVERED_PARCELS.get(i));
            ReadOnlyParcel expectedParcel = TYPICAL_UNDELIVERED_PARCELS.get(i);
            ParcelCardHandle actualCard = parcelListPanelHandle.getParcelCardHandle(i);

            assertCardDisplaysParcel(expectedParcel, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ParcelCardHandle expectedCard = parcelListPanelHandle.getParcelCardHandle(INDEX_SECOND_PARCEL.getZeroBased());
        ParcelCardHandle selectedCard = parcelListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
