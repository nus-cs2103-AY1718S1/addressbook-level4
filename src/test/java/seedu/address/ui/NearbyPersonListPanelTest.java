package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.NearbyPersonListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;

//@@author khooroko
public class NearbyPersonListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToNearbyListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToNearbyListRequestEvent(INDEX_SECOND_PERSON);

    private NearbyPersonListPanelHandle nearbyPersonListPanelHandle;

    @Before
    public void setUp() {
        NearbyPersonListPanel nearbyPersonListPanel = new NearbyPersonListPanel(TYPICAL_PERSONS, BENSON);
        uiPartRule.setUiPart(nearbyPersonListPanel);
        nearbyPersonListPanelHandle = new NearbyPersonListPanelHandle(getChildNode(nearbyPersonListPanel.getRoot(),
                NearbyPersonListPanelHandle.NEARBY_PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        ReadOnlyPerson expectedPerson;
        PersonCardHandle actualCard;

        nearbyPersonListPanelHandle.navigateToCard(BENSON);
        expectedPerson = BENSON;
        actualCard = nearbyPersonListPanelHandle.getPersonCardHandle(0);

        assertCardDisplaysPerson(expectedPerson, actualCard);
        assertEquals(Integer.toString(1) + ". ", actualCard.getId());

        nearbyPersonListPanelHandle.navigateToCard(CARL);
        expectedPerson = BENSON;
        actualCard = nearbyPersonListPanelHandle.getPersonCardHandle(0);

        assertCardDisplaysPerson(expectedPerson, actualCard);
        assertEquals(Integer.toString(1) + ". ", actualCard.getId());
    }

    @Test
    public void handleJumpToNearbyListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PersonCardHandle expectedCard =
                nearbyPersonListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedCard = nearbyPersonListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
