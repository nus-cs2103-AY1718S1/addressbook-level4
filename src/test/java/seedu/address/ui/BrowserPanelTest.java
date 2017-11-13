package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAPS_DIRECTIONS_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAPS_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.model.PersonAddressDisplayDirectionsEvent;
import seedu.address.commons.events.model.PersonAddressDisplayMapEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private PersonAddressDisplayMapEvent personAddressDisplayMapEventStub;
    private PersonAddressDisplayDirectionsEvent personAddressDisplayDirectionsEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    //@@author nbriannl
    @Before
    public void setUp() throws Exception {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        personAddressDisplayMapEventStub = new PersonAddressDisplayMapEvent(new Person(ALICE), 0);
        personAddressDisplayDirectionsEventStub = new PersonAddressDisplayDirectionsEvent(new Person(ALICE),
                new Address("Blk 123 Yishun 61"), 0);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person, should not be loaded
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertNotEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        postNow(personAddressDisplayMapEventStub);
        URL expectedGMapsUrl = new URL(
                GOOGLE_MAPS_URL_PREFIX + "123,%20Jurong%20West%20Ave%206,%20?dg=dbrw&newdg=1");
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedGMapsUrl, browserPanelHandle.getLoadedUrl());

        postNow(personAddressDisplayDirectionsEventStub);
        URL expectedGMapsDirectionsUrl = new URL(
                GOOGLE_MAPS_DIRECTIONS_URL_PREFIX + "Blk%20123%20Yishun%2061" + "/" + "123,"
                        + "%20Jurong%20West%20Ave%206,%20#08-111");
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedGMapsDirectionsUrl, browserPanelHandle.getLoadedUrl());

    }
}
