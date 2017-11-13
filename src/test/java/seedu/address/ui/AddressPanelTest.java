package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.AddressPanel.DEFAULT_LIGHT_PAGE;
import static seedu.address.ui.AddressPanel.DEFAULT_PAGE;
import static seedu.address.ui.AddressPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.AddressPanel.GOOGLE_SEARCH_URL_SUFFIX;
import static seedu.address.ui.AddressPanel.UNIT_NUMBER_REGEX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.AddressPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.UserPrefs;

public class AddressPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private AddressPanel addressPanel;
    private AddressPanelHandle addressPanelHandle;
    private UserPrefs prefs;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        prefs = new UserPrefs();
        guiRobot.interact(() -> addressPanel = new AddressPanel(prefs));
        uiPartRule.setUiPart(addressPanel);

        addressPanelHandle = new AddressPanelHandle(addressPanel.getRoot());
    }

    //@@author darrinloh-reused
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, addressPanelHandle.getLoadedUrl());

        // secondary web page
        URL expectedDefaultPageUrlSecondary = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_LIGHT_PAGE);
        assertFalse(expectedDefaultPageUrlSecondary.equals(addressPanelHandle.getLoadedUrl()));

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getAddress().value.replaceAll(" ", "+").replaceAll(UNIT_NUMBER_REGEX, "")
                + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(addressPanelHandle);
        assertEquals(expectedPersonUrl, addressPanelHandle.getLoadedUrl());
    }
}
