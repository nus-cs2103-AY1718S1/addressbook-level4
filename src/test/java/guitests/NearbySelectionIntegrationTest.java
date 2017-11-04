package guitests;

import static org.junit.Assert.assertNotSame;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertInfoDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertResultMessage;

import org.junit.Test;

import guitests.guihandles.InfoPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.NearbyPersonNotInCurrentListEvent;
import seedu.address.commons.events.ui.NearbyPersonPanelSelectionChangedEvent;
import seedu.address.logic.ListObserver;
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
        assertResultMessage(mainWindowHandle.getResultDisplay(), ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                mainWindowHandle.getPersonListPanel().getSelectedCardIndex() + 1));
    }

    @Test
    public void handleNearbyPersonPanelSelectionChangeEvent() {
        JumpToListRequestEvent jumpToListRequestEventStub = new JumpToListRequestEvent(INDEX_SECOND_PERSON);
        NearbyPersonPanelSelectionChangedEvent nearbyPersonPanelSelectionChangedEventStub =
                new NearbyPersonPanelSelectionChangedEvent(new PersonCard(CARL, 2));
        postNow(jumpToListRequestEventStub); // Benson is selected
        postNow(nearbyPersonPanelSelectionChangedEventStub); // Carl should be selected
        mainWindowHandle.updateChangeInList();
        // TODO: Assert personListPanel selects the correct person.
        assertInfoDisplaysPerson(nearbyPersonPanelSelectionChangedEventStub.getNewSelection().person,
                mainWindowHandle.getInfoPanel());
        assertResultMessage(mainWindowHandle.getResultDisplay(), ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                mainWindowHandle.getPersonListPanel().getSelectedCardIndex() + 1));
    }

}
