package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
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
//        assertEquals(ALICE.getName().toString(), infoPanelHandle.getName());
//        assertEquals(ALICE.getPhone().toString(), infoPanelHandle.getPhone());
//        assertEquals(MESSAGE_INFO_PHONE_FIELD, infoPanelHandle.getPhoneField());
//        assertEquals(ALICE.getEmail().toString(), infoPanelHandle.getEmail());
//        assertEquals(MESSAGE_INFO_EMAIL_FIELD, infoPanelHandle.getEmailField());
//        assertEquals(ALICE.getAddress().toString(), infoPanelHandle.getAddress());
//        assertEquals(MESSAGE_INFO_ADDRESS_FIELD, infoPanelHandle.getAddressField());
//        assertEquals(ALICE.getPostalCode().toString(), infoPanelHandle.getPostalCode());
//        assertEquals(MESSAGE_INFO_POSTAL_CODE_FIELD, infoPanelHandle.getPostalCodeField());
//        assertEquals(ALICE.getDebt().toString(), infoPanelHandle.getDebt());
//        assertEquals(MESSAGE_INFO_DEBT_FIELD, infoPanelHandle.getDebtField());
//        assertEquals(ALICE.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()), infoPanelHandle.getTags());
        assertInfoDisplaysPerson(ALICE, infoPanelHandle);
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
