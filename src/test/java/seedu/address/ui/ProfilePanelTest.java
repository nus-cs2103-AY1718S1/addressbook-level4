package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InsuranceProfileHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class ProfilePanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private ProfilePanel profilePanel;
    private InsuranceProfileHandle profilePanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> profilePanel = new ProfilePanel());
        uiPartRule.setUiPart(profilePanel);

        profilePanelHandle = new InsuranceProfileHandle(profilePanel.getRoot());

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
        postNow(selectionChangedEventStub);

        ReadOnlyPerson expectedSelectedPerson = ALICE;

        assertEquals(expectedSelectedPerson.getName().toString(), profilePanelHandle.getName());
        assertEquals(expectedSelectedPerson.getEmail().toString(), profilePanelHandle.getEmail());
        assertEquals(expectedSelectedPerson.getPhone().toString(), profilePanelHandle.getPhone());
        assertEquals(expectedSelectedPerson.getDateOfBirth().toString(), profilePanelHandle.getDateOfBirth());
        assertEquals(expectedSelectedPerson.getAddress().toString(), profilePanelHandle.getAddress());

    }
}
