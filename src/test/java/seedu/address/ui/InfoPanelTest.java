package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.testutil.GuiTestAssert.assertInfoDisplaysPerson;

import java.net.URL;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InfoPanelHandle;
import seedu.address.MainApp;
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
        assertEquals(infoPanelHandle.getAddress(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getAddressField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getDebt(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getDebtField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getPostalCode(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getPostalCodeField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getName(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getEmail(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getEmailField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getPhone(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getPhoneField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getTags(), new ArrayList<>());

        // associated info of a person
        postNow(selectionChangedEventStub);
        assertEquals(infoPanelHandle.getAddress(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getAddressField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getDebt(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getDebtField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getPostalCode(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getPostalCodeField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getName(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getEmail(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getEmailField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getPhone(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getPhoneField(), MESSAGE_EMPTY_STRING);
        assertEquals(infoPanelHandle.getTags(), new ArrayList<>());
    }

    /**
     * Asserts that {@code infoPanel} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertInfoDisplay(InfoPanel infoPanel, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        InfoPanelHandle personInfoHandle = new InfoPanelHandle(infoPanel.getRoot());

        // verify person details are displayed correctly
        assertInfoDisplaysPerson(expectedPerson, personInfoHandle);
    }
}
