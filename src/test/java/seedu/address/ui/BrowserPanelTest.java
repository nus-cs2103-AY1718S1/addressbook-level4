package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_CAPTCHA_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.WebsiteSelectionRequestEvent;

//@@author hansiang93
public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private WebsiteSelectionRequestEvent mapsSelectionEventStub;
    private WebsiteSelectionRequestEvent searchSelectionEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        mapsSelectionEventStub = new WebsiteSelectionRequestEvent("mapsView");
        searchSelectionEventStub = new WebsiteSelectionRequestEvent("searchView");

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        //@@author bladerail
        String urlString = GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX;
        URL expectedPersonUrl = new URL(urlString);
        URL expectedCaptchaUrl = new URL(
                GOOGLE_SEARCH_CAPTCHA_PREFIX + getCaptchaUrl(urlString));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertTrue(expectedPersonUrl.equals(browserPanelHandle.getLoadedUrl())
                || browserPanelHandle.getLoadedUrl().toExternalForm()
                .contains(expectedCaptchaUrl.toExternalForm()));
        //@@author hansiang93
    }

    @Test
    public void buttonPressDisplay() throws Exception {
        postNow(selectionChangedEventStub);
        // associated maps page of a person
        postNow(mapsSelectionEventStub);
        String expectedPersonMapsUrl = "/maps/search/";

        waitUntilBrowserLoaded(browserPanelHandle);
        assertTrue(browserPanelHandle.getLoadedUrl().toString().contains(expectedPersonMapsUrl));

        // associated search page of a person
        postNow(searchSelectionEventStub);
        //@@author bladerail
        String urlString = GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX;
        URL expectedPersonUrl = new URL(urlString);
        URL expectedCaptchaUrl = new URL(
                GOOGLE_SEARCH_CAPTCHA_PREFIX + getCaptchaUrl(urlString));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertTrue(expectedPersonUrl.equals(browserPanelHandle.getLoadedUrl())
                || browserPanelHandle.getLoadedUrl().toExternalForm()
                .contains(expectedCaptchaUrl.toExternalForm()));
        //@@author hansiang93
    }

    //@@author bladerail
    /**
     * Returns the string format of a captcha URL
     * @param urlString
     * @return
     */
    private String getCaptchaUrl(String urlString) {
        String updatedUrlString = urlString.replaceAll("\\+", "%2B")
                .replaceAll("\\?", "%3F")
                .replaceAll("\\=", "%3D")
                .replaceAll("\\&", "%26");
        return updatedUrlString;
    }
    //@@author hansiang93
}
