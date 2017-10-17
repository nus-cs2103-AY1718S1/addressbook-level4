package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;

import guitests.guihandles.ProfilePanelHandle;
import seedu.address.model.person.ReadOnlyPerson;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class ProfilePanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private ProfilePanel profilePanel;
    //private ProfilePanelHandle profilePanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> profilePanel = new ProfilePanel());
        uiPartRule.setUiPart(profilePanel);


       // profilePanelHandle = new ProfilePanelHandle(profilePanel.getRoot());

    }

    @Test
    public void display() throws Exception {
        // default web page
        String expectedDefaultName = ProfilePanel.DEFAULT_MESSAGE;
        assertEquals(expectedDefaultName, profilePanel.getName());

        // associated web page of a person
        postNow(selectionChangedEventStub);

       ReadOnlyPerson expectedSelectedPerson = ALICE;
       assertEquals(ALICE, profilePanel.person);

    }
}
