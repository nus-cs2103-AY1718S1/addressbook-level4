package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ProfilePanelHandle;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class ProfilePanelTest extends GuiUnitTest {
    private PersonNameClickedEvent personNameClickedEventStub;

    private ProfilePanel profilePanel;
    private ProfilePanelHandle profilePanelHandle;

    @Before
    public void setUp() {
        personNameClickedEventStub = new PersonNameClickedEvent(ALICE);

        guiRobot.interact(() -> profilePanel = new ProfilePanel());
        uiPartRule.setUiPart(profilePanel);

        profilePanelHandle = new ProfilePanelHandle(profilePanel.getRoot());

    }

    @Test
    public void display() throws Exception {
        // default profile page
        final String expectedDefaultName = ProfilePanel.DEFAULT_MESSAGE;
        final String emptyText = "";

        assertEquals(expectedDefaultName, profilePanelHandle.getName());
        assertEquals(emptyText, profilePanelHandle.getEmail());
        assertEquals(emptyText, profilePanelHandle.getPhone());
        assertEquals(emptyText, profilePanelHandle.getDateOfBirth());
        assertEquals(emptyText, profilePanelHandle.getAddress());

        // select Stub Person
        postNow(personNameClickedEventStub);

        ReadOnlyPerson expectedSelectedPerson = ALICE;

        assertEquals(expectedSelectedPerson.getName().toString(), profilePanelHandle.getName());
        assertEquals(expectedSelectedPerson.getEmail().toString(), profilePanelHandle.getEmail());
        assertEquals(expectedSelectedPerson.getPhone().toString(), profilePanelHandle.getPhone());
        assertEquals(expectedSelectedPerson.getDateOfBirth().toString(), profilePanelHandle.getDateOfBirth());
        assertEquals(expectedSelectedPerson.getAddress().toString(), profilePanelHandle.getAddress());

    }
}
