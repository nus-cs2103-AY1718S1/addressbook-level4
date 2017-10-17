package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import seedu.address.model.person.ReadOnlyPerson;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class ProfilePanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private ProfilePanel profilePanel;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> profilePanel = new ProfilePanel());
        uiPartRule.setUiPart(profilePanel);

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
