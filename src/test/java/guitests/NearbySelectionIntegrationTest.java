package guitests;

import static org.junit.Assert.assertNotSame;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertInfoDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.InfoPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.NearbyPersonNotInCurrentListEvent;
import seedu.address.commons.events.ui.NearbyPersonPanelSelectionChangedEvent;
import seedu.address.ui.PersonCard;

//@@author khooroko
public class NearbySelectionIntegrationTest extends AddressBookGuiTest {

    @Test
    public void handleNearbyPersonNotInCurrentListEvent() {
        NearbyPersonNotInCurrentListEvent nearbyPersonNotInCurrentListEventStub =
                new NearbyPersonNotInCurrentListEvent(new PersonCard(CARL, 2));
        PersonListPanelHandle originalPersonListPanelHandle = mainWindowHandle.getPersonListPanel();
        InfoPanelHandle originalInfoPanelHandle = mainWindowHandle.getInfoPanel();
        postNow(nearbyPersonNotInCurrentListEventStub);
        mainWindowHandle.updateChangeInList();
        PersonCardHandle expectedCard = mainWindowHandle.getPersonListPanel()
                .getPersonCardHandle(INDEX_THIRD_PERSON.getZeroBased());
        PersonCardHandle selectedCard = mainWindowHandle.getPersonListPanel().getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
        assertNotSame(originalPersonListPanelHandle, mainWindowHandle.getPersonListPanel());
        assertNotSame(originalInfoPanelHandle, mainWindowHandle.getInfoPanel());
    }

    @Test
    public void handleNearbyPersonPanelSelectionChangeEvent() {
        JumpToListRequestEvent jumpToListRequestEventStub = new JumpToListRequestEvent(INDEX_SECOND_PERSON);
        NearbyPersonPanelSelectionChangedEvent nearbyPersonPanelSelectionChangedEventStub =
                new NearbyPersonPanelSelectionChangedEvent(new PersonCard(CARL, 2));
        postNow(jumpToListRequestEventStub); // Benson is selected
        postNow(nearbyPersonPanelSelectionChangedEventStub); // Carl should be selected
        mainWindowHandle.updateChangeInList();
        assertCardEquals(mainWindowHandle.getPersonListPanel().getPersonCardHandle(CARL),
                mainWindowHandle.getPersonListPanel().getHandleToSelectedCard());
        assertInfoDisplaysPerson(nearbyPersonPanelSelectionChangedEventStub.getNewSelection().person,
                mainWindowHandle.getInfoPanel());
    }

}
