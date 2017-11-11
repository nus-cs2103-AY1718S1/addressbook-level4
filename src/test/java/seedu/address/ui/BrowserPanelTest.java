package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.AccessLocationRequestEvent;
import seedu.address.commons.events.ui.AccessWebsiteRequestEvent;

//@@author DarrenCzen
public class BrowserPanelTest extends GuiUnitTest {
    private static final String ALICE_WEBSITE = "https://twitter.com/alice";
    private static final String ALICE_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private AccessWebsiteRequestEvent accessWebsiteEventStub;
    private AccessLocationRequestEvent accessLocationEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        accessWebsiteEventStub = new AccessWebsiteRequestEvent(ALICE.getWebsite().toString());
        accessLocationEventStub = new AccessLocationRequestEvent(ALICE.getAddress().toString());

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void displayWebsite() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(accessWebsiteEventStub);
        URL expectedPersonUrl = new URL(ALICE_WEBSITE);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // associated location of a person
        postNow(accessLocationEventStub);
        URL expectedPersonLocation = new URL(GOOGLE_SEARCH_URL_PREFIX + ALICE_ADDRESS.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        // not equals as generated website for inputted address on google maps is different
        assertNotEquals(expectedPersonLocation, browserPanelHandle.getLoadedUrl());
    }
}
