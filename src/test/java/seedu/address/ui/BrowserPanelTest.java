package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.FACEBOOK_SEARCH_URL;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.ShowFacebookRequestEvent;

public class BrowserPanelTest extends GuiUnitTest {
    //private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private ShowFacebookRequestEvent facebookEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        //selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        facebookEventStub = new ShowFacebookRequestEvent("Bob");

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        /*
        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
        */

        // Facebook search page of a person
        postNow(facebookEventStub);
        URL expectedFacebookUrl = new URL(FACEBOOK_SEARCH_URL + "Bob");

        //waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedFacebookUrl, browserPanelHandle.getLoadedUrl());
    }
}
