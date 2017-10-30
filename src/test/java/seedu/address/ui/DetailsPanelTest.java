package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.ui.testutil.GuiTestAssert.assertDetailsDisplaysPerson;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.DetailsPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class DetailsPanelTest extends GuiUnitTest {
    private static final String MESSAGE_EMPTY_STRING = "";

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private DetailsPanel detailsPanel;
    private DetailsPanelHandle detailsPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> detailsPanel = new DetailsPanel());
        uiPartRule.setUiPart(detailsPanel);

        detailsPanelHandle = new DetailsPanelHandle(detailsPanel.getRoot());
    }

    @Test
    public void display() throws Exception {

        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getName());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getAddress());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getAddressField());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getEmail());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getEmailField());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getSchEmail());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getSchEmailField());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getBirthday());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getBirthdayField());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getPhone());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getPhoneField());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getHomePhone());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getHomePhoneField());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getWebsite());
        assertEquals(MESSAGE_EMPTY_STRING, detailsPanelHandle.getWebsiteField());
        assertEquals(new ArrayList<>(), detailsPanelHandle.getTags());
        detailsPanelHandle.rememberSelectedPersonDetails();

        // associated details of a person
        postNow(selectionChangedEventStub);
        assertDetailsDisplay(detailsPanel, ALICE);
        assertTrue(detailsPanelHandle.isSelectedPersonChanged());
        detailsPanelHandle.rememberSelectedPersonDetails();

        // asserts that no change is registered when same person is clicked
        postNow(selectionChangedEventStub);
        assertDetailsDisplay(detailsPanel, ALICE);
        assertFalse(detailsPanelHandle.isSelectedPersonChanged());

        // associated info of next person
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1));
        postNow(selectionChangedEventStub);
        assertTrue(detailsPanelHandle.isSelectedPersonChanged());
        assertDetailsDisplay(detailsPanel, BOB);
    }

    @Test
    public void equals() {
        detailsPanel = new DetailsPanel();

        assertTrue(detailsPanel.equals(detailsPanel));

        assertFalse(detailsPanel.equals(detailsPanelHandle));

        DetailsPanel expectedDetailsPanel = new DetailsPanel();

        assertTrue(detailsPanel.equals(expectedDetailsPanel));

        detailsPanel.loadPersonInfo(AMY);
        assertFalse(detailsPanel.equals(expectedDetailsPanel));

        expectedDetailsPanel.loadPersonInfo(AMY);
        assertTrue(detailsPanel.equals(expectedDetailsPanel));
    }

    /**
     * Asserts that {@code infoPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertDetailsDisplay(DetailsPanel detailsPanel, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        DetailsPanelHandle personDetailsHandle = new DetailsPanelHandle(detailsPanel.getRoot());

        // verify person details are displayed correctly
        assertDetailsDisplaysPerson(expectedPerson, personDetailsHandle);
    }
}
