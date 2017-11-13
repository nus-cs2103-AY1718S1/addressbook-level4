package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ProfilePanelHandle;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.model.insurance.InsurancePerson;
import seedu.address.model.person.ReadOnlyPerson;

//@@author RSJunior37
public class ProfilePanelTest extends GuiUnitTest {
    private PersonNameClickedEvent personNameClickedEventStub;

    private ProfilePanel profilePanel;
    private ProfilePanelHandle profilePanelHandle;

    @Before
    public void setUp() {
        InsurancePerson insurancePerson = new InsurancePerson(ALICE);
        personNameClickedEventStub = new PersonNameClickedEvent(insurancePerson);

        guiRobot.interact(() -> profilePanel = new ProfilePanel());
        uiPartRule.setUiPart(profilePanel);

        profilePanelHandle = new ProfilePanelHandle(profilePanel.getRoot());

    }

    @Test
    public void display() throws Exception {
        // default profile page
        final String expectedDefaultName = ProfilePanel.DEFAULT_MESSAGE;

        assertEquals(expectedDefaultName, profilePanelHandle.getName());
        assertNull(profilePanelHandle.getEmail());
        assertNull(profilePanelHandle.getPhone());
        //@@author Pujitha97
        assertNull(profilePanelHandle.getDateOfBirth());
        assertNull(profilePanelHandle.getGender());
        //@@author
        assertNull(profilePanelHandle.getAddress());

        // select Stub Person
        postNow(personNameClickedEventStub);

        ReadOnlyPerson expectedSelectedPerson = ALICE;

        assertEquals(expectedSelectedPerson.getName().toString(), profilePanelHandle.getName());
        assertEquals(expectedSelectedPerson.getEmail().toString(), profilePanelHandle.getEmail());
        assertEquals(expectedSelectedPerson.getPhone().toString(), profilePanelHandle.getPhone());
        assertEquals(expectedSelectedPerson.getAddress().toString(), profilePanelHandle.getAddress());
        //@@author Pujitha97
        assertEquals(expectedSelectedPerson.getDateOfBirth().toString(), profilePanelHandle.getDateOfBirth());
        assertEquals(expectedSelectedPerson.getGender().toString(), profilePanelHandle.getGender());
        //@@author
    }
}
