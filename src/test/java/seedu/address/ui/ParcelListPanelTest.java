package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ParcelListPanelHandle;
import guitests.guihandles.ParcelCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.parcel.ReadOnlyParcel;

public class ParcelListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyParcel> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private ParcelListPanelHandle parcelListPanelHandle;

    @Before
    public void setUp() {
        ParcelListPanel parcelListPanel = new ParcelListPanel(TYPICAL_PERSONS);
        uiPartRule.setUiPart(parcelListPanel);

        parcelListPanelHandle = new ParcelListPanelHandle(getChildNode(parcelListPanel.getRoot(),
                ParcelListPanelHandle.PARCEL_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            parcelListPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            ReadOnlyParcel expectedPerson = TYPICAL_PERSONS.get(i);
            ParcelCardHandle actualCard = parcelListPanelHandle.getParcelCardHandle(i);

            assertCardDisplaysPerson(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ParcelCardHandle expectedCard = parcelListPanelHandle.getParcelCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        ParcelCardHandle selectedCard = parcelListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
