package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPlaces.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.commons.events.ui.PlacePanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private PlacePanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PlacePanelSelectionChangedEvent(new PlaceCard(ALICE, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a place
        postNow(selectionChangedEventStub);
        /*
        URL expectedPlaceUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);
        */
        //@@author thanhson16198
        URL expectedPlaceUrl;
        if (ALICE.getWebsite().toString().contains("www.-.com")) {
            expectedPlaceUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                    + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);
        } else {
            expectedPlaceUrl = new URL(ALICE.getWebsite().toString().replaceAll(" ", "+"));
        }
        //@@author
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPlaceUrl, browserPanelHandle.getLoadedUrl());
    }
}
