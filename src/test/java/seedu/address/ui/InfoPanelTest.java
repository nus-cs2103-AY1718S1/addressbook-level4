package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.ui.testutil.GuiTestAssert.assertInfoDisplaysPerson;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InfoPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author khooroko
public class InfoPanelTest extends GuiUnitTest {
    private static final String MESSAGE_EMPTY_STRING = "";

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private InfoPanel infoPanel;
    private InfoPanelHandle infoPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> infoPanel = new InfoPanel());
        uiPartRule.setUiPart(infoPanel);

        infoPanelHandle = new InfoPanelHandle(infoPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default info panel
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getAddress());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getAddressField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDebt());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDebtField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getPostalCode());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getPostalCodeField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getName());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getEmail());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getEmailField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getPhone());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getPhoneField());
        assertEquals(new ArrayList<>(), infoPanelHandle.getTags());

        // associated info of a person
        postNow(selectionChangedEventStub);
        assertInfoDisplay(infoPanel, ALICE);

        // associated info of next person
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1));
        postNow(selectionChangedEventStub);
        assertInfoDisplay(infoPanel, BOB);
    }

    /**
     * Asserts that {@code infoPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertInfoDisplay(InfoPanel infoPanel, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        InfoPanelHandle personInfoHandle = new InfoPanelHandle(infoPanel.getRoot());

        // verify person details are displayed correctly
        assertInfoDisplaysPerson(expectedPerson, personInfoHandle);
    }
}
