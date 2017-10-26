package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.FXML_FILE_FOLDER;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_DIRECTION_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_SEARCH_URL_SUFFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.BrowserPanelFindRouteEvent;
import seedu.address.commons.events.ui.BrowserPanelShowLocationEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private BrowserPanelShowLocationEvent showLocationEventStub;
    private BrowserPanelFindRouteEvent findRouteEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    private String startLocation = "Clementi Street";

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        showLocationEventStub = new BrowserPanelShowLocationEvent(new Person(ALICE));
        findRouteEventStub = new BrowserPanelFindRouteEvent(new Person(ALICE), startLocation);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void displayPerson() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void displayLocation() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated Google map of a person's address
        postNow(showLocationEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_MAP_SEARCH_URL_PREFIX + "123,+Jurong+West+Ave+6,+"
                + "?dg=dbrw&newdg=1");

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void displayRoute() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated the route from entered location to selected person's address
        postNow(findRouteEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_MAP_DIRECTION_URL_PREFIX
                + startLocation.replaceAll(" ", "+") + GOOGLE_MAP_SEARCH_URL_SUFFIX
                + ALICE.getAddress().toString().replaceAll(" ", "+") + GOOGLE_MAP_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
